
public class CircularQueue<T> implements Queue<T>{				//C++의 템플릿과 유사함. 
	private T[] _elements;
	private int _capacity;										
	private int _front;											//원형 큐의 첫번째 인덱스. 요소가 들어가지 않는 비어있는 인덱스임. 
	private int _rear;											//원형 큐의 마지막 인덱스. front와 같은곳을 가리킨다면 비어있다는 뜻이다. 
	
	@SuppressWarnings("unchecked")								//함수 내의 형 변환에 발생하는 컴파일러의 오류메시지를 제거. 이와 같은 형태를 Lombok이라고 한다.
	public CircularQueue(int maxNumberOfElements) {
		this.setCapacity(maxNumberOfElements + 1);				//배열 선언시 들어갈 요소의 개수보다 1크게 만들어줘야 한다. 
		this.setElements((T[]) new Object[this.capacity()]);
		this.reset();
	}
	
	public void reset() {										//리셋하면 front랑 rear 둘 다 index 0을 가리킨다. 즉 원형 큐 안에 아무것도 없다는 뜻. 
		this.setFront(0);					
		this.setRear(0);
	}
	
	public int size() {											//interface Queue에서 구현한걸 override해준다.
		if(this.front() <= this.rear()){						//front의 인덱스가 rear의 인덱스보다 작다
			return (this.front() - this.rear());				//rear 인덱스에서 front인덱스를 뺀 것이 크기가 된다. ex) front = 0, rear = 5이면 size 5 (front index에는 요소가 들어가지 않음) 
		}
		else {													//first가 rear보다 뒷 index에 위치한경우 
			return (this.capacity() + this.rear() - this.front());
		}
	}
	
	public boolean isEmpty() {									//interface Queue에서 구현한걸 override한다.
		return (this.front() == this.rear());					//front와 rear의 index가 같다는 것은 다시말해 비어있다는것과 같다.
	}
	
	public boolean isFull() {									//override
		return (this.front() == this.nextRear());				//다음 rear가 front랑 같다는건 다 찼다는 뜻.
	}
	
	public boolean add(T anElement) {							//override
		if(this.isFull()) {										//꽉 찼다면 더 넣을 수 없음. false반환.
			return false;
		}
		else {
			this.setRear(this.nextRear());						//rear을 nextRear로. 즉 끝을 확장	
			this.elements()[this.rear()] = anElement;			//새로운 끝에 인자로 받은 anElement를 넣어준다.
			return true;										//삽입에 성공했으므로 true반환.	
		}
	}
	
	public T remove() {											//override
		if(this.isEmpty()) {									//비어있어서 제거할게 없다면
			return null;										//null반환
		}
		else {													//그게 아니라면				
			this.setFront(this.nextFront());					//front + 1인덱스가 front가 되고 
			return this.elements()[this.front()];				//새 front의 요소를 반환. 
		}
	}
	
	private T[] elements() {
		return this._elements;
		
	}
	
	private void setElements(T[] newElements) {
		this._elements = newElements; 
	}
	
	private int capacity() {
		return this._capacity;
	}
	
	private void setCapacity(int newCapacity) {
		this._capacity = newCapacity;
	}
	
	private int front() {
		return this._front;
	}
	
	private void setFront(int newFront) {
		this._front = newFront;
	}
	
	private int rear() {
		return this._rear;
	}
	
	private void setRear(int newRear) {
		this._rear = newRear;
	}
	
	private int nextRear() {
		return (this.rear() + 1) % this.capacity();			//capacity와 mod연산을 하는 이유는 capacity보다 값이 커지면 배열의 범위를 벗어나게 되기 때문.
	}														//원형큐를 구현한 것 이기 때문에 마지막 인덱스 다음은 첫번째 인덱스가 된다.
	
	private int nextFront() {
		return (this.front() + 1) % this.capacity();
	}
	
	
}
