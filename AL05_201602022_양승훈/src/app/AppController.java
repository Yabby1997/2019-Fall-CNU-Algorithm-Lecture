package app;

import graph.WeightedUndirectedAdjacencyMatrixGraph;
import graph.WeightedEdge;
import kruskal.MinCostSpanningTree;
import list.List;
import list.Iterator;

public class AppController {
	
	private WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge> _graph;	//이웃 여부를 나타내줄 2차원 매트릭스에 관한 객체를 갖는다. 저번 실습과 달리 WeightedUndirectedAdjacencyMatrixGraph를 사용한다.
	private MinCostSpanningTree _minCostSpanningTree;						//minCostSpanningTree와spanningTreeEdgeList를 멤버로 선언해준다.
	private List<WeightedEdge> _spanningTreeEdgeList;
	
	
	public AppController() {
		this.setGraph(null);
		this.setMinCostSpanningTree(null);
		this.setSpanningTreeEdgeList(null);
	}
	
	//getter setter
	private WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge> graph() {
		return this._graph;
	}
	
	private void setGraph(WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge> newGraph) {
		this._graph = newGraph;
	} 
	
	private MinCostSpanningTree minCostSpanningTree() {
		return this._minCostSpanningTree;
	}
	
	private void setMinCostSpanningTree(MinCostSpanningTree newMinCostSpanningTree) {
		this._minCostSpanningTree = newMinCostSpanningTree;
	}
	
	private List<WeightedEdge> spanningTreeEdgeList(){
		return this._spanningTreeEdgeList;
	}
	
	private void setSpanningTreeEdgeList(List<WeightedEdge> newSpanningTreeEdgeList) {
		this._spanningTreeEdgeList = newSpanningTreeEdgeList;
	}
	
	private void inputAndMakeGraph() {
		AppView.outputLine("> 입력할 그래프의 vertex 수와 edge 수를 먼저 입력해야 합니다 : ");
		int numberOfVertices = this.inputNumberOfVertices();			//AppView에 있는 함수와 이름이 동일하지만, AppView에 있는 함수는 입력을 받는것이고, 유효성의 판단을 해주는 함수를 여기서 재정의해준다.
		this.setGraph(new WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge>(numberOfVertices));		//AdjacencyMatrixGraph는 입력받은  만큼의 vertex를 갖는다. 입력받은 vertex만큼의 행과 열을 가져서 각 요소가 인접성을 나타냄.
		
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
	
	
	private void showGraph() {										//graph에서 직접출력하면 MVC모델에 어긋난다. iterator를 사용해 구현해야하지만 일단은 모든 vertex간의 가능성을 확인하는 방식으로 구현한다.
		AppView.outputLine("");
		AppView.outputLine("> 입력된 그래프는 다음과 같습니다 : ");
		for(int tailVertex = 0; tailVertex < this.graph().numberOfVertices(); tailVertex++) {			//2중 for문을 통해 모든 vertex 사이의 edge를 확인한다.
			AppView.output("[" + tailVertex + "] ->");													//tail vertex에서 
			for(int headVertex = 0; headVertex < this.graph().numberOfVertices(); headVertex++) {		//head vertex로 
				if(this.graph().edgeDoesExist(tailVertex, headVertex)) {								//edge가 연결이 돼있다면 
					AppView.output(" " + headVertex);													//head vertex출력 
					AppView.output("(" + this.graph().weightOfEdge(tailVertex, headVertex) + ")");		//cost(weight)출력 
				}
			}
			AppView.outputLine("");
		}
		
		AppView.outputLine("");																			//추가로 인접성을 보여준다. 
		AppView.outputLine("> 입력된 그래프의 Adjacency Matrix는 다음과 같습니다 : ");
		AppView.output("      ");
		for(int headVertex = 0; headVertex < this.graph().numberOfVertices(); headVertex++) {			//headVertex 인덱스를 표시 
			AppView.output(String.format(" [%1s]", headVertex));										//포맷에 맞추어 출력한다. 
		}
		AppView.outputLine("");
		for(int tailVertex = 0; tailVertex < this.graph().numberOfVertices(); tailVertex++) {			
			AppView.output("[" + tailVertex + "] ->");													//tailVertex 인덱스를 표시 
			for(int headVertex = 0; headVertex < this.graph().numberOfVertices(); headVertex++) {		//해당 인덱스의 weight를 출력한다. -> adjacency matrix의 요소 값은 해당되는 edge의 cost
				int weight = this.graph().weightOfEdge(tailVertex, headVertex);
				AppView.output(String.format("%4d", weight));
			}
			AppView.outputLine("");
		}
	}
	
	private void showMinCostSpanningTree() {
		AppView.outputLine("");
		AppView.outputLine("> 주어진 그래프의 최소비용 확장트리의 edge들은 다음과 같습니다 : ");
		Iterator<WeightedEdge> listIterator = this.spanningTreeEdgeList().listIterator();
		while(listIterator.hasNext()) {
			WeightedEdge edge = listIterator.next();
			AppView.outputLine("Tree Edge(" + edge.tailVertex() + ", " + edge.headVertex() + ", (" + edge.weight() + "))");
		}
	}
	
	public void run() {																	//실질적인 프로그램의 흐름   
		AppView.outputLine("<<< 최소비용 확장 트리 찾기 프로그램을 시작합니다 >>>");
		this.inputAndMakeGraph();
		this.showGraph();	
		AppView.outputLine("");
		AppView.outputLine("> 주어진 그래프의 최소비용 확장트리 찾기를 시작합니다 : ");
		AppView.outputLine("");
		this.setMinCostSpanningTree(new MinCostSpanningTree());
		this.setSpanningTreeEdgeList(this.minCostSpanningTree().solve(this.graph()));
		if(this.spanningTreeEdgeList() == null) {
			AppView.outputLine("> 주어진 그래프의 컴포넌트가 2개 이상이어서, 최소비용 확장트리 찾기에 실패하였습니다.");
		}
		else {
			this.showMinCostSpanningTree();
		}
		AppView.outputLine("");
		AppView.outputLine("<<< 최소비용 확장 트리 찾기 프로그램을 종료합니다 >>>");
	}
}
