package graph;

public class WeightedUndirectedAdjacencyMatrixGraph<WE extends WeightedEdge> 		//일반 edge는 사용할 수 없다. 오직 weightedEdge의 자식 class만을 쓸 수 있음.
		extends UndirectedAdjacencyMatrixGraph<WE>									//UndirectedAdjacencyMatrixGraph를 상속받음. <- 별도로 override할거 아니면 구현 따로 안해도 됨.
		implements SupplementForWeightedGraph<WE> {									//SupplementForWeigthedGraph를 구현해야함.	<- 얘는 선언만 되어있는거니까 필수적으로 구현을 해야함.
																					//weight 0 으로는 edge가 존재하지 않음을 나타낼 수 없다.
	private static final int WEIGHTED_EDGE_NONE = -1;								//음의 weight을 갖는 edge는 지금 다루지 않는다. 따라서 -1을 edge가 없음을 표현하는데 쓴다.
																				
	public WeightedUndirectedAdjacencyMatrixGraph(int givenNumberOfVertices) {
		super();																	//superclass에 선언된 기본 생성자를 호출해 컴파일러 오류를 방지, 이해를 도울 수 있다.
		this.setNumberOfVertices(givenNumberOfVertices);							//super class의 생성자와 동일하다. 
		this.setNumberOfEdges(0);
		this.setAdjacency(new int[givenNumberOfVertices][givenNumberOfVertices]);
		for(int tailVertex = 0; tailVertex < this.numberOfVertices(); tailVertex++) {
			for(int headVertex = 0; headVertex < this.numberOfVertices(); headVertex++) {
				this.setWeightOfEdgeAsNone(tailVertex, headVertex);
			}
		}	
	}
	
	@Override
	public int weightOfEdge(WE anEdge) {												//overload
		if(anEdge != null) {
			return this.weightOfEdge(anEdge.tailVertex(), anEdge.headVertex());
		}
		return WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE;
	}

	@Override
	public int weightOfEdge(int aTailVertex, int aHeadVertex) {
		if(this.edgeDoesExist(aTailVertex, aHeadVertex)){
			return this.adjacencyOfEdge(aTailVertex, aHeadVertex);						//weighted edge에서 adjacency는 즉 weight를 나타낸다. 
		}
		return WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE;
	}
	
	private void setWeightOfEdge(int aTailVertex, int aHeadVertex, int newWeight) {		//edge에 무개를! 
		this.adjacency()[aTailVertex][aHeadVertex] = newWeight;							//adjacency 함수는 이미 UndirectedAdjacencyMatrixGraph에 구현되어있어 그냥 쓸 수 있음. 
	}

	private void setWeightOfEdgeAsNone(int aTailVertex, int aHeadVertex) {				//edge 없음으로 만들기 
		this.setWeightOfEdge(aTailVertex, aHeadVertex, WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE);
	}
	
	@Override																			//이미 super class에 구현된 함수가 여기서 수정되어야 하기 떄문에 override해준다. 
	protected boolean adjacencyOfEdgeDoesExist(int aTailVertex, int aHeadVertex) {
		return (this.adjacencyOfEdge(aTailVertex, aHeadVertex) != WeightedUndirectedAdjacencyMatrixGraph.WEIGHTED_EDGE_NONE);	//superclass 에서는 0이 없는거였지만 여기선 -1 
	}
	
	@Override
	public boolean addEdge(WE anEdge) {
		if(anEdge != null) {															//입력받은 edge가 null이 아니여야 함.
			if(this.edgeIsValid(anEdge) && !this.edgeDoesExist(anEdge)) {				//edge가 유효 하면서 이미 존재하지 않으면 추가가능. 
				int tailVertex = anEdge.tailVertex();
				int headVertex = anEdge.headVertex();
				this.setWeightOfEdge(tailVertex, headVertex, anEdge.weight());			//super class에서는 adjacency가 단순 연결성이었지만, weighted에서는 weight을 나타낸다.
				this.setWeightOfEdge(headVertex, tailVertex, anEdge.weight());
				this.setNumberOfEdges(this.numberOfEdges() + 1);
				return true;
			}
		}
		return false; 
	}
}
