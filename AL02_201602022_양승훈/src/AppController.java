
public class AppController {
	
	private AdjacencyMatrixGraph _graph;			//이웃 여부를 나타내줄 2차원 매트릭스에 관한 객체를 갖는다.
	private PairwiseDisjointSets _pairwiseDisjointSets;
	
	public AppController() {
		this.setGraph(null);
		this.setPairwiseDisjointSets(null);					//그래프가 입력되지 않은 상태에서는 set을 만들 수가 없다.
	}

	private AdjacencyMatrixGraph graph() {
		return this._graph;
	}
	
	private void setGraph(AdjacencyMatrixGraph newGraph) {
		this._graph = newGraph;
	}

	private PairwiseDisjointSets pairwiseDisjointSets() {
		return this._pairwiseDisjointSets;
	}
	
	private void setPairwiseDisjointSets(PairwiseDisjointSets newPairwiseDisjointSets) {
		this._pairwiseDisjointSets = newPairwiseDisjointSets;
	}
		
	private void initCycleDetection() {																//생성시점말고 다른시점에서 초기화를 해주어야 하므로 초기화 함수를 별도로 만든다.
		this.setPairwiseDisjointSets(new PairwiseDisjointSets(this.graph().numberOfVertices()));	//matrix에서 vertex의 개수를 얻어 만들어준다.
	}
	
	private boolean addedEdgeDoesMakeCycle(Edge anAddedEdge) {
		int tailVertex = anAddedEdge.tailVertex();					//인자로 받은 검사대상 edge의 tail과 head를 분리해서 저장.
		int headVertex = anAddedEdge.headVertex();
		int setForTailVertex = this.pairwiseDisjointSets().find(tailVertex);		//해당 vertex가 어느 집합에 속해있는지 찾아낸다.
		int setForHeadVertex = this.pairwiseDisjointSets().find(headVertex);	
		if(setForTailVertex == setForHeadVertex) {				//만약 tailVertex가 속한 집합과 headVertex가 속한집합이 같다면 그건 이 edge가 cycle을 만들었다는 의미이다. (true반환)
			return true;
		}
		else {													//만약 그렇지 않고(서로 다른 집합에 속다면) 두 집합을 union해주고 false를 반환한다.
			this.pairwiseDisjointSets().union(setForTailVertex, setForHeadVertex);
			return false;
		}
	}
	
	private void inputAndMakeGraph() {
		AppView.outputLine("> 입력할 그래프의 vertex 수와 edge 수를 먼저 입력해야 합니다 : ");
		int numberOfVertices = this.inputNumberOfVertices();			//AppView에 있는 함수와 이름이 동일하지만, AppView에 있는 함수는 입력을 받는것이고, 유효성의 판단을 해주는 함수를 여기서 재정의해준다.
		this.setGraph(new AdjacencyMatrixGraph(numberOfVertices));		//AdjacencyMatrixGraph는 입력받은  만큼의 vertex를 갖는다. 입력받은 vertex만큼의 행과 열을 가져서 각 요소가 인접성을 나타냄. 행->열이 이웃인가? 1 : 0
		
		int numberOfEdges = this.inputNumberOfEdges();		//연결할 edge의 개수를 입력받는다.
		AppView.outputLine("");
		AppView.outputLine("> 이제부터 edge를 주어진 수 만큼 입력합니다.");
		this.initCycleDetection();							//edge를 입력받는 시점에서는 이미 # of vertex를 입력받은 후이다. 따라서 이때 pairwiseDisjointSet을 초기화 해주어야 한다.
		
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
				if(this.addedEdgeDoesMakeCycle(edge)) {																					//추가된 edge가 cycle을 만들었다
					AppView.outputLine("![Cycle] 삽입된 edge (" + edge.tailVertex() + ", " + edge.headVertex() + ") 는 그래프에 사이클을 만들었습니다.");		//cycle생성 메시지를 출력한다.
				}
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
	
	public void run() {													//실질적인 프로그램의 흐$$
		AppView.outputLine("<<<입력되는 그래프의 사이클 검사를 시작합니다>>>");
		this.inputAndMakeGraph();
		this.showGraph();
		AppView.outputLine("");
		AppView.outputLine("<<<그래프의 입력과 사이클 검사를 종료합니다>>>");
	}
}
