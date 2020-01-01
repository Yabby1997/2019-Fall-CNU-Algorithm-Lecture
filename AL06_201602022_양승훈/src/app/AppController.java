package app;

import graph.AdjacencyGraph;
import graph.WeightedDirectedAdjacencyListGraph;
import graph.WeightedEdge;
import shortestPaths.ShortestPaths;
import list.LinkedStack;
import list.Iterator;

public class AppController {
	
	private AdjacencyGraph<WeightedEdge> _graph;
	private ShortestPaths<WeightedEdge> _shortestPaths;			//최단경로를 찾기위해 멤버로 갖는다. 
	
	public AppController() {
		this.setGraph(null);
		this.setShortestPaths(new ShortestPaths<WeightedEdge>());
	}
	
	//getter setter
	private AdjacencyGraph<WeightedEdge> graph() {
		return this._graph;
	}
	
	private void setGraph(AdjacencyGraph<WeightedEdge> newGraph) {
		this._graph = newGraph;
	} 
	
	public ShortestPaths<WeightedEdge> shortestPaths(){
		return this._shortestPaths;
	}
	
	public void setShortestPaths(ShortestPaths<WeightedEdge> newShortestPaths) {
		this._shortestPaths = newShortestPaths;
	}
	
	private void inputAndMakeGraph() {
		AppView.outputLine("> 입력할 그래프의 vertex 수와 edge 수를 먼저 입력해야 합니다 : ");
		int numberOfVertices = this.inputNumberOfVertices();			//AppView에 있는 함수와 이름이 동일하지만, AppView에 있는 함수는 입력을 받는것이고, 유효성의 판단을 해주는 함수를 여기서 재정의해준다.
		this.setGraph(new WeightedDirectedAdjacencyListGraph<WeightedEdge>(numberOfVertices));		//graph는 입력받은  만큼의 vertex를 갖는다. 입력받은 vertex만큼의 행과 열을 가져서 각 요소가 인접성을 나타냄.
		
		int numberOfEdges = this.inputNumberOfEdges();		//연결할 edge의 개수를 입력받는다.
		AppView.outputLine("");
		AppView.outputLine("> 이제부터 edge를 주어진 수 만큼 입력합니다.");
		
		int edgeCount = 0;
		while(edgeCount < numberOfEdges) {							//for문을 쓰면 안되는 이유는? -> for문을 쓰게 되면 조건부로 증감식을 실행시킬 수 없다. 즉 이미 그래프에 있는 edge가 입력되더라도 edgeCount가 ++되기 때문?
			WeightedEdge edge = this.inputEdge();
			if(this.graph().edgeDoesExist(edge)) {
				AppView.outputLine("(오류) 입력된 edge (" + edge.tailVertex() + ", " + edge.headVertex() + ", (" + edge.weight() + ")) 는 그래프에 이미 존재합니다.");	//이미 그래프에 존재하는 edge를 추가하려고 하면 카운트하지 말고 반복.
			}
			else {
				edgeCount++;
				this.graph().addEdge(edge);
				AppView.outputLine("! 새로운 edge (" + edge.tailVertex() + ", " + edge.headVertex() + ", (" + edge.weight() + ")) 가 그래프에 삽입되었습니다.");			//그래프에 없는 edge를 추가하라고 하면 카운트하고 반복.	
			}
		}
	}
	
	private WeightedEdge inputEdge() {
		do {																			//do while문을 사용하는 이유는?
			AppView.outputLine("- 입력할 edge의 두 vertex와 cost 차례로 입력해야 합니다 : ");
			int tailVertex = AppView.inputTailVertex();
			int headVertex = AppView.inputHeadVertex();
			int cost = AppView.inputCost();
			if(this.graph().vertexDoesExist(tailVertex) && this.graph().vertexDoesExist(headVertex)) {		//꼬리와 머리 vertex가 모두 존재할 때
				if(tailVertex == headVertex) {																//꼬리가 머리랑 같다면 self 오류메시지를 출력
					AppView.outputLine("[오류] 두 vertex 번호가 동일합니다.");
				}
				else {													//두 vertex 번호가 같지 않다. 즉, self가 아니라면 유효한 edge이다.
					return (new WeightedEdge(tailVertex, headVertex, cost));			//꼬리와 머리 vertex를 연결하고 cost만큼의 weight을 갖는 weighted edge를 생성해서 반환한다.
				}
			}
			else {	
				if(!this.graph().vertexDoesExist(tailVertex)) {			//tail vertex가 그래프상에 존재하지 않는다면 edge를 생성할 수 없다. 오류메시지 출력 
					AppView.outputLine("[오류] 존재하지 않는 tail vertex 입니다 : " + tailVertex);
				}
				if(!this.graph().vertexDoesExist(headVertex)) {			//head vertex가 그래프상에 존재하지 않는다면 edge를 생성할 수 없다. 오류메시지 출력 
					AppView.outputLine("[오류] 존재하지 않는 head vertex 입니다 : " + headVertex);
				}
				if(cost < 0) {											//입력받은 cost(weight)이 음수일 경우 edge를 생성할 수 없다. 오류메시지 출력 
					AppView.outputLine("[오류] edge의 비용은 양수여야 합니다 : " + cost);
				}
			}
		}
		while(true);
	}
	
