package priorityQ;

public abstract class MinPriorityQ<E extends Comparable<E>> {			//generic type으로 E를 쓸 것인데 해당 클래스 내부에서 E끼리의 비교가 발생할 수 있음을 명시한다. 제한을 둔다.
	private static final int DEFAULT_CAPACITY = 100;

	private int _size;
	private int _capacity;

	public MinPriorityQ() {										//기본생성자는 
		this(MinPriorityQ.DEFAULT_CAPACITY);					//기본크기 상수를 인자로 갖는 생성자를 호출함. 
	}

	@SuppressWarnings("unchecked")
	public MinPriorityQ(int givenCapacity) {
		this.setCapacity(givenCapacity);
		this.setSize(0);
	}

	//setter는 private으로 getter는 public으로
	public int size() {
		return this._size;
	}

	protected void setSize(int newSize) {
		this._size = newSize;
	}

	public int capacity() {
		return this._capacity;
	}

	protected void setCapacity(int newCapacity) {
		this._capacity = newCapacity;
	}

	public boolean isEmpty() {
		return (this.size() == 0);
	}

	public boolean isFull() { return (this.size() == this.capacity());}

	public abstract boolean add(E anElement);
	
	public abstract E min();
	
	public abstract E removeMin();
}
