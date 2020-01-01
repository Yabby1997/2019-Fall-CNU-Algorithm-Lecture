package list;

public abstract class List<T> {
	public abstract int size();
	
	public abstract boolean isEmpty();
	public abstract boolean isFull();
	
	public abstract boolean add(T anElement);
	public abstract T removeAny();
	
	public abstract void reset();

	public abstract Iterator<T> listIterator();
}
