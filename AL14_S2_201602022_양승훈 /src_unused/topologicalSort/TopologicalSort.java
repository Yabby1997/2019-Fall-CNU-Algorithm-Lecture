package topologicalSort;

import view.AppView;
import graph.Edge;
import graph.AdjacencyGraph;
import list.List;
import list.Stack;
import list.Iterator;
import list.LinkedStack;
import list.StackWithIterator;
import list.LinkedStackWithIterator;
import list.ArrayList;

public class TopologicalSort<E extends Edge> {
	private static final boolean DEBUG_MODE = true; 																	//디버그 메시지 출력 여부를 결정할 상수 debug mode를 선언해준다.
	private static void showDebugMessage(String aMessage) {																//디버그 모드가 true인 경우에만 디버그 메시지를 출력하기 위해 static 함수 showDebugMessage를 구현해준다.
		if(DEBUG_MODE) {
			AppView.output(aMessage);
		}
	}
	
	private AdjacencyGraph<E> _graph;
	private int[] _predecessorCounts;
	private StackWithIterator<Integer> _zeroCountVertices;
	private List<Integer> _sortedList;
	
	//constructor
	public TopologicalSort() {
		this.setGraph(null);
		this.setPredecessorCounts(null);
		this.setZeroCountVertices(null);
		this.setSortedList(null);
	}
	
	//getter/setter
	private AdjacencyGraph<E> graph(){
		return this._graph;
	}
	
	private void setGraph(AdjacencyGraph<E> newGraph) {
		this._graph = newGraph;
	}
	
	private int[] predecessorCounts() {
		return this._predecessorCounts;
	}
	
	private void setPredecessorCounts(int[] newPredecessorCount) {
		this._predecessorCounts = newPredecessorCount;
	}
	
	private StackWithIterator<Integer> zeroCountVertices(){
		return this._zeroCountVertices;
	}
	
	private void setZeroCountVertices(StackWithIterator<Integer> newZeroCountVertices) {
		this._zeroCountVertices = newZeroCountVertices;
	}
	
	private List<Integer> sortedList(){
		return this._sortedList;
	}
	
	private void setSortedList(List<Integer> newSortedList) {
		this._sortedList = newSortedList;
	}
	
	public List<Integer> topologicalSortedList(){																		//외부에서 정렬된 리스트에 접근할 때는 이 함수를 사용하여 코드의 가독성을 살린다.
		return this.sortedList();
	}
	
	private void initPredecessorCounts() {
		this.setPredecessorCounts(new int[this.graph().numberOfVertices()]);											//vertex마다 predecessor가 몇개인지를 체크해줘야 하므로 predecessor 개수 배열은 vertex의 개수만큼의 크기를 갖는다.
		for(int tailVertex = 0; tailVertex < this.graph().numberOfVertices(); tailVertex++) {
			this.predecessorCounts()[tailVertex] = 0;																	//일단 모두 선행자가 없는것으로 초기화해준다.
		}
		for(int tailVertex = 0; tailVertex < this.graph().numberOfVertices(); tailVertex++) {							//모든 tail에 대해
			Iterator<E> iterator = this.graph().neighborIteratorOf(tailVertex);											//모든 인접한 vertex를 순회하며
			while(iterator.hasNext()) {
				Edge edge = (Edge) iterator.next();																		//AdjacencyGraph이기때문에 WeightedEdge도 올 수 있다.  그래서 Edge로 강제
				this.predecessorCounts()[edge.headVertex()]++;															//검사하는 tailvertex는 iterator가 순회하며 발견한 headVertex의 선행자이므로 headVertex의 선행자 수를 증가시킨다.
			}
		}
		TopologicalSort.showDebugMessage("\n[Debug] 각 vertex의 초기 선행자 수는 다음과 같습니다 : \n--> ");			//만들어진 predecessorCount 배열의 모든 요소를 디버그 메시지를 통해 보여준다.
		for(int vertex = 0; vertex < this.graph().numberOfVertices(); vertex++) {
			TopologicalSort.showDebugMessage(" [" + vertex + "]=" + this.predecessorCounts()[vertex]);
		}
		TopologicalSort.showDebugMessage("\n");
	}
	