	private int inputNumberOfVertices() {
		int numberOfVertices = AppView.inputNumberOfVertices();
		while(numberOfVertices <= 0) {								//while문을 이용해 입력받은 값이 조건에 안맞으면 오류 메시지를 출력하고 입력을 다시받도록 함.
			AppView.outputLine("[오류] vertex 수는 0 보다 커야 합니다 : " + numberOfVertices);	
			numberOfVertices = AppView.inputNumberOfVertices();
		}
		return numberOfVertices;
	}
	
	private int inputNumberOfEdges() {
		int numberOfEdges = AppView.inputNumberOfEdges();
		while(numberOfEdges <= 0) {
			AppView.outputLine("[오류] edge 수는 0 보다 커야 합니다 : " + numberOfEdges);
			numberOfEdges = AppView.inputNumberOfEdges();
		}
		return numberOfEdges;		
	}
	
	private int inputSourceVertex() {
		int sourceVertex = AppView.inputSourceVertex();													//AppView에 선언한 함수를 통해 source vertex를 일단 입력받고, 
		while(! this.graph().vertexDoesExist(sourceVertex)) {											//유효한 vertex입력이 들어올때까지 반복한다. 
			AppView.outputLine("[오류] 입력된 출발 vertex는 존재하지 않습니다 : " + sourceVertex);
			sourceVertex = AppView.inputSourceVertex();
		}
		return sourceVertex;																			//최종적으로 입력된 source vertex를 반환해준다. 
	}
	
	private void solveAndShowShortestPaths() {
		AppView.outputLine("");
		AppView.outputLine("> 주어진 그래프에서 최단 경로를 찾습니다 :");
		if(this.graph().numberOfVertices() <= 1) {
			AppView.outputLine("[오류] vertex 수 (" + this.graph().numberOfVertices() + ")가 너무 적어서, 최단경로 찾기를 하지 않습니다. 2 개 이상이어야 합니다.");		//vertex가 2개는 되어야 경로가 생긴다. 
		}
		else {
			AppView.outputLine("> 출발점을 입력해야 합니다 : ");
			int sourceVertex = this.inputSourceVertex();												//sourceVertex를 입력받고 
			if(this.shortestPaths().solve(this.graph(), sourceVertex)) {								//그거로 최단경로가 찾아지면, 
				AppView.outputLine("");
				AppView.outputLine("> 최단 경로 비용은 다음과 같습니다 : ");
				AppView.outputLine("출발점 = " + sourceVertex + " : ");
				for(int destination = 0; destination < this.graph().numberOfVertices(); destination++) {
					if(destination != sourceVertex) {
						AppView.output("   [목적점 = " + destination + "]  ");
						AppView.output("최소비용 = " + this.shortestPaths().minCostOfPathToDestination(destination) + ", ");		//solve가 돌아간 이후에는 각 vertex에 대한 최단거리가 출력됨 
						AppView.output("경로 : ");
						LinkedStack<Integer> pathToDestination = this.shortestPaths().pathToDestination(destination);			//각 vertex의 경로를 역추적해 stack에 담아둔것을 반환. 
						LinkedStack<Integer>.IteratorForLinkedStack iterator = pathToDestination.iterator();
						while(iterator.hasNext()) {																				//반환된 경로의 stack을 iterator돌리며 경로 출력. 
							AppView.output(" -> " + iterator.next());
						}
						AppView.outputLine("");
					}
				}
			}
			else {
				AppView.outputLine("[오류] 최단경로 찾기에 실패하였습니다.");
			}
		}
	}
	
	private void showGraph() {										//graph에서 직접출력하면 MVC모델에 어긋난다. iterator를 사용해 구현해야하지만 일단은 모든 vertex간의 가능성을 확인하는 방식으로 구현한다.
		AppView.outputLine("");
		AppView.outputLine("> 입력된 그래프는 다음과 같습니다 : ");
		for(int tailVertex = 0; tailVertex < this.graph().numberOfVertices(); tailVertex++) {			//2중 for문을 통해 모든 vertex 사이의 edge를 확인한다.
			AppView.output("[" + tailVertex + "] ->");													//tail vertex에서 
			Iterator<WeightedEdge> neighborIterator = this.graph().neighborIteratorOf(tailVertex);
			while(neighborIterator.hasNext()) {															//이전에는 2중 for문을 이용해 처리했지만, 이번에는 iterator를 이용해 처리한다. 
				WeightedEdge nextEdge = neighborIterator.next();										//다음 요소가있다면 iterator의 다음요소의 
				AppView.output(" " + nextEdge.headVertex());											//headVertex 정보와 weight정보를 출력한다. 
				AppView.output("(" + nextEdge.weight() + ")");
			}
			AppView.outputLine("");
		}
	}
	
	public void run() {																	//실질적인 프로그램의 흐름   
		AppView.outputLine("<<< 최소비용 확장 트리 찾기 프로그램을 시작합니다 >>>");
		this.inputAndMakeGraph();
		this.showGraph();	
		this.solveAndShowShortestPaths();												//모든 입력을 받은 뒤 최단 경로를 찾아 보여주는 함수인 solveAndShowShortestPaths를 호출한다. 		
		AppView.outputLine("");
		AppView.outputLine("<<< 최소비용 확장 트리 찾기 프로그램을 종료합니다 >>>");
	}
}
