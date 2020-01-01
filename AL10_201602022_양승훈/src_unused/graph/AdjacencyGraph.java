package graph;

import list.Iterator;

public abstract class AdjacencyGraph<E extends Edge> implements Graph<E> {		//모든 종류의 그래프가 동일한 코드상에서 돌아갈 수 있도록 해주기위해 공통의 추상 클래스를 만들어준다. 
	
	protected static final int EDGE_EXIST = 1;
	protected static final int EDGE_NONE = 0;
		
	private int _numberOfVertices;
	private int _numberOfEdges;
	
	//아래 함수들은 모든 graph에서 동일하게 사용할 수 있는 함수들이다. 
	@Override
	public int numberOfVertices() {
		return this._numberOfVertices;
	}

	@Override
	public int numberOfEdges() {
		return this._numberOfEdges;
	}
	
	protected void setNumberOfVertices(int newNumberOfVertices) {
		this._numberOfVertices = newNumberOfVertices;
	}
	
	protected void setNumberOfEdges(int newNumberOfEdges) {
		this._numberOfEdges = newNumberOfEdges;
	}

	@Override
	public boolean vertexDoesExist(int aVertex) {
		return ((aVertex >= 0) && (aVertex < this.numberOfVertices()));											//vertex번호가 음수이면 안된다. 그리고 주어진 vertex의 개수를 초과해서도 안된다. 
	}

	@Override
	public boolean edgeIsValid(int aTailVertex, int aHeadVertex) {
		return (this.vertexDoesExist(aTailVertex) && this.vertexDoesExist(aHeadVertex));						//tail과 head가 모두 존재하는 vertex여야만 edge도 유효할 수 있다. 
	}

	@Override
	public boolean edgeIsValid(E anEdge) {
		if(anEdge != null) {
			return (this.vertexDoesExist(anEdge.tailVertex()) && this.vertexDoesExist(anEdge.headVertex()));	//위에서 구현한 edgeIsValid와 동일한 기능을하지만 다른 인자를 받아서한다. overloading 
		}
		return false;
	}
	
	//abstract class 이므로 Graph의 모든 함수를 여기서 구현할 필요는 없다. 해당 추상 클래스를 상속받는 클래스들에서 직접 구현하여 사용한다. 
	@Override
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edgeDoesExist(E anEdge) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E edge(int aTailVertex, int aHeadVertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addEdge(E anEdge) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> neighborIteratorOf(int aTailVertex) {
		// TODO Auto-generated method stub
		return null;
	}

}
