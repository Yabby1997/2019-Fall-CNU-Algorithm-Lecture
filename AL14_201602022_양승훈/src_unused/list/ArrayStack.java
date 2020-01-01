package list;

public class ArrayStack<E> implements Stack<E> {
    private static final int DEFAULT_CAPACITY = 100;                                                                    //기본 크기 상수 100 설정

    private int _capacity;
    private int _top;
    private E[] _elements;

    @SuppressWarnings("unchecked")
    public ArrayStack(int givenCapacity){
        this.setCapacity(givenCapacity);
        this.setElements((E[]) new Object[this.capacity()]);
        this.setTop(-1);
    }

    public ArrayStack(){
        this(ArrayStack.DEFAULT_CAPACITY);
    }

    private int capacity(){
        return this._capacity;
    }

    private void setCapacity(int newCapacity){
        this._capacity = newCapacity;
    }

    private int top(){
        return this._top;
    }

    private void setTop(int newTop){
        this._top = newTop;
    }

    private E[] elements(){
        return this._elements;
    }

    private void setElements(E[] newElements){
        this._elements = newElements;
    }


    @Override
    public int size() {
        return (this.top() + 1);
    }

    @Override
    public void reset(){
        for(int i = 0; i < this.size(); i++){
            this.elements()[i] = null;
        }
        this.setTop(-1);
    }

    @Override
    public boolean isEmpty(){
        return (this.top() == -1);
    }

    @Override
    public boolean isFull(){
        return (this.top() == this.capacity()-1);
    }

    @Override
    public boolean push(E anElement){
        if (!this.isFull()){
            this.setTop(this.top() + 1);
            this.elements()[this.top()] = anElement;
        }
        return false;
    }

    @Override
    public E pop(){
        if (!this.isEmpty()) {
            E topElement = this.elements()[this.top()];
            this.elements()[this.top()] = null;
            this.setTop(this.top() - 1);
            return topElement;
        }
        return null;
    }

    @Override
    public E peek(){
        if(!this.isEmpty()){
            return this.elements()[this.top()];
        }
        return null;
    }
}
