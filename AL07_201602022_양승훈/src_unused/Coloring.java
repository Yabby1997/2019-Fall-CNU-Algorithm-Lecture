
public class Coloring {
	private AdjacencyMatrixGraph _graph;
	private VertexColor[] _vertexColors;				//enum으로 생성해줬던 그 VertexColor. 3가지값을 갖는다. NONE, RED, BLUE
	private int _startingVertex;
	private LinkedList<Edge> _sameColorEdges;			//template을 이용해 만들었던 LinkedList. 
	
	public Coloring(AdjacencyMatrixGraph givenGraph) {		//생성자는 그래프를 인자로 받는다.
		this.setGraph(givenGraph);							//인자로 받은 그래프를 Coloring의 그래프로 해주고 
		this.setVertexColors(new VertexColor[this.graph().numberOfVertices()]);		//vertex의 색들을 나타내는 vertexcolor배열을 인자로받은 graph의 vertex의 개수만큼의 크기로 선언해준다.
		for(int vertex = 0; vertex < this.graph().numberOfVertices(); vertex++) {	//VertexColor의 배열을 for loop를 돌며 모두 NONE으로 초기화해준다. 초기엔 모든 Vertex들이 색을 갖지 않는다.
			this.setVertexColor(vertex, VertexColor.NONE);
		}
		this.setSameColorEdges(new LinkedList<Edge>());		//같은색의 edge의 연결 리스트를 만들어 넣어준다.
		this.setStartingVertex(0);							//첫번째 vertex가 시작점.
	}
	
	public void runColoring() {
		this.paintColorsOfVertices();						//Vertex들에 색을 칠하고 
		this.findSameColorEdges();							//동일한 색이 칠해진 edge가 있는지 찾는다.
	}
	
	public void paintColorsOfVertices() {
		this.setVertexColor(this.startingVertex(), VertexColor.RED);																	//시작 vertex를 빨간색으로 칠하고 시작. 
		Queue<Integer> breadthFirstSearchQueue = new CircularQueue<Integer>(this.graph().numberOfVertices());							//원형큐인 너비우선탐색 큐를 만들어 그 크기(capacity)를 그래프의 vertex수로 초기화해준다. 
		breadthFirstSearchQueue.add(this.startingVertex());																				//너비우선탐색큐(BFS)에 startingVertex를 삽입한다. 
		while(! breadthFirstSearchQueue.isEmpty()) {																					//너비 우선탐색큐가 텅텅 빌 때까지 while loop (BFS) 
			int tailVertex = breadthFirstSearchQueue.remove();																			//너비 우선탐색 큐의 맨 앞을 제거하고 그 요소를 tailVertex로 받음. 
			VertexColor headVertexColor = ((this.vertexColor(tailVertex) == VertexColor.RED) ? VertexColor.BLUE : VertexColor.RED);		//나온 vertexColor가 RED이면 BLUE를, 그렇지않다면 RED를 임시 저장. 다음 vertex에 칠할 색이다. 
			for(int headVertex = 0; headVertex < this.graph().numberOfVertices(); headVertex++) {										//모든 vertex를 순회하며 
				Edge visitedEdge = new Edge(tailVertex, headVertex);																	//그 vertex와 tailVertex의 edge를 만들고, 
				if(this.graph().edgeDoesExist(visitedEdge)) {																			//그 edge가 실제로 graph에 존제한다면, tailVertex와 headVertex는 edge로 연결이 돼있다는 뜻. 
					if(this.vertexColor(headVertex) == VertexColor.NONE) {																//edge로 연결 돼있는데 headVertex가 색이 칠해져있지 않다 -> 방문된적이 없다면 
						this.setVertexColor(headVertex, headVertexColor);																//headVertex의 색을 아까 임시저장해둔 색으로 칠하고 
						breadthFirstSearchQueue.add(headVertex);																		//headVertex를 BFS에 삽입해서 while loop 반복
					}
				}
			}
		}
	}
	
	public void findSameColorEdges() {
		for(int tailVertex = 0; tailVertex < this.graph().numberOfVertices(); tailVertex++) {											//2중 for loop를 돌며 모든 vertex간의 edge를 확인한다. 
			for(int headVertex = 0; headVertex < this.graph().numberOfVertices(); headVertex++) {
				Edge visitingEdge = new Edge(tailVertex, headVertex);																	//임시로 만들어낸 vertex간의 edge, visitingEdge 
				if(this.graph().edgeDoesExist(visitingEdge)) {																			//visitingEdge가 실제로 graph상에 존제하는 edge이고 
					if(this.vertexColor(tailVertex) == this.vertexColor(headVertex)) {													//tailVertex와 headVertex의 색이 같다면 이는 같은 색을 연결한 edge이다. 
						this.sameColorEdges().add(visitingEdge);																		//같은색을 연결하는 edge들의 연결리스트인 sameColorEdges에 해당 edge를 삽입해준다. 
					}
				}
			}
		}
	}
	
	private AdjacencyMatrixGraph graph() {
		return this._graph;
	}
	
	private void setGraph(AdjacencyMatrixGraph newGraph) {
		this._graph = newGraph;
	}
	
	private int startingVertex() {
		return this._startingVertex;
	}
	
	private void setStartingVertex(int newVertex) {
		this._startingVertex = newVertex;
	}
	
	private VertexColor[] vertexColors() {
		return this._vertexColors;
	}
	
	private void setVertexColors(VertexColor[] newVertexColors) {
		this._vertexColors = newVertexColors;
	}
	
	public VertexColor vertexColor(int aVertex) {		//aVertex번째의 색이 뭔지 반환. 
		return this.vertexColors()[aVertex];			//_vertexColors의 aVertex번째 요소를 반환.
	}
	
	private void setVertexColor(int aVertex, VertexColor newColor) {			//aVertex번째의 색을 newColor로 
		this.vertexColors()[aVertex] = newColor;								//반환할때와 동일한 방식으로 접근해서 요소를 넣어줌. 
	}
	
	public LinkedList<Edge> sameColorEdges(){
		return this._sameColorEdges;
	}
	
	private void setSameColorEdges(LinkedList<Edge> newLinkedList) {
		this._sameColorEdges = newLinkedList;
	}
}
