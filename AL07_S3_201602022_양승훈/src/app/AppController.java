package app;

import graph.DirectedAdjacencyMatrixGraph;
import topologicalSort.TopologicalSort;
import graph.AdjacencyGraph;
import graph.DirectedAdjacencyMatrixGraph;
import graph.Edge;
import list.Iterator;
import list.List;

public class AppController {
	
	private AdjacencyGraph<Edge> _graph;
	private TopologicalSort<Edge> _topologicalSort;
	
	public AppController() {
		this.setGraph(null);
		this.setTopologicalSort(new TopologicalSort<Edge>());
	}
	
	//getter setter
	private AdjacencyGraph<Edge> graph() {
		return this._graph;
	}
	
	private void setGraph(AdjacencyGraph<Edge> newGraph) {
		this._graph = newGraph;
	}

	private TopologicalSort<Edge> topologicalSort(){
		return this._topologicalSort;
	}

	private void setTopologicalSort(TopologicalSort<Edge> newTopologicalSort){
		this._topologicalSort = newTopologicalSort;
	}

	private void inputAndMakeGraph() {
		AppView.outputLine("> 입력할 그래프의 vertex 수와 edge 수를 먼저 입력해야 합니다 : ");
		int numberOfVertices = this.inputNumberOfVertices();															//AppView에 있는 함수와 이름이 동일하지만, AppView에 있는 함수는 입력을 받는것이고, 유효성의 판단을 해주는 함수를 여기서 재정의해준다.
		this.setGraph(new DirectedAdjacencyMatrixGraph<>(numberOfVertices));											//graph는 입력받은  만큼의 vertex를 갖는다. 입력받은 vertex만큼의 행과 열을 가져서 각 요소가 인접성을 나타냄.
		//abstract class인 AdjacencyGraph로 멤버인 _graph를 해 놓았기 때문에 AdjacencyGraph를 상속받는 모든 클래스가 이 자리에 올 수 있고, 여기서만 변경을 해주면 다른곳은 수정하지 않아도 정상적으로 작동한다. 
		
		int numberOfEdges = this.inputNumberOfEdges();																		//연결할 edge의 개수를 입력받는다.
		AppView.outputLine("");
		AppView.outputLine("> 이제부터 edge를 주어진 수 만큼 입력합니다.");
		
		int edgeCount = 0;
		while(edgeCount < numberOfEdges) {											//for문을 쓰면 안되는 이유는? -> for문을 쓰게 되면 조건부로 증감식을 실행시킬 수 없다. 즉 이미 그래프에 있는 edge가 입력되더라도 edgeCount가 ++되기 때문?
			Edge edge = this.inputEdge();
			if(this.graph().edgeDoesExist(edge)) {
				AppView.outputLine("(오류) 입력된 edge (" + edge.tailVertex() + ", " + edge.headVertex() + ") 는 그래프에 이미 존재합니다.");		//이미 그래프에 존재하는 edge를 추가하려고 하면 카운트하지 말고 반복.
			}
			else {
				edgeCount++;
				this.graph().addEdge(edge);
				AppView.outputLine("! 새로운 edge (" + edge.tailVertex() + ", " + edge.headVertex() + ") 가 그래프에 삽입되었습니다.");			//그래프에 없는 edge를 추가하라고 하면 카운트하고 반복.	
			}
		}
	}
	
	private  Edge inputEdge() {
		do {																											//do while문을 사용하는 이유는?
			AppView.outputLine("- 입력할 edge의 두 vertex를 차례로 입력해야 합니다 : ");
			int tailVertex = AppView.inputTailVertex();
			int headVertex = AppView.inputHeadVertex();
			if(this.graph().vertexDoesExist(tailVertex) && this.graph().vertexDoesExist(headVertex)) {					//꼬리와 머리 vertex가 모두 존재할 때
				if(tailVertex == headVertex) {																			//꼬리가 머리랑 같다면 self 오류메시지를 출력
					AppView.outputLine("[오류] 두 vertex 번호가 동일합니다.");
				}
				else {																									//두 vertex 번호가 같지 않다. 즉, self가 아니라면 유효한 edge이다.
					return (new  Edge(tailVertex, headVertex));															//꼬리와 머리 vertex를 연결하고 cost만큼의 weight을 갖는 weighted edge를 생성해서 반환한다.
				}
			}
			else {	
				if(!this.graph().vertexDoesExist(tailVertex)) {															//tail vertex가 그래프상에 존재하지 않는다면 edge를 생성할 수 없다. 오류메시지 출력
					AppView.outputLine("[오류] 존재하지 않는 tail vertex 입니다 : " + tailVertex);
				}
				if(!this.graph().vertexDoesExist(headVertex)) {															//head vertex가 그래프상에 존재하지 않는다면 edge를 생성할 수 없다. 오류메시지 출력
					AppView.outputLine("[오류] 존재하지 않는 head vertex 입니다 : " + headVertex);
				}
			}
		}
		while(true);
	}
	
	private int inputNumberOfVertices() {
		int numberOfVertices = AppView.inputNumberOfVertices();
		while(numberOfVertices <= 0) {																					//while문을 이용해 입력받은 값이 조건에 안맞으면 오류 메시지를 출력하고 입력을 다시받도록 함.
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
	
	private void showGraph() {																//graph에서 직접출력하면 MVC모델에 어긋난다. iterator를 사용해 구현해야하지만 일단은 모든 vertex간의 가능성을 확인하는 방식으로 구현한다.
		AppView.outputLine("");
		AppView.outputLine("> 입력된 그래프는 다음과 같습니다 : ");
		for(int tailVertex = 0; tailVertex < this.graph().numberOfVertices(); tailVertex++) {							//2중 for문을 통해 모든 vertex 사이의 edge를 확인한다.
			AppView.output("[" + tailVertex + "] ->");															//tail vertex에서
			Iterator<Edge> neighborIterator = this.graph().neighborIteratorOf(tailVertex);								//tailVertex의 이웃한 vertex리스트를 iterator로 순회
			while(neighborIterator.hasNext()) {
				Edge nextEdge = neighborIterator.next();																//모든 인접한 headVertex들을 출력한다.
				AppView.output(" " + nextEdge.headVertex());
			}
			AppView.outputLine("");
		}
	}

	private void showSortedResult(){
		AppView.outputLine("");
		AppView.outputLine("> 위상정렬 결과는 다음과 같습니다 : ");
		List<Integer> topologicalSortedList = this.topologicalSort().topologicalSortedList();							//위상정렬된 리스트를 받아온다.
		Iterator<Integer> iterator = topologicalSortedList.listIterator();												//받아온 정렬된 리스트를 이터레이터로 순회하며 출력해준다.
		while(iterator.hasNext()){
			int vertex = iterator.next();
			AppView.output("-> " + vertex + " ");
		}
		AppView.outputLine("");
	}

	public void run() {
		AppView.outputLine("<<< 위상정렬 프로그램을 시작합니다 >>>");
		this.inputAndMakeGraph();
		this.showGraph();																								//모든 입력을 받은 뒤 최단 경로를 찾아 보여주는 함수인 solveAndShowShortestPaths를 호출한다.

		if(this.topologicalSort().solve(this.graph())){
			this.showSortedResult();
		}
		else{
			AppView.outputLine("");
			AppView.outputLine("[오류] 위상정렬을 성공적으로 마치지 못했습니다. 그래프에 사이클이 존재합니다.");

		}
		AppView.outputLine("");
		AppView.outputLine("<<<위상정렬 프로그램을 종료합니다 >>>");
	}
}
