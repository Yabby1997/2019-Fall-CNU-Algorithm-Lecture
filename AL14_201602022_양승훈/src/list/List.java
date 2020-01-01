package list;

public interface List<T> {
    public int capacity();
    public int size();
    public boolean isEmpty();
    public boolean isFull();
    public boolean orderIsValid(int order);
    public T elementAt(int order);
    public boolean add(T elementForAdd);
}
