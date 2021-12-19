package general.util;

public class Pair<T, E> {
    private final T t;
    private final E e;

    private Pair(T t, E e) {
        this.t = t;
        this.e = e;
    }

    public static <T, E> Pair<T, E> of(T t, E e) {
        return new Pair<>(t, e);
    }

    public T getLeft() {
        return t;
    }

    public E getRight() {
        return e;
    }
}
