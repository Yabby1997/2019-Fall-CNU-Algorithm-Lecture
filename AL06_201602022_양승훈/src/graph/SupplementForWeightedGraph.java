package graph;

public interface SupplementForWeightedGraph<E> {										//Weight속성을 추가하기위해 interface를 만든다. 
	public int weightOfEdge(E anEdge);
	public int weightOfEdge(int aTailVertex, int aHeadVertex);
}
