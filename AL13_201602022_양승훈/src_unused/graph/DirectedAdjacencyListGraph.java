package graph;

import list.LinkedList;
import list.Iterator;

public class DirectedAdjacencyListGraph<E extends Edge> extends AdjacencyGraph<E> {		//추상 클래스로 만든 AdjacencyGraph를 상속받는다. 
	private LinkedList<E>[] _adjacency;													//각 vertex로부터의 인접한 vertex들을 나타낼 LinkedList들의 배열 
	
	@SuppressWarnings("unchecked")														//컴파일러에게 형변환에 문제가 없었음을 알려준다. 
	public DirectedAdjacencyListGraph(int givenNumberOfVertices) {
		this.setNumberOfVertices(givenNumberOfVertices);
		this.setAdjacency(new LinkedList[givenNumberOfVertices]);						//generic type E가 생성자에 쓰일 수 없음 따라서 오류가 발생했다. 
		for(int tailVertex = 0; tailVertex < this.numberOfVertices(); tailVertex++) {	//각 Vertex에 대해 인접한 Vertex를 나타낼 linkedList가 하나씩 있다. 
			this.adjacency()[tailVertex] = new LinkedList<E>();
		}
	}
	
	protected LinkedList<E>[] adjacency(){
		return this._adjacency;
	}
	
	protected void setAdjacency(LinkedList<E>[] newAdjacency) {
		this._adjacency = newAdjacency;
	}
	
	protected LinkedList<E> neighborListOf(int aTailVertex){								//인자로 받은 vertex번호를 통해 해당 vertex의 인접성 리스트를 얻는다 (edge
		return this.adjacency()[aTailVertex];
	}
	
	protected int adjacencyOfEdge(int aTailVertex, int aHeadVertex) {				
		if(this.vertexDoesExist(aTailVertex) && this.vertexDoesExist(aHeadVertex)) {		//tail과 head 모두 존재하는 vertex라면 
			Iterator<E> iterator = this.neighborIteratorOf(aTailVertex);					//tail을 통해 iterator를 받아옴 
			while(iterator.hasNext()) {														//다음 요소가 존재한다면 
				E neighborEdge = iterator.next();											//다음요소 즉, 인접한 엣지의 리스트의 다음 요소를 neighborEdge에 저장 
				if(aHeadVertex == neighborEdge.headVertex()) {								//neighborEdge의 headVertex가 인자로받았던 headVertex와 같다면 해당 vertex간에는 인접성이 있다.
					return AdjacencyGraph.EDGE_EXIST;										//인접성이 있음을 반환해준다. weighted Edge를 사용하게되면 NONE,EXIST대신 cost를 반환해준다. 
				}
			}
		}
		return AdjacencyGraph.EDGE_NONE;
	}
	
	@Override
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex) {
		return (this.adjacencyOfEdge(aTailVertex, aHeadVertex) != AdjacencyGraph.EDGE_NONE);	//인접성이 존재하지않음이 아니라면 존재하는것 
	}
	
	@Override
	public boolean edgeDoesExist(Edge anEdge) {
		if(anEdge != null) {			
			return (this.adjacencyOfEdge(anEdge.tailVertex(), anEdge.headVertex())) != AdjacencyGraph.EDGE_NONE;	//함수 오버로딩 
		}
		return false;
	}
	
	@Override
	public E edge(int aTailVertex, int aHeadVertex) {										//adjacencyOfEdge함수와 전체적인 구조가 동일하다. 
		if(this.vertexDoesExist(aTailVertex)) {												//단 존재하면 그 엣지를 반환해주고, 그렇지 않다면 null을 반환한다는점에서 다르다. 
			Iterator<E> iterator = this.neighborIteratorOf(aTailVertex);
			while(iterator.hasNext()) {
				E neighborEdge = iterator.next();
				if(aHeadVertex == neighborEdge.headVertex()) {
					return neighborEdge;
				}
			}
		}
		return null;
	}

	@Override
	public boolean addEdge(E anEdge) {
		if((this.edgeIsValid(anEdge) && !(this.edgeDoesExist(anEdge)))) {					//추가하려는 edge가 valid하고 이미 존재하는 edge가 아니어야 추가할 수 있다. 
			this.neighborListOf(anEdge.tailVertex()).add(anEdge);							//추가할 수 있으므로, adjacency를 나타내는 리스트의 배열의 tailvertex번째 요소에 anEdge를 추가해준다 (tailVertex의 인접성 추가) 
			this.setNumberOfEdges(this.numberOfEdges() + 1);								//추가 됐으므로 EDGE의 개수도 하나 크게 설정해준다. 
			return true;
		}
		return false;
	}
	
	@Override
	public Iterator<E> neighborIteratorOf(int aTailVertex){				
		if(this.vertexDoesExist(aTailVertex)) {												//vertex가 존재하는거라면 
			return (Iterator<E>) this.adjacency()[aTailVertex].listIterator();				//해당 VErtex의 list의 LIstIterator를 반환한다. 
		}
		return null;
	}
}
