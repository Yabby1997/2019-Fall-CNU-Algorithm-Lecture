
public class AppController {
	
	private AdjacencyMatrixGraph _graph;			//이웃 여부를 나타내줄 2차원 매트릭스에 관한 객체를 갖는다.
	private Coloring _coloring;						//색칠에 대해 다룰 coloring 객체를 갖는다.
	//private PairwiseDisjointSets _pairwiseDisjointSets; 멤버변수 제거
	
	public AppController() {
		this.setGraph(null);
		this.setColoring(null);
		//this.setPairwiseDisjointSets(null); 멤버변수와 관련 함수가 제거됨.
	}

	private AdjacencyMatrixGraph graph() {
		return this._graph;
	}
	
	private void setGraph(AdjacencyMatrixGraph newGraph) {
		this._graph = newGraph;
	} 
	
	private Coloring coloring() {
		return this._coloring;
	}
	
	private void setColoring(Coloring aColoring) {
		this._coloring = aColoring;
	}
	
	//private PairwiseDisjointSets pairwiseDisjointSets() 함수 제거 
	//private void setPairwiseDisjointSets() 함수 제거 
	//private void initCycleDetection() 함수 제거 
	//private boolean addedEdgeDoesMakeCycle() 함수 제거 
	
	private void inputAndMakeGraph() {
		AppView.outputLine("> 입력할 그래프의 vertex 수와 edge 수를 먼저 입력해야 합니다 : ");
		int numberOfVertices = this.inputNumberOfVertices();			//AppView에 있는 함수와 이름이 동일하지만, AppView에 있는 함수는 입력을 받는것이고, 유효성의 판단을 해주는 함수를 여기서 재정의해준다.
		this.setGraph(new AdjacencyMatrixGraph(numberOfVertices));		//AdjacencyMatrixGraph는 입력받은  만큼의 vertex를 갖는다. 입력받은 vertex만큼의 행과 열을 가져서 각 요소가 인접성을 나타냄. 행->열이 이웃인가? 1 : 0
		
		int numberOfEdges = this.inputNumberOfEdges();		//연결할 edge의 개수를 입력받는다.
		AppView.outputLine("");
		AppView.outputLine("> 이제부터 edge를 주어진 수 만큼 입력합니다.");
		
		int edgeCount = 0;
		while(edgeCount < numberOfEdges) {							//for문을 쓰면 안되는 이유는? -> for문을 쓰게 되면 조건부로 증감식을 실행시킬 수 없다. 즉 이미 그래프에 있는 edge가 입력되더라도 edgeCount가 ++되기 때문?
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
	
	private Edge inputEdge() {
		do {																			//do while문을 사용하는 이유는?
			AppView.outputLine("- 입력할 edge의 두 vertex를 차례로 입력해야 합니다 : ");
			int tailVertex = AppView.inputTailVertex();
			int headVertex = AppView.inputHeadVertex();
			if(this.graph().vertexDoesExist(tailVertex) && this.graph().vertexDoesExist(headVertex)) {		//꼬리와 머리 vertex가 모두 존재할 때
				if(tailVertex == headVertex) {																//꼬리가 머리랑 같다면 self 오류메시지를 출력
					AppView.outputLine("[오류] 두 vertex 번호가 동일합니다.");
				}
				else {													//두 vertex 번호가 같지 않다. 즉, self가 아니라면 유효한 edge이다.
					return (new Edge(tailVertex, headVertex));			//꼬리와 머리 vertex를 연결하는 edge를 생성해서 반환한
				}
			}
			else {	
				if(!this.graph().vertexDoesExist(tailVertex)) {			//tail vertex가 그래프상에 존재하지 않는다면 edge를 생성할 수 없다. 오류메시지 출력 
					AppView.outputLine("[오류] 존재하지 않는 tail vertex 입니다 : " + tailVertex);
				}
				if(!this.graph().vertexDoesExist(headVertex)) {			//head vertex가 그래프상에 존재하지 않는다면 edge를 생성할 수 없다. 오류메시지 출력 
					AppView.outputLine("[오류] 존재하지 않는 head vertex 입니다 : " + headVertex);
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
				if(this.graph().edgeDoesExist(new Edge(tailVertex, headVertex))) {						//edge가 연결이 돼있다면 
					AppView.output(" " + headVertex);													//head vertex 를 출력
				}
			}
			AppView.outputLine("");
		}
	}
	
	private void showColoring() {
		AppView.outputLine("");
		AppView.outputLine("> 각 vertex에 칠해진 색은 다음과 같습니다 : ");
		for(int vertex = 0; vertex < this.graph().numberOfVertices(); vertex++) {
			AppView.outputLine("[" + vertex + "]" + this.coloring().vertexColor(vertex).name());			//name함수는 enum으로 선언했던 VertexColor에 정의된 색상의 이름(NONE, RED, BLUE)을 String객체로 반환해준다.
		}
		AppView.outputLine("");
		AppView.outputLine("> 양 끝 vertex의 색깔이 같은 edge들은 다음과 같습니다 : ");
		if(this.coloring().sameColorEdges().size() == 0) {											//같은 색상의 edge들만 모아놓았던 연결리스트의 사이즈가 0이다 -> 같은 색상의 edge들이 없다. 
			AppView.outputLine("!! 모든 edge의 양 끝 vertex의 색깔이 다릅니다.");
		}
		else {
			LinkedList<Edge>.IteratorForLinkedList iterator = this.coloring().sameColorEdges().iterator();		//iterator는 처음에 head를 가리킨다. 즉 리스트의 맨 앞에 있는 edge가 iterator에 의해 가리켜지고 있음. 
			while(iterator.hasNext()) {																			//iterator가 가리키는 것 (next)이 null이 아니라면 
				Edge currentEdge = iterator.next();																//currentEdge는 iterator가 가리키는 edge의 다음 edge
				AppView.output("(" + currentEdge.tailVertex() + ", " + currentEdge.headVertex() + ") : ");
				AppView.outputLine(this.coloring().vertexColor(currentEdge.tailVertex()).name());
			}
		}
	}
	
	public void run() {													//실질적인 프로그램의 흐$$
		AppView.outputLine("<<< 그래프 색칠하기를 시작합니다 >>>");
		this.inputAndMakeGraph();
		this.showGraph();	
		this.setColoring(new Coloring(this.graph()));				 	//색깔 칠하기에 있어서 색을 저장할 원형큐의 사이즈는 vertex의 개수로 초기화돼야함.
		this.coloring().runColoring();									//색칠 시작. 
		this.showColoring();											//색칠 어떻게 됐는지보여줌. 
		AppView.outputLine("");
		AppView.outputLine("<<< 색칠하기 종료합니다 >>>");
	}
}
