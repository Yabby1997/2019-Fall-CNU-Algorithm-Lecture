
public interface Queue<T> {							//제네릭을 이용해 코드의 재활용성을 늘린다. 제너릭 타입 T에 무엇이 오느냐에 따라 해당 인터페이스가 재사용 될 수 있다.
	public void reset();
	public int size();
	public boolean isEmpty();
	public boolean isFull();
	public boolean add(T anElement);
	public T remove();
}
