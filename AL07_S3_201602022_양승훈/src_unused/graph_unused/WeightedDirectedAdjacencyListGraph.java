package graph;

import list.Iterator;

public class WeightedDirectedAdjacencyListGraph<WE extends WeightedEdge> extends DirectedAdjacencyListGraph<WE> implements SupplementForWeightedGraph<WE> {
	//앞서 만들었던 DisjointAdjacencyListGraph를 상속받고, 이전 실습에서 만들었던 Weight관련 속성을 추가해줄 SupplementForWeightedGraph를 상속받아준다.  
	private static final int WEIGHT_INFINITE = Integer.MAX_VALUE / 2;					//없음을 표시할 무한대 상수 정의  
	
	public WeightedDirectedAdjacencyListGraph(int givenNumberOfVertices) {				//생성자는 슈퍼클래스의 생성자를 그대로 사용해도 된다. 
		super(givenNumberOfVertices);
	}
	
	@Override
	protected int adjacencyOfEdge(int aTailVertex, int aHeadVertex) {
		Iterator<WE> iterator = (Iterator<WE>)this.neighborIteratorOf(aTailVertex);				//tailVertex linkedList의 iterator를 weightedEdge로 형변환해서 사용 
		while(iterator.hasNext()) {
			WE neighborEdge = (WE) iterator.next();
			if(aHeadVertex == neighborEdge.headVertex()) {
				return neighborEdge.weight();													//UnWeighted에서는 연결성을 반환해줫지만, weighted에서는 cost가 된다.  
			}
		}
		return WeightedDirectedAdjacencyListGraph.WEIGHT_INFINITE;								//만약 없다면 무한대를 반환한다.  없다는 의미임  
	}

	@Override
	public boolean edgeDoesExist(int aTailVertex, int aHeadVertex) {
		if(this.edgeIsValid(aTailVertex, aHeadVertex)) {
			return (this.adjacencyOfEdge(aTailVertex, aHeadVertex) < WeightedDirectedAdjacencyListGraph.WEIGHT_INFINITE);		//앞서 정의한 무한대보다 적다면 있는거임 
		}			
		return false;
	}
	
	@Override
	public boolean edgeDoesExist(WE anEdge) {													//pdf에서는 해당 부분을 구현하지 않았다 임의로 구현함 . 함수 오버로딩 
		if(anEdge != null) {
			return (this.edgeDoesExist(anEdge.tailVertex(), anEdge.headVertex()));
		}
		return false;
	}
	
	//SupplementFOrWeightedGraph의 함수를 구현한다. 
	@Override
	public int weightOfEdge(int aTailVertex, int aHeadVertex) {									//엣지의cost를 반환해주는 함수. vertex간의 adjacency가 곧 COST이다. 
		if(this.edgeIsValid(aTailVertex, aHeadVertex)) {										//유효한 edge라면 
			return this.adjacencyOfEdge(aTailVertex, aHeadVertex);								//그 edge의 adjacency가 곧 cost이다 
		}
		return WeightedDirectedAdjacencyListGraph.WEIGHT_INFINITE;			
	}

	@Override
	public int weightOfEdge(WE anEdge) {														//함수 오버로딩 
		if(this.edgeIsValid(anEdge)) {
			return this.adjacencyOfEdge(anEdge.tailVertex(), anEdge.headVertex());
		}
		return WeightedDirectedAdjacencyListGraph.WEIGHT_INFINITE;
	}
}
