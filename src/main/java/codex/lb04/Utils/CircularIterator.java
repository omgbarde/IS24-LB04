
package codex.lb04.Utils;

import java.util.ArrayList;
import java.util.Iterator;

public class CircularIterator<T> implements Iterator<T> {
    private ArrayList<T> list;
    private int current = 0;

    public CircularIterator(ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

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

