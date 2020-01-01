
public class AdjacencyMatrixGraph {
	
	private static final int EDGE_EXIST = 1;
	private static final int EDGE_NONE = 0;
	
	private int _numberOfVertices;
	private int _numberOfEdges;
	private int[][] _adjacency;					//인접성을 나타낼 2차원 배열
	
	public AdjacencyMatrixGraph(int givenNumberOfVertices) {
		this.setNumberOfVertices(givenNumberOfVertices);							//vertex는 생성시부터 입력을 받지
		this.setNumberOfEdges(0);													//각 vertex에 연결된 edge들은 처음에 없으므로 0으로 생성된다.
		this.setAdjacency(new int[givenNumberOfVertices][givenNumberOfVertices]);	//행과 열의 개수가 vertex인 2차원 매트릭스 생성 -> 인접성을 나타
		for(int tailVertex = 0; tailVertex < this._numberOfVertices; tailVertex++) {
			for(int headVertex = 0; headVertex < this._numberOfVertices; headVertex++) {
				this.adjacency()[tailVertex][headVertex] = AdjacencyMatrixGraph.EDGE_NONE;		//adjacency 함수가 _adjacency를 반환, [tailVertex][headVertex]에 접근해 Edge_Non을 입력
				//static 변수를가져오는거기때문에 this는 사용이 불가능하다. this는 객체를 가리키는거고, static은 객체 없이 사용하는것.
			}
		}
	}
	
	private void setNumberOfVertices(int newNumberOfVertices) {
		this._numberOfVertices = newNumberOfVertices;
	}
	
	private void setNumberOfEdges(int newNumberOfEdges) {
		this._numberOfEdges = newNumberOfEdges;
	}
	
	private int[][] adjacency(){
		return this._adjacency;
	}
	
	private void setAdjacency(int[][] newAdjacency) {
		this._adjacency = newAdjacency;
	}

	private boolean adjacencyOfEdgeDoesExist(int tailVertex, int headVertex) {
		return (this.adjacency()[tailVertex][headVertex] != AdjacencyMatrixGraph.EDGE_NONE);			//EDGE_NONE인면 연결되지 않았다는 의미, 그렇지 않다면 연결되어있다는 의미다.
	}
		
	public int numberOfVertices() {
		return this._numberOfVertices;
	}
	
	public int numberOfEdges() {
		return this._numberOfEdges;
	}
	
	public boolean vertexDoesExist(int aVertex) {
		return (aVertex >= 0 && aVertex < this.numberOfVertices());			//검사하는 aVertex가 0보다 크고 vertex개수보단 적어야 존재하는 것.
	}
	
	public boolean edgeDoesExist(Edge anEdge) {
		if(anEdge != null) {												//인자로 받은 edge가 null은 아닌지?
			int tailVertex = anEdge.tailVertex();							//아니라면 인자로 받은 anEdge의 tailVertex를 저장 
			int headVertex = anEdge.headVertex();							//headVertex도 저장 
			if(this.vertexDoesExist(tailVertex) && this.vertexDoesExist(headVertex)){		//저장된 tail, head vertex가 유효한지?
				return (this.adjacencyOfEdgeDoesExist(tailVertex, headVertex));				//유효하다면 인접한지 안한지 반환
			}				
		}
		return false;
	}
	
	public boolean addEdge(Edge anEdge) {
		if(anEdge != null) {												//인자로 받은 edge가 null은 아닌지?
			int tailVertex = anEdge.tailVertex();							//아니라면 인자로 받은 anEdge의 tailVertex를 저장 
			int headVertex = anEdge.headVertex();							//headVertex도 저장 
			if(this.vertexDoesExist(tailVertex) && this.vertexDoesExist(headVertex)) {		//vertex가 유효한지?
				if(!this.adjacencyOfEdgeDoesExist(tailVertex, headVertex)) {				//edge가 이미 존재하지는 않는지?
					this.adjacency()[tailVertex][headVertex] = AdjacencyMatrixGraph.EDGE_EXIST;		//edge가 삽입될 수 있다면 인접성을 나타내는 배열인 _adjacency배열에 EDGE_EXIST를 삽입한다.
					this.adjacency()[headVertex][tailVertex] = AdjacencyMatrixGraph.EDGE_EXIST;		//무방향성 그래프(undirected graph)는 tail과 head가 바뀌어도 되므로 diagonally 대칭하다. 따라 대칭되는 위치에도 삽입해줘야 한다.
					//this.adjacency()가 _adjacency를 반환, 거기에 인덱스를 이용해서 각 요소에 접근한다. 
				}	
			}
		}
		return false;
	}
}
