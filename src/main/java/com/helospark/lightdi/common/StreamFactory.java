package com.helospark.lightdi.common;

import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import com.helospark.lightdi.LightDiContextConfiguration;

public class StreamFactory {
    private ForkJoinPool forkJoinPool = null;
    private boolean isParallel = false;

    public StreamFactory(LightDiContextConfiguration contextConfiguration) {
	if (contextConfiguration.getThreadNumber() > 1) {
	    forkJoinPool = new ForkJoinPool(contextConfiguration.getThreadNumber());
	    isParallel = true;
	}
    }

    public <T> Stream<T> stream(Collection<T> collection) {
	Stream<T> result = collection.stream();
	if (isParallel) {
	    return new ParallelStream<>(forkJoinPool, result.parallel());
	} else {
	    return result;
	}
    }
}
