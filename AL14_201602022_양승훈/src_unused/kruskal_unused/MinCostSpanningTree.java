package kruskal;

import view.AppView;
import graph.WeightedEdge;
import graph.WeightedUndirectedAdjacencyMatrixGraph;
import list.LinkedList;
import list.List;

public class MinCostSpanningTree {
	private WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge> _graph;
	private MinPriorityQ<WeightedEdge> _minPriorityQ;								//graph엣지의 비용이 적은거부터 얻기 위해 최소우선순위큐를 이용한다. 
	private List<WeightedEdge> _spanningTreeEdgeList;
	
	public MinCostSpanningTree() {
		this.setGraph(null);
		this.setMinPriorityQ(null);
		this.setSpanningTreeEdgeList(null);
	}
	
	//getter setter private으로 선언 
	private MinPriorityQ<WeightedEdge> minPriorityQ(){
		return this._minPriorityQ;
	}
	
	private void setMinPriorityQ(MinPriorityQ<WeightedEdge> newMinPriorityQ) {
		this._minPriorityQ = newMinPriorityQ;
	}
	
	private WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge> graph(){
		return this._graph;
	}
	
	private void setGraph(WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge> newGraph) {
		this._graph = newGraph;
	}
	
	private List<WeightedEdge> spanningTreeEdgeList(){
		return this._spanningTreeEdgeList;
	}
	
	private void setSpanningTreeEdgeList(List<WeightedEdge> newSpanningTreeEdgeList) {
		this._spanningTreeEdgeList = newSpanningTreeEdgeList;
	}
	
	private void initMinPriorityQ() {
		this.setMinPriorityQ(new MinPriorityQ<WeightedEdge>(this.graph().numberOfEdges()));			//graph의 edge 개수만큼의 크기를 가진 weightedEdge를 받는 minPriorityQ를 만들어준다. 
		int numberOfVertices = this.graph().numberOfVertices();
		for(int tailVertex = 0; tailVertex < numberOfVertices; tailVertex++) {						//무향그래프에 대한 처리이므로 이렇게 해줌으로써 비교 횟수를 줄일 수 있음. 
			for(int headVertex = tailVertex + 1; headVertex < numberOfVertices; headVertex++) {
				if(this.graph().edgeDoesExist(tailVertex, headVertex)) {							//해당 edge가 존재한다면 
					int weight = this.graph().weightOfEdge(tailVertex, headVertex);					//그 edge의 weight을 
					WeightedEdge edge = new WeightedEdge(tailVertex, headVertex, weight);			//이용해서 WeightedEdge를 만들고 
					this.minPriorityQ().add(edge);													//최소우선순위 큐에 넣어준다. 이렇게 하면 weight이 적은 순으로 정렬되 최소 우선순위 큐에 삽입된다. 
				}
			}
		}
	}
	
	public List<WeightedEdge> solve(WeightedUndirectedAdjacencyMatrixGraph<WeightedEdge> aGraph){
		this.setGraph(aGraph);																		//인자로 그래프를 받아 설정해줌. 
		this.initMinPriorityQ();																	//minPriorityQ 생성. 크기는 방금 인자로 받은 그래프의 vertex 개수이다. 
		this.setSpanningTreeEdgeList(new LinkedList<WeightedEdge>());								//spanningTreeEdgeList 생성. 
		
		PairwiseDisjointSets pairwiseDisjointSets = new PairwiseDisjointSets(this.graph().numberOfVertices());		//pairwiseDisjointSet을 이용한다.
		int maxNumberOfTreeEdges = this.graph().numberOfVertices() - 1;												//유니크한 경로를 갖기 때문에 엣지의 개수는 vertex - 1	만큼이어야한다.   

		while((this.spanningTreeEdgeList().size() < maxNumberOfTreeEdges) && (!this.minPriorityQ().isEmpty())) {	//스패닝트리의 사이즈가 최대 엣지의 개수보다 작고 minPriorityQ가 비어있지 않다면 더 들어가야하므로 반복. 
			WeightedEdge edge = this.minPriorityQ().removeMin();									//minPriorityQ의 root를 제거(가장 작은 weight를 갖는 edge가 반환됨)
			int setOfTailVertex = pairwiseDisjointSets.find(edge.tailVertex());						//반환된 edge의 tailVertex가 속하는 집합을 구함 
			int setOfHeadVertex = pairwiseDisjointSets.find(edge.headVertex());						//반환된 edge의 headVertex가 속하는 집합을 구함 
			if(setOfTailVertex == setOfHeadVertex) {												//tail, head가 속한 집합이 동일하다, 즉 같은 집합에 있다면... Debug메시지 출력 
				AppView.outputLine("[DEBUG] Edge(" + edge.tailVertex() + ", " + edge.headVertex() + ", (" + edge.weight() + "))는 스패닝 트리에 사이클을 생성시키므로, 버립니다.");
			}
			else {
				this.spanningTreeEdgeList().add(edge);												//그렇지 않다면(사이클을 만들지 않으면) spanningTreeEdgeList에 추가해준다. 
				pairwiseDisjointSets.union(setOfTailVertex, setOfHeadVertex);						//tail과 head 의 집합을 union해준다.
				AppView.outputLine("[DEBUG] Edge(" + edge.tailVertex() + ", " + edge.headVertex() + ", (" + edge.weight() + "))는 스패닝 트리의 edge로 추가됩니다.");
			}
		}
		return (this.spanningTreeEdgeList().size() == maxNumberOfTreeEdges) ? this.spanningTreeEdgeList() : null;	//solve가 정상적으로 됐으면 solve된 spanning tree를 아니면 null 반
	}
}
