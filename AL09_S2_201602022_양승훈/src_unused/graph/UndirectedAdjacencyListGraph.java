package graph;

public class UndirectedAdjacencyListGraph<E extends Edge> extends DirectedAdjacencyListGraph<E>{

    public UndirectedAdjacencyListGraph(int givenNumberOfVertices){
        super(givenNumberOfVertices);
    }

    @Override
    public boolean addEdge(E anEdge){
        if (this.edgeIsValid(anEdge) && (!this.edgeDoesExist(anEdge))) {
            this.neighborListOf(anEdge.tailVertex()).add(anEdge);                                   //인접리스트로 구현되기 때문에 리스트에 edge를 넣어준다.
            @SuppressWarnings("unchecked")
            E reversedEdge = (E) anEdge.reversed();
            this.neighborListOf(reversedEdge.tailVertex()).add(reversedEdge);                       //방향성을 갖지 않는 리스트를 구현하기 때문에 반전된 edge도 넣어준다.
            this.setNumberOfEdges(this.numberOfEdges() + 1);
            return true;
        }
        return false;
    }
}