package shortestPaths;

import app.AppView;
import graph.AdjacencyGraph;
import graph.SupplementForWeightedGraph;
import graph.WeightedEdge;
import list.LinkedStack;
import list.Iterator;

public class ShortestPaths<WE extends WeightedEdge> {
	private static final int UNDEFINED_SOURCE = -1;						//출발 vertex가 정의되지 않은 상태를 나타내준다. 
	private static final boolean DEBUG_MODE = true;						//디버그 메시지를 출발할땐 true, 아닐땐 false를 이용한다. 
	
	private static void showDebugMessage(String aMessage) {				//디버그 모드가 TRue인 경우 디버그 메시지를 출력해주기 위한 함수를 선언해준다. 
		if(DEBUG_MODE) {
			AppView.outputDebugMessage(aMessage);
		}
	}
	
	private AdjacencyGraph<WE> _graph;									//추상 클래스 adjacencyGraph를 상속받는 모든 클래스가 올 수 있음. 
	private int _source;
	private int[] _path;
	private int[] _distance;
	
	//생성자 
	public ShortestPaths() {							
		this.setGraph(null);
		this.setSource(ShortestPaths.UNDEFINED_SOURCE);
		this.setPath(null);
		this.setDistance(null);
	}
	
	//getter/setter
	private AdjacencyGraph<WE> graph(){
		return this._graph;
	}
	
	private void setGraph(AdjacencyGraph<WE> newGraph) {
		this._graph = newGraph;
	}
	
	private int source() {
		return this._source;
	}
	
	private void setSource(int newSource) {
		this._source = newSource;
	}
	
	private int[] path() {
		return this._path;
	}
	
	private void setPath(int[] newPath) {
		this._path = newPath;
	}
	
	private int[] distance() {
		return this._distance;
	}
	
	private void setDistance(int[] newDistance) {
		this._distance = newDistance;
	}
	
	private int chooseVertexForNextShortestPath(boolean[] found) {									//발견되지 않은 vertex중에서 경로가 가장 짧은것을 찾는다.
		int currentForChoice = 0;
		while(currentForChoice < this.graph().numberOfVertices() && found[currentForChoice]) {		//확인하는 vertex가 유효한 vertex이고 찾아진 전례가 있다면 다음 vertex를 확인해야한다.
			currentForChoice++;																		//확인하는 vertex를 다음 것으로 옮긴다.
		}		
		for(int nextForChoice = currentForChoice + 1; nextForChoice < this.graph().numberOfVertices(); nextForChoice++) { 	//확인하는 vertex 뒤의 vertex들을 모두 훑으면서 
			if((!found[nextForChoice]) && (this.distance()[currentForChoice] > this.distance()[nextForChoice])) {			//방문한적 없고 거리도 더 짧은게 발견되면 그거 기준으로 다시 반복하며 제일 작은걸 찾는다. 
				currentForChoice = nextForChoice;
			}
		}
		return currentForChoice;																	//최단경로에 있는 vertex를 나타내는게 currentForChoice가 된다. 
	}
	
	private void debug_showIteration(int iteration, int u, boolean[] found) {											//디버그용 출력 함수. 알고리즘의 과정을 보여주는 역할을 한다.
		ShortestPaths.showDebugMessage("[Debug] Iteration_" + iteration + ": (u=" + u + "):");
		for(int w = 0; w < this.graph().numberOfVertices(); w++) {
			ShortestPaths.showDebugMessage(" d[" + w + "]=");
			if(this.distance()[w] >= Integer.MAX_VALUE / 2) {										//distance가 integer.MAX_VALUE 보다크다는건 연결되어있지 않다는 것. PDF에서 잘못 되어있는것 같아 수정 
				ShortestPaths.showDebugMessage("∞");
			}
			else {
				ShortestPaths.showDebugMessage("" + this.distance()[w]);							//그렇지 않다면 연결된 것이므로 distance를 출력 
			}
		}
		ShortestPaths.showDebugMessage("\n");
	}
	
