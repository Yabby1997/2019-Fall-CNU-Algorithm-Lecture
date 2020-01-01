package list;

public class LinkedStack<E> implements Stack<E> {		//linkedStack은 stack을 상속받는다. 

	private int _size;									//stack의 사이즈와 최상단 노드를 나타낼 멤버 
	private LinkedNode<E> _top;
	
	public LinkedStack() {
		this.setSize(0);
		this.setTop(null);
	}
	
	private LinkedNode<E> top() {
		return this._top;
	}
	
	private void setTop(LinkedNode<E> newTop) {
		this._top = newTop;
	}
	
	@Override
	public void reset() {
		this.setSize(0);
		this.setTop(null);
	}

	@Override
	public int size() {
		return this._size;
	}
	
	public void setSize(int newSize) {
		this._size = newSize;
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
	public boolean push(E anElement) {
		LinkedNode<E> newTop = new LinkedNode<E>(anElement, this.top());		//입력받은 anElement로 새로운 노드를 만드는데, 이 노드는 top위에 위치하게 된다. 
		this.setTop(newTop);													//stack에 삽입되었으므로 top은 newTop이된다. 
		this.setSize(this.size() + 1);											//마찬가지로 삽입되었으므로 크기를 1늘린다.
		return true;															//삽입성공 -> true를 반환. 
	}

	@Override
	public E pop() {
		if(this.isEmpty()) {													//stack이 비어있는 상태라면 pop할 수 없다. 
			return null;	
		}
		else {
			E removedElement = this.top().element();							//스택은 후입선출 선입후출이다. pop하게되면 stack에 있는 요소중 가장 나중에 삽입된 요소인 top이 제거된다. 따라서 반활될요소는 top의 것이다. 
			this.setTop(this.top().next());										//top의 다음 node가 새로운 top이된다.
			this.setSize(this.size() - 1);										//pop되었으므로 사이즈가 1 줄어든다. 
			return removedElement;												//제거 성공한 요소를 반환해준다. 
		}
	}

	@Override
	public E peek() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public IteratorForLinkedStack iterator() {
		return new IteratorForLinkedStack();
	}
	
	public class IteratorForLinkedStack implements Iterator<E> {
		LinkedNode<E> _nextNode;
		
		private IteratorForLinkedStack() {										//생성자가 호출되면 해당 LinkedStack top을 가리키도록 설정됨. 
			this.setNextNode(LinkedStack.this.top());
		}
		private LinkedNode<E> nextNode(){										//iterator가 가리키는 노드 nextNode를 반환해준다. 
			return this._nextNode;
		}
		
		private void setNextNode(LinkedNode<E> aLinkedNode) {					//iterator가 가리키는 nextNode를 설정. 
			this._nextNode = aLinkedNode;
		}
		
		@Override
		public boolean hasNext() {
			return (this.nextNode() != null);									//nextNode가 있다면 true 반환 
		}
		
		@Override
		public E next() {
			E nextElement = this.nextNode().element();							//가리키고있던 nextNode의 요소를 저장.
			this.setNextNode(this.nextNode().next());							//nextNode의 next를 가리킨다.
			return nextElement;													//저장해둔 요소를 반환. 
		}		
	}
}
