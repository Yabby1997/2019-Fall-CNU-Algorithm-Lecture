package graph;

import list.Iterator;

public interface Graph<E> {
	public int numberOfVertices();
	public int numberOfEdges();
	
	public boolean vertexDoesExist(int aVertex);
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex);
	public boolean edgeDoesExist(E anEdge);								//edgeDoesExist함수를 overloading하여 사용한다. (다형성)
	
	public boolean edgeIsValid(int aTailVertex, int aHeadVertex);
	public boolean edgeIsValid(E anEdge);
	
	public E edge(int aTailVertex, int aHeadVertex);
	public boolean addEdge(E anEdge);
	
	public Iterator<E> neighborIteratorOf(int aTailVertex);
}
