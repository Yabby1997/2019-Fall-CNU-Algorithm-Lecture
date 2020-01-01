package list;

public class LinkedStackWithIterator<T> implements StackWithIterator<T> {			//stackWithIterator가 이미 stack을 상속받기때문에, 얘만 상속 받으면 된다. 
	private LinkedNode<T> _top;
	private int _size;

	//constructor 
	public LinkedStackWithIterator() {
		this.reset();								//reset함수를 구현하고 생성자에서는 그냥 reset함수를 호출하는것으로 한다.
	}
	
	//getter/setter
	@Override
	public int size() {
		return this._size;
	}
	
	private void setSize(int newSize) {
		this._size = newSize;
	}
	
	private LinkedNode<T> top(){
		return this._top;
	}
	
	private void setTop(LinkedNode<T> newTop) {
		this._top = newTop;
	}
	
	@Override
	public void reset() {
		this.setSize(0);
		this.setTop(null);
	}
	
	@Override
	public boolean isEmpty() {
		return (this.size() == 0);							//size가 0이되면 비어있는 것.
	}

	@Override
	public boolean isFull() {														//정해진 용량이 없다. false 반환. 
		return false;
	}

	@Override
	public boolean push(T anElement) {
		LinkedNode<T> newTop = new LinkedNode<T>(anElement, this.top());			//새로 들어올 top은 기존의 top을 다음 요소로 갖고, 인자로 받은 element를 갖는다. 
		this.setTop(newTop);														//새로 만들어진 top이 stack 의 top이된다. 
		this.setSize(this.size() + 1);												//삽입성공. 사이즈 + 1,true 반환. 
		return true;
	}

	@Override
	public T pop() {
		if(this.isEmpty()) {														//비어있다면 제거할 수 없다. 
			return null;
		}
		else {
			T removedElement = this.top().element();								//제거할 요소를 저장하고 
			this.setTop(this.top().next());											//top을 top다음거로 바꿔주고 
			this.setSize(this.size() - 1);											//사이즈를 1 줄인다 ->제거 끝 
			return removedElement;													//저장해놨던 제거된 요소를 반환 
		}
	}

	@Override
	public T peek() {
		if(this.isEmpty()) {
			return null;
		}
		else {
			return this.top().element();											//그렇지 않다면 top의 요소를 반환해준다. 
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new IteratorForLinkedStack();
	}

	private class IteratorForLinkedStack implements Iterator<T>{
		private LinkedNode<T> _nextNode;
		
		private IteratorForLinkedStack() {
			this.setNextNode(LinkedStackWithIterator.this.top());					//이터레이터를 호출한 객체의 top이 iterator의 nextNode가 된다. 
		}
		
		private LinkedNode<T> nextNode(){
			return this._nextNode;
		}
		
		private void setNextNode(LinkedNode<T> newNextNode) {
			this._nextNode = newNextNode;
		}
		
		public boolean hasNext() {
			return (this.nextNode() != null);                                        //nextNode가null이 아니어야한다.
		}
		
		public T next() {
			T nextElement = this.nextNode().element();								//nextNode의 element를 반환해주고 
			this.setNextNode(this.nextNode().next());								//iterator가 가리키는 위치를 nextNode의 다음으로 옮겨준다. 
			return nextElement;
		}
	}
}
