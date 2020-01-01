package list;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 100;

    private int _capacity;
    private int _size;
    private T[] _elements;

    public ArrayList(){
        this(ArrayList.DEFAULT_CAPACITY);
    }

    public ArrayList(int givenCapacity){
        this._capacity = givenCapacity;
        this._size = 0;
        this._elements = (T[]) new Object[this._capacity];
    }

    protected void setElementAt(T element, int order){
        this._elements[order] = element;
    }

    @Override
    public int capacity(){
        return this._capacity;
    }

    @Override
    public int size(){
        return this._size;
    }

    @Override
    public boolean isEmpty(){
        return (this._size == 0);
    }

    @Override
    public boolean isFull(){
        return (this._size == this._capacity);
    }

    @Override
    public boolean orderIsValid(int order){
        return ((order >= 0) && (order < this._size));
    }

    @Override
    public T elementAt(int order){
        return this._elements[order];
    }

    @Override
    public boolean add(T elementForAdd){
        if(!this.isFull()){
            this.setElementAt(elementForAdd, this.size());
            this._size++;
            return true;
        }
        return false;
    }
}