	@SuppressWarnings("unchecked")
	public boolean solve(AdjacencyGraph<WE> aGraph, int aSource) {									//최단경로를 찾는 함수 
		if(aGraph == null || aGraph.numberOfVertices() < 2) {										//그래프가 null이거나 vertex가 2개보다 작다 -> 성립할 수 없음 
			return false;
		}
		if(! aGraph.vertexDoesExist(aSource)) {														//인자로 전달받은 source vertex가 graph상에 존재하지 않는 경우도 성립할 수 없다. 
			return false;
		}
		
		this.setGraph(aGraph);																		//인자로 입력받은 graph와 source를 이용해 필드를 설정해준다. 
		this.setSource(aSource);
		this.setDistance(new int[this.graph().numberOfVertices()]);
		this.setPath(new int[this.graph().numberOfVertices()]);
		boolean[] found = new boolean[this.graph().numberOfVertices()];								//방문 여부를 저장할 boolean 배열 found도 graph의 vertex만큼의 크기를 갖도록 만들어준다. 
		for(int vertex = 0; vertex < this.graph().numberOfVertices(); vertex++) {
			found[vertex] = false;																	//모든 vertex를 순회하며 false로 초기화. 
			this.distance()[vertex] = ((SupplementForWeightedGraph<WE>)this.graph()).weightOfEdge(this.source(), vertex);		//distance배열에는 source로부터 해당 vertex까지의 거리를 갖도록 초기화. weightOfEdge함수를 사용하기위해 형변환 
			this.path()[vertex] = this.source();																				//어짜피 연결 안돼있으면 INFINITE로 입력될거다.
		}
		found[this.source()] = true;																							//시작 위치인 SOURCE는 처음부터 발견된것과 같다 
		this.distance()[this.source()] = 0;																						//self 이기때문에 거리는 0 
		this.path()[this.source()] = -1;																						//시작점이기때문에 경로는 -1
		
		ShortestPaths.showDebugMessage("\n[Debug] 최단경로 찾기 반복 과정 :\n");
		this.debug_showIteration(0, this.source(), found);																			//source->0번째 경로 찾는과정 출력 
		
		//pdf에서 사용한 알파벳 변수명을 사용하면 너무 헷갈려서 변수명을 변경해서 구현 
		for(int pathFindingIteration = 1; pathFindingIteration < this.graph().numberOfVertices() - 1; pathFindingIteration++) {		//source부터 시작했고, unique한경로 즉, vertex개수 -1개의 edge를 찾아야한다. 
			int vertexForNextShortestPath = chooseVertexForNextShortestPath(found);													//found 배열을 이용해 방문하지 않은 것 중 가장 짧은 거리에 있는 vertex를 선택. 
			found[vertexForNextShortestPath] = true;																				//해당 vertex를 선택할것이므로 찾아진거로 변경. 		
			
			Iterator<WE> iterator = this.graph().neighborIteratorOf(vertexForNextShortestPath);										//해당 vertex의 인접한 vertex들을 찾아야하므로 그걸 기준으로 이터레이터생성 
			
			while(iterator.hasNext()) {																								//이터레이터가 기리키는게 있다면,  (순회)
				WE edge = iterator.next();						
				int headVertexOfNeighborEdge = edge.headVertex();																	//이웃하는 headVertex를 통해 
				if(!found[headVertexOfNeighborEdge]) {																				//아직 방문되지 않은, 즉 최단거리가 찾아지지 않은 headvertex에 한해서 
					if(this.distance()[headVertexOfNeighborEdge] > this.distance()[vertexForNextShortestPath] + edge.weight()) {	//해당 vertex까지의 거리가 방금까지 찾은거에 weight를 더하는거보다 크면 
						this.distance()[headVertexOfNeighborEdge] = this.distance()[vertexForNextShortestPath] + edge.weight();		//그냥 방금 찾은거에서 거쳐가는걸로 거리를 주고 
						this.path()[headVertexOfNeighborEdge] = vertexForNextShortestPath;											//짧은 거리가 찾아졌으니 경로는 방금 찾은 vertex를 거쳐가는것이다. 
					}
				}
			}
			this.debug_showIteration(pathFindingIteration, vertexForNextShortestPath, found);
		}		
		ShortestPaths.showDebugMessage("[Debug] 반복 과정 보여주기를 마칩니다.\n");
		return true;
	}

	public int minCostOfPathToDestination(int aDestination) {		//source로부터 aDestination까지의 최단거리는 distance배열의 aDestination에 들어있다. 
		return this.distance()[aDestination];
	}
	
	public LinkedStack<Integer> pathToDestination(int aDestination){								//경로를 역 추적하며 지나온 경로를 스택에 담아 스택을 반환해준다. 
		LinkedStack<Integer> pathStack = new LinkedStack<Integer>();
		for(int vertex = aDestination; vertex >= 0; vertex = this.path()[vertex]) {
			pathStack.push(Integer.valueOf(vertex));
		}
		return pathStack;
	}
}
