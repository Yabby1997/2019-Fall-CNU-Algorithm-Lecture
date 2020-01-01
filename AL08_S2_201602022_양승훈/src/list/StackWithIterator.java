package list;

public interface StackWithIterator<T> extends Stack<T> {
	public Iterator<T> iterator();									//이터레이터를 가져야하는 스택을 위해 인터페이스를 만들어 iterator호출 함수를 선언해준다. 
}
