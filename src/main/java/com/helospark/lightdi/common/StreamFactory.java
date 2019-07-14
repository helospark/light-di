package com.helospark.lightdi.common;

import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import com.helospark.lightdi.LightDiContextConfiguration;

public class StreamFactory {
    private static ForkJoinPool forkJoinPool = null;
    private int threadNumber;
    private boolean isParallel = false;

    public StreamFactory(LightDiContextConfiguration contextConfiguration) {
        if (contextConfiguration.getThreadNumber() > 1) {
            threadNumber = contextConfiguration.getThreadNumber();
            isParallel = true;
        }
    }

    public <T> Stream<T> stream(Collection<T> collection) {
        Stream<T> result = collection.stream();
        if (isParallel) {
            lazyInitForkJoinPool();
            return new ParallelStream<>(forkJoinPool, result.parallel());
        } else {
            return result;
        }
    }

    private void lazyInitForkJoinPool() {
        if (forkJoinPool == null) {
            synchronized (this) {
                if (forkJoinPool == null) {
                    forkJoinPool = new ForkJoinPool(threadNumber);
                }
            }
        }
    }

    public void close() {
        if (forkJoinPool != null) {
            forkJoinPool.shutdown();
            forkJoinPool = null;
        }
    }
}
