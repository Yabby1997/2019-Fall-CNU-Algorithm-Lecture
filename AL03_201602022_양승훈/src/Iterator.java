
public interface Iterator<E> {						//반복자를 구현한다. Generic 자료형 E를 받음
	public boolean hasNext();
	public E next();
}
