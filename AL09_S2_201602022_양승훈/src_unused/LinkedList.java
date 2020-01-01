package list;

public class LinkedList<T> extends List<T>{
	private LinkedNode<T> _head;						//연결리스트는 헤드와(연결노드)와 사이즈를 갖는다.
	private int _size;
		
	public LinkedList() {								//생성시엔 아무것도 없다.
		this.setHead(null);
		this.setSize(0);
	}
	
	@Override
	public boolean isEmpty() {
		return (this.size() == 0);
	}
	
	@Override
	public boolean isFull() {
		return false;
	}
	
	@Override
	public boolean add(T anElement) {
		LinkedNode<T> newHeadNode = new LinkedNode<T>(anElement, this.head());		//인자로 입력된 값을 요소로 갖고, 현제 리스트의 헤드를 다음노드로 갖는 새로운 헤드 노드를 만듬. 
		this.setHead(newHeadNode);													//만들어진 노드는 연결리스트의 새 헤드가 된다.
		this.setSize(this.size() + 1);												//하나 삽입되었으므로 사이즈도 1 증가.
		return true;																//삽입 성공 true 반환. 
	}
	
	@Override
	public T removeAny() {
		if(this.isEmpty()) {														//리스트가 비어있다면 제거할 수 없다.
			return null;															//따라서 null반환. 
		}
		else {																		//그렇지 않다면, 
			T removedElement = this.head().element();								//제거될 요소는 헤드의 요소다. 제거되기 전에 임시로 저장해준다. 
			this.setHead(this.head().next());										//헤드가 제거될테니 새 헤드는 헤드의 다음 노드이다. 
			this.setSize(this.size() - 1);											//하나 제거되었으므로 사이즈도 1 감소.
			return removedElement;													//제거 성공 제거된 요소 반환.
		}
	}
	
	@Override
	public void reset() {								//사이즈를 0으로, 헤드를 null로 바꿔 기본 생성자가 호출됐을때와 같은 초기상태로 만들어준다. 
		this.setSize(0);;
		this.setHead(null);
	}
	
	//week 05 
	@Override
	public Iterator<T> listIterator(){
		return new IteratorForLinkedList();
	}
	
	private LinkedNode<T> head(){
		return this._head;
	}
	
	private void setHead(LinkedNode<T> newHead) {
		this._head = newHead;
	}
	
	public int size() {
		return this._size;
	}
	
	public void setSize(int newSize) {
		this._size = newSize;
	}
	
	public class IteratorForLinkedList implements Iterator<T> {					//내부 클래스로 구현하는 이유는 상위 클래스의 멤버에 접근할 수 있도록 하기 위함.
		private LinkedNode<T> _nextNode;												//연결리스트의 head는 private이다. iterator를 통해 접근하기 위함이다.
		
		private IteratorForLinkedList() {											//생성시 연결리스트의 헤드를 _nextNode로 갖는다. 즉 iterator의 nextNode는 처음에 연결리스트의 head를 가리킨다.
			this.setNextNode(LinkedList.this.head());								//이후 next함수를 통해 순회한다.
		}
		
		public boolean hasNext() {
			return (this.nextNode() != null);
		}
		
		public T next() {
			T nextElement = this.nextNode().element();								//현재 iterator가 가리키고있는 _next의 요소를 임시로 저장해준다.
			this.setNextNode(this.nextNode().next());								//iterator가 가리키는 node의 다음 노드를 iterator가 가리키는 _nextNode로 설정해준다.
			return nextElement;														//지나간 요소 반환. 
		}
		
		private LinkedNode<T> nextNode(){											
			return this._nextNode;
		}
		
		private void setNextNode(LinkedNode<T> newLinkedNode) {
			this._nextNode = newLinkedNode;
		}
	}
}

