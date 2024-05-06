
package codex.lb04.Utils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents a circular iterator
 *
 * @param <T> the type of the iterator
 */
public class CircularIterator<T> implements Iterator<T> {
    private ArrayList<T> list;
    private int current = 0;

    public CircularIterator(ArrayList<T> list) {
        this.list = list;
    }

    /**
     * This method checks if the list has a next element
     *
     * @return true if the list has a next element, false otherwise
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * This method returns the next element of the list
     *
     * @return the next element of the list
     */
    @Override
    public T next() {
        if(current == list.size() - 1) {
            current = 0;
            return list.get(current);
        }
        current++;
        return list.get(current);
    }
}