	private void initZeroCountVertices() {
		this.setZeroCountVertices(new LinkedStackWithIterator<Integer>());												//interface인 StackWithIterator를 변수명으로 사용하고 실질적인 객체 타입은 LinkedStackWithIterator로 준다.
		TopologicalSort.showDebugMessage("\n[Debug] 그래프에 선행자가 없는 vertex는 다음과 같습니다 : \n--> ( ");
		for(int vertex = 0; vertex < this.graph().numberOfVertices(); vertex++) {
			if(this.predecessorCounts()[vertex] == 0) {																	//선행자의 개수를 확인하면서
				this.zeroCountVertices().push(vertex);																	//선행자 개수가 0인것을 발견하면, 선행자 0인 vertex를 모아두는 zeroCountVertices스택에 삽입한다.
				TopologicalSort.showDebugMessage(vertex + " ");												//그리고 debug메시지로 보인다.
			}
		}
		TopologicalSort.showDebugMessage(")\n");
	}
	
	private void showZeroCountVertices() {
		TopologicalSort.showDebugMessage("--> 스택 : <Top>");
		Iterator<Integer> iterator = this.zeroCountVertices().iterator();
		
		while(iterator.hasNext()) {
			int vertex = (Integer) iterator.next();
			TopologicalSort.showDebugMessage(" " + vertex);
		}
		TopologicalSort.showDebugMessage(" <Bottom>\n");
	}

	public boolean solve(AdjacencyGraph<E> aGraph){
		this.setGraph(aGraph);
		this.initPredecessorCounts();
		this.initZeroCountVertices();
		this.setSortedList(new ArrayList<Integer>(this.graph().numberOfVertices()));

		TopologicalSort.showDebugMessage("\n[DEBUG] 스택에 pop/push되는 과정은 다음과 같습니다 : \n");
		this.showZeroCountVertices();
		while(!this.zeroCountVertices().isEmpty()){																		//zeroCountVertices의 사이즈가 0이 될 떄 까지 반복한다.
			int tailVertex = this.zeroCountVertices().pop();															//predecessor가 0개인 vertex들의 배열에서 요소를 pop받는다. (pop)
			TopologicalSort.showDebugMessage("--> Popped = " + tailVertex + " : Pushed = ( ");
			this.sortedList().add(tailVertex);																			//pop된 요소, 즉 predecessor 0개를 갖는 vertex를 sortedList에 넣고
			Iterator<E> iterator = this.graph().neighborIteratorOf(tailVertex);											//pop된 tailVertex와 인접한 엣지를 순회하는 iterator를 이용,
			while(iterator.hasNext()){
				Edge edge = (Edge) iterator.next();
				--this.predecessorCounts()[edge.headVertex()];															//tailVertex와 연결됐던 headVertex들의 선행자를 하나씩 줄여준다. tailVertex를 제거했기 때문.
				if(this.predecessorCounts()[edge.headVertex()] == 0){													//그로 인해서 headVertex의 선행자가 0이 되었다면, 이또한 zeroCountVertices리스트에 넣어준다. (push)
					this.zeroCountVertices().push(edge.headVertex());
					TopologicalSort.showDebugMessage(edge.headVertex() + " ");
				}
			}
			TopologicalSort.showDebugMessage(")\n");
			this.showZeroCountVertices();																				//선행자가 0인 스택이 빌때까지 팝. 그로인해 다른 vertex들의 선행자 수가 변하는걸 반영해 선행자 수 0이 된 요소 발견시 다시 push
		}
		return (this.sortedList().size() == this.graph().numberOfVertices());												//이렇게 정렬된 topologicalSortedList의 사이즈가 graph의 vertex개수와 같아야 정상적으로 위상정렬이 된 것이다.
	}
}
