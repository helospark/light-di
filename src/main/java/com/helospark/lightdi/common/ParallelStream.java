package com.helospark.lightdi.common;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Parallel stream implementation that does not use common fork join pool.
 * <p>
 * Intermediate operations just delegate to the stream field, terminal
 * operations execute operation on thread pool.
 * 
 * @author helospark
 *
 * @param <T> type of the stream
 */
public class ParallelStream<T> implements Stream<T> {
    private ForkJoinPool parallelPool;
    private Stream<T> stream;

    public ParallelStream(ForkJoinPool parallelPool, Stream<T> stream) {
        this.parallelPool = parallelPool;
        this.stream = stream;
    }

    @Override
    public Iterator<T> iterator() {
        return stream.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Override
    public Stream<T> sequential() {
        throw new IllegalStateException("Trying to sequential a parallel stream");
    }

    @Override
    public Stream<T> parallel() {
        throw new IllegalStateException("Explicit call for parallel on a parallel stream");
    }

    @Override
    public Stream<T> unordered() {
        return stream.unordered();
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return stream.onClose(closeHandler);
    }

    @Override
    public void close() {
        stream.close();
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return stream.filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return new ParallelStream<>(parallelPool, stream.map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new ParallelStream<>(parallelPool, stream.flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Stream<T> distinct() {
        return stream.distinct();
    }

    @Override
    public Stream<T> sorted() {
        return stream.sorted();
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return stream.sorted(comparator);
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return stream.peek(action);
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return stream.limit(maxSize);
    }

    @Override
    public Stream<T> skip(long n) {
        return stream.skip(n);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        submitToThreadPool(() -> stream.forEach(action));
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        submitToThreadPool(() -> stream.forEachOrdered(action));
    }

    @Override
    public Object[] toArray() {
        return submitToThreadPool(() -> stream.toArray());
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return submitToThreadPool(() -> stream.toArray(generator));
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return submitToThreadPool(() -> stream.reduce(identity, accumulator));
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return submitToThreadPool(() -> stream.reduce(accumulator));
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return submitToThreadPool(() -> stream.reduce(identity, accumulator, combiner));
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return submitToThreadPool(() -> stream.collect(supplier, accumulator, combiner));
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return submitToThreadPool(() -> stream.collect(collector));
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return submitToThreadPool(() -> stream.min(comparator));
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return submitToThreadPool(() -> stream.max(comparator));
    }

    @Override
    public long count() {
        return submitToThreadPool(() -> stream.count());
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return submitToThreadPool(() -> stream.anyMatch(predicate));
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return submitToThreadPool(() -> stream.allMatch(predicate));
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return submitToThreadPool(() -> stream.noneMatch(predicate));
    }

    @Override
    public Optional<T> findFirst() {
        return submitToThreadPool(() -> stream.findFirst());
    }

    @Override
    public Optional<T> findAny() {
        return submitToThreadPool(() -> stream.findAny());
    }

    private <F> F submitToThreadPool(Callable<F> callable) {
        try {
            return parallelPool.submit(callable).get();
        } catch (InterruptedException e) {
            throw new RuntimeException("Unable to get result from thread pool");
        } catch (ExecutionException e) {
            throw translateExecutionException(e);
        }
    }

    private void submitToThreadPool(Runnable runnable) {
        try {
            parallelPool.submit(runnable).get();
        } catch (InterruptedException e) {
            throw new RuntimeException("Unable to get result from thread pool");
        } catch (ExecutionException e) {
            throw translateExecutionException(e);
        }
    }

    private RuntimeException translateExecutionException(ExecutionException e) {
        Throwable cause = e.getCause();
        if (cause == null) {
            return new RuntimeException("Unable to get result from thread pool", e);
        } else if (cause instanceof RuntimeException) {
            return (RuntimeException) cause;
        } else {
            return new RuntimeException("Unable to get result from thread pool", cause);
        }
    }

}
