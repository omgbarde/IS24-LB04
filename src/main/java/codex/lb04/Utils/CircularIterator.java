package codex.lb04.Utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class CircularIterator<T> implements Iterator<T> {

    private final Iterable<T> iterable;
    private Iterator<T> iterator;
    private T nextValue;

    /**
     * Create a new instance of a CircularIterator. The ordering of this
     * Iterator will be dictated by the Iterator returned by Collection itself.
     *
     * @param col The collection to iterate indefinitely
     *
     * @throws NullPointerException if col is {@code null}
     * @throws IllegalArgumentException if col is empty.
     */
    public CircularIterator(final Collection<T> col) {
        this.iterable = Objects.requireNonNull(col);
        this.iterator = col.iterator();
        if (col.isEmpty()) {
            throw new IllegalArgumentException("CircularIterator can only be used on non-empty lists");
        }
        this.nextValue = advance();
    }

    /**
     * Returns true since the iteration will forever cycle through the provided
     * {@code Collection}.
     *
     * @return Always true
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        final T next = nextValue;
        nextValue = advance();
        return next;
    }

    /**
     * Return the next value in the {@code Iterator}, restarting the
     * {@code Iterator} if necessary.
     *
     * @return The next value in the iterator
     */
    private T advance() {
        if (!iterator.hasNext()) {
            iterator = iterable.iterator();
        }
        return iterator.next();
    }

    /**
     * Peek at the next value in the Iterator. Calling this method multiple
     * times will return the same element without advancing this Iterator. The
     * value returned by this method will be the next item returned by
     * {@code next()}.
     *
     * @return The next value in this {@code Iterator}
     */
    public T peek() {
        return nextValue;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
