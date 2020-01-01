package graph;

public class UndirectedAdjacencyMatrixGraph<E extends Edge> implements Graph<E> {	//인터페이스 graph를 가지고있으므로 graph에선언한 함수를 모두 구현해줘야함. 

	private static final int EDGE_EXIST = 1;
	private static final int EDGE_NONE = 0;
	
	private int _numberOfVertices;
	private int _numberOfEdges;
	private int[][] _adjacency;					//인접성을 나타낼 2차원 배열
	
	protected UndirectedAdjacencyMatrixGraph() {}									//상속받은 class는 super class의 생성자를 call해야한다. super class의 생성자가 해줄일이 있다면 괜찮지만, 
																					//Weighted의 경우처럼 super class의 생성자에게 바라는게 없는경우는 빈 생성자라도 호출해야한다. 
	public UndirectedAdjacencyMatrixGraph(int givenNumberOfVertices) {
		this.setNumberOfVertices(givenNumberOfVertices);							//vertex는 생성시부터 입력을 받지
		this.setNumberOfEdges(0);													//각 vertex에 연결된 edge들은 처음에 없으므로 0으로 생성된다.
		this.setAdjacency(new int[givenNumberOfVertices][givenNumberOfVertices]);	//행과 열의 개수가 vertex인 2차원 매트릭스 생성 -> 인접성을 나타
		for(int tailVertex = 0; tailVertex < this._numberOfVertices; tailVertex++) {
			for(int headVertex = 0; headVertex < this._numberOfVertices; headVertex++) {
				this.setAdjacencyOfEdgeAsNone(tailVertex, headVertex);				//NONE으로 만들어주는 함수를 새로 만들어 사용한다. 
			}
		}
	}	
	
	@Override
	public int numberOfVertices() {
		return this._numberOfVertices;
	}

	@Override
	public int numberOfEdges() {
		return this._numberOfEdges;
	}
	
	protected void setNumberOfVertices(int newNumberOfVertices) {												//상속는 클래스에서도 함수를 사용할 수 있도록 private에서 protected로 변경해준다. 
		this._numberOfVertices = newNumberOfVertices;
	}
	
	protected void setNumberOfEdges(int newNumberOfEdges) {
		this._numberOfEdges = newNumberOfEdges;
	}
	
	protected int[][] adjacency(){
		return this._adjacency;
	}
	
	protected void setAdjacency(int[][] newAdjacency) {
		this._adjacency = newAdjacency;
	}

	protected int adjacencyOfEdge(int tailVertex, int headVertex) {
		return this.adjacency()[tailVertex][headVertex];
	}
	
	protected void setAdjacencyOfEdgeAs(int tailVertex, int headVertex, int anAdjacencyOfEdge) {				//Edge의 adjacency를 인자로 받아 설정한다. 
		this.adjacency()[tailVertex][headVertex] = anAdjacencyOfEdge;
	}
	
	private void setAdjacencyOfEdgeAsExist(int tailVertex, int headVertex) {									//Edge의 adjacency를 EDGE_EXIST로 설정한다.
		this.setAdjacencyOfEdgeAs(tailVertex, headVertex, UndirectedAdjacencyMatrixGraph.EDGE_EXIST);
	}
	
	protected void setAdjacencyOfEdgeAsNone(int tailVertex, int headVertex) {									//EDGE의 adjacency를 EDGE_NONE으로 설정한다. 
		this.setAdjacencyOfEdgeAs(tailVertex, headVertex, UndirectedAdjacencyMatrixGraph.EDGE_NONE);
	}
	
	protected boolean adjacencyOfEdgeDoesExist(int tailVertex, int headVertex) {
		return (this.adjacency()[tailVertex][headVertex] != UndirectedAdjacencyMatrixGraph.EDGE_NONE);			//EDGE_NONE인면 연결되지 않았다는 의미, 그렇지 않다면 연결되어있다는 의미다.
	}

	@Override
	public boolean vertexDoesExist(int aVertex) {
		return (aVertex >= 0 && aVertex < this.numberOfVertices());							//검사하는 aVertex가 0보다 크고 vertex개수보단 적어야 존재하는 것.
	}
	
	@Override
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex) {
		if(this.edgeIsValid(aTailVertex, aHeadVertex)) {									//인자로 받은 tail과 headVertex가 유효한것인지를 확인해서 유효하다면 
			return this.adjacencyOfEdgeDoesExist(aTailVertex, aHeadVertex);					//tail head사이의 인접성이 있는가를 반환. 
		}																					//vertex가 유효하지 않다면 인접성이 있을 수 없으므로 false를 반환한다.
		return false;
	}

	@Override
	public boolean edgeDoesExist(E anEdge) {												//바로 위에서 구현한 edgeDoesExist를 overloading해서 사용한다.
		if(anEdge != null) {																//인자로 edge를 받아 그 edge의 tail과 head를 이용해 존재하는지 확인. 
			return this.edgeDoesExist(anEdge.tailVertex(), anEdge.headVertex());
		}
		return false;
	}

	@Override
	public boolean edgeIsValid(int aTailVertex, int aHeadVertex) {
		return (this.vertexDoesExist(aTailVertex) && this.vertexDoesExist(aHeadVertex));	//새롭게 구현함. tailVertex와 headVertex가 모두 존제한다면 edge는 유효하다.
	}

	@Override
	public boolean edgeIsValid(E anEdge) {													//바로 위에서 구현한 edgeIsValid를 다른 인자를 받아 처리함.
		if(anEdge != null) {																//다형성을 가지는 함수임. overloading 
			return (this.edgeIsValid(anEdge.tailVertex(), anEdge.headVertex()));			//인자로 받은 edge가 null이 아니라면 그 tail과 headVertex를 통해 유효한지 확인.
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")															//lombok으로 오류 처리. 
	@Override
	public E edge(int aTailVertex, int aHeadVertex) {	
		if(this.edgeDoesExist(aTailVertex, aHeadVertex)) {									//edge가 존재한다
			return (E) new Edge(aTailVertex, aHeadVertex);									//tail과 head로 edge를 만들어서 반환해준다.
		}
		return null;																		//없으면 null반환. 
	} 

	@Override
	public boolean addEdge(E anEdge) {
		if(anEdge != null) {																//인자로 받은 edge가 null은 아닌지?
			if(this.edgeIsValid(anEdge) && !this.edgeDoesExist(anEdge)) {					//아니라면 edge가 유효하면서 이미 존제하지는 않는지?
				int tailVertex = anEdge.tailVertex();
				int headVertex = anEdge.headVertex();
				this.setAdjacencyOfEdgeAsExist(tailVertex, headVertex);						//그렇다면 edge의 연결성을 만들어줘야한다. 
				this.setAdjacencyOfEdgeAsExist(headVertex, tailVertex);						//Undirected그래프를 만들고 있는 것 이기 때문에 tail->head head->tail 모두 존재하는것으로 바꿔준다.
				this.setNumberOfEdges(this.numberOfEdges() + 1);
				return true;																//edge가 추가되었으니 true반환.
			}	
		}
		return false;																		//그렇지 못하다면 edge가 추가되지 못한 것 이므로 false 반환.
	}
}
