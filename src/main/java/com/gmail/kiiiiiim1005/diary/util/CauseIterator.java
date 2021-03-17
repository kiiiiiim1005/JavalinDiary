package com.gmail.kiiiiiim1005.diary.util;

import java.util.Iterator;
import java.util.function.Consumer;

public class CauseIterator implements Iterator<Throwable> {

    private Throwable throwable;
    private int depth;

    public CauseIterator(Throwable throwable) {
        this(throwable, -1);
    }

    public CauseIterator(Throwable throwable, int depth) {
        this.throwable = throwable;
        this.depth = depth;
    }

    @Override
    public boolean hasNext() {
        return depth > 0 && throwable.getCause() != null;
    }

    @Override
    public Throwable next() {
        depth--;
        throwable = throwable.getCause();
        return throwable;
    }

    @Override
    public void remove() { }

    @Override
    public void forEachRemaining(Consumer<? super Throwable> action) {
        while (hasNext()) {
            action.accept(next());
        }
    }
}
