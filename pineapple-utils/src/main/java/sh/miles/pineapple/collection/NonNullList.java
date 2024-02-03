package sh.miles.pineapple.collection;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * ArrayList wrapper that prevents null value
 *
 * @param <T> the type of the list
 * @since 1.0.0
 */
public class NonNullList<T> extends AbstractList<T> {

    private final List<T> list = new ArrayList<>();
    private Supplier<T> nullValue = null;

    public NonNullList(Supplier<T> nullValue) {
        this.nullValue = nullValue;
    }

    public NonNullList() {
    }

    @Override
    public boolean add(T t) {
        if (t == null) {
            if (nullValue != null) {
                list.add(nullValue.get());
                return true;
            }
            return false;
        }
        list.add(t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (element == null) {
            if (nullValue != null) {
                list.add(index, nullValue.get());
                return;
            }
            return;
        }
        list.add(index, element);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }
}
