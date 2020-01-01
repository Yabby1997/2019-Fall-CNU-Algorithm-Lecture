package list;

public interface Stack<T> {				//인터페이스 stack을 정의해 Stack을 구현할 때 함께 구현해주어야 하는 함수들을 선언해준다. 
	public void reset();
	public int size();
	
	public boolean isEmpty();
	public boolean isFull();
	
	public boolean push(T anElement);
	public T pop();
	
	public T peek();
}
