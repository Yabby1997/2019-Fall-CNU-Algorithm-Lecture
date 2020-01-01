package list;

public class ArrayList<T> extends List<T> {
	private static final int DEFAULT_CAPACITY = 10;
	
	private int _capacity;
	private int _size;
	private T[] _elements;
	
	//constructor
	public ArrayList() {
		this(ArrayList.DEFAULT_CAPACITY);									//기본생성자는 기본 크기를 인자로 넣어 생성자를 호출하도록 한다. 
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList(int givenCapacity) {
		this.setCapacity(givenCapacity);
		this.setElements((T[]) new Object[this.capacity()]);				//기본적으로 자바에서 모든 객체는 최 상단에 object를 상속받고 있다. 따라서 objec의 객체를 만들어 형변환 해준다. 
		this.setSize(0);
	}
	
	private int capacity() {
		return this._capacity;
	}

	//getter setter
	@Override
	public int size() {														//getter setter 중 size 는 list에서 선언되어있으므로 override 해 주어야 함. 
		return this._size;
	}
	
	private T[] elements(){
		return this._elements;
	}
	
	private void setCapacity(int newCapacity) {
		this._capacity = newCapacity;
	}
	
	private void setSize(int newSize) {
		this._size = newSize;
	}
	
	private void setElements(T[] newElements) {
		this._elements = newElements;
	}

	@Override
	public boolean isEmpty() {
		return (this.size() == 0);											//size가 0이면 비어있는 것. 
	}

	@Override
	public boolean isFull() {	
		return (this.size() == this.capacity());							//size랑 capacity가 같다면 그것은 리스트가 꽉 찼다는것을 의미한다. 
	}

	@Override
	public boolean add(T anElement) {
		return this.addToLast(anElement);
	}
	
	private boolean addToLast(T anElement) {
		if(!this.isFull()) {
			this.elements()[this.size()] = anElement;						//꽉 찬게 아니라면 this.size()는 마지막 인덱스의 바로 뒤 인덱스를 가리킨다. 거기에 새 요소를 넣어준다.
			this.setSize(this.size() + 1);									//삽입되었으므로 사이즈를 증가, true반환. 
			return true;
		}
		return false;
	}

	@Override
	public T removeAny() {
		return this.removeLast();
	}

	private T removeLast() {
		if(!this.isEmpty()) {
			this.setSize(this.size() - 1);									//미리 사이즈를 줄여준다. 이제 사이즈는 제거될값이 위치한 index를 나타낸다. 
			T removedElement = this.elements()[this.size()];				//제거될 index의 값을 removedElement에 저장해준다. 
			this.elements()[this.size()] = null;							//미리 값을 저장해뒀으니 이제 null로 제거해준다.
			return removedElement;
		}
		return null;
	}
	
	@Override
	public void reset() {
		for(int i = 0; i < this.size(); i++) {								//elemens배열을 순회하며 null로 만들어버린다. 
			this.elements()[i] = null;
		}
		this.setSize(0);													//입력된게 없으니 사이즈도 0으로 만든다. 
	}

	@Override
	public Iterator<T> listIterator() {
		return new IteratorForArrayList();
	}
	
	private class IteratorForArrayList implements Iterator<T>{
		private int _nextPosition;
		
		private IteratorForArrayList() {
			this.setNextPosition(0);
		}
		
		private int nextPosition() {
			return this._nextPosition;
		}
		
		private void setNextPosition(int newPosition) {
			this._nextPosition = newPosition;
		}
		
		public boolean hasNext() {
			return (this.nextPosition() < ArrayList.this.size());				//iterator가 기리키는 위치가 어레이리스트의 사이즈보다 작아야한다. 
		}
		
		public T next() {
			T nextElement =  ArrayList.this.elements()[this.nextPosition()];	//이터레이터가 가리키는 위치의 요소를 반환해주고, 
			this.setNextPosition(this.nextPosition() + 1);						//이터레이터가 가리키는 위치도 움직여준다. 
			return nextElement;
		}
	}
}
