package list;

public class LinkedNode<T> {
	private T _element;
	private LinkedNode<T> _next;
	
	public LinkedNode() {												//기본 생성자와, 
		this.setElement(null);
		this.setNext(null);
	}
	
	public LinkedNode(T givenElement, LinkedNode<T> givenNext) {		//인자로 값을 받는 생성자를 구현 
		this.setElement(givenElement);
		this.setNext(givenNext);
	}
	
	public T element() {
		return this._element;
	}
	
	public void setElement(T newElement) {
		this._element = newElement;
	}
	
	public LinkedNode<T> next(){
		return this._next;
	}
	
	public void setNext(LinkedNode<T> newNext) {
		this._next = newNext;
	}
}
