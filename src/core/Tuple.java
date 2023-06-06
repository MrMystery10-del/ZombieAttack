package core;

public class Tuple<T, K, V> {
    private T first;
    private K second;
    private V third;

    public Tuple(T first, K second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return first;
    }

    public K getSecond() {
        return second;
    }

    public V getThird() {
        return third;
    }
}
