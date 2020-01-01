package graph;

import list.Iterator;

public class DirectedAdjacencyMatrixGraph<E extends Edge> extends AdjacencyGraph<E> {		//추상 클래스로 만든 AdjacencyGraph를 상속받는다.
    private int[][] _adjacency;													            //각 vertex로부터의 인접한 vertex들을 나타낼 LinkedList들의 배열

    protected DirectedAdjacencyMatrixGraph(){}

    public DirectedAdjacencyMatrixGraph(int givenNumberOfVertices) {
        this.setNumberOfVertices(givenNumberOfVertices);
        this.setAdjacency(new int[givenNumberOfVertices][givenNumberOfVertices]);
        for(int tailVertex = 0; tailVertex < this.numberOfVertices(); tailVertex++) {
            for(int headVertex = 0; headVertex < this.numberOfVertices(); headVertex++) {
                this.setAdjacencyOfEdgeAsNone(tailVertex, headVertex);
            }
        }
    }

    protected int[][] adjacency(){
        return this._adjacency;
    }

    protected void setAdjacency(int[][] newAdjacency) {
        this._adjacency = newAdjacency;
    }

    protected int adjacencyOfEdge(int aTailVertex, int aHeadVertex) {
        return this.adjacency()[aTailVertex][aHeadVertex];
    }

    protected void setAdjacencyOfEdgeAs(int tailVertex, int headVertex, int anAdjacencyOfEdge){
        this.adjacency()[tailVertex][headVertex] = anAdjacencyOfEdge;
    }

    protected void setAdjacencyOfEdgeAsNone(int tailVertex, int headVertex){
        this.setAdjacencyOfEdgeAs(tailVertex, headVertex, AdjacencyGraph.EDGE_NONE);
    }

    protected void setAdjacencyOfEdgeAsExist(int tailVertex, int headVertex){
        this.setAdjacencyOfEdgeAs(tailVertex, headVertex, AdjacencyGraph.EDGE_EXIST);
    }

    protected boolean adjacencyOfEdgeDoesExist(int tailVertex, int headVertex){
        return (this.adjacencyOfEdge(tailVertex, headVertex) != AdjacencyGraph.EDGE_NONE);
    }

    @Override
    public boolean edgeDoesExist(int aTailVertex, int aHeadVertex) {
        if(this.edgeIsValid(aTailVertex, aHeadVertex)){
            return (this.adjacencyOfEdgeDoesExist(aTailVertex, aHeadVertex));
        }
        return false;
    }

    @Override
    public boolean edgeDoesExist(Edge anEdge) {
        if(anEdge != null) {
            return this.edgeDoesExist(anEdge.tailVertex(), anEdge.headVertex());
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E edge(int aTailVertex, int aHeadVertex) {
        if(this.edgeDoesExist(aTailVertex, aHeadVertex)) {									//edge가 존재한다
            return (E) new Edge(aTailVertex, aHeadVertex);									//tail과 head로 edge를 만들어서 반환해준다.
        }
        return null;
    }

    @Override
    public boolean addEdge(E anEdge) {
        if(anEdge != null) {																//인자로 받은 edge가 null은 아닌지?
            if(this.edgeIsValid(anEdge) && !this.edgeDoesExist(anEdge)) {					//아니라면 edge가 유효하면서 이미 존제하지는 않는지?
                int tailVertex = anEdge.tailVertex();
                int headVertex = anEdge.headVertex();
                this.setAdjacencyOfEdgeAsExist(tailVertex, headVertex);		                //그렇다면 edge의 연결성을 만들어줘야한다.
                this.setNumberOfEdges(this.numberOfEdges() + 1);
                return true;																//edge가 추가되었으니 true반환.
            }
        }
        return false;																		//그렇지 못하다면 edge가 추가되지 못한 것 이므로 false 반환.
    }

    public Iterator<E> neighborIteratorOf(int aTailVertex){
        return new IteratorForNeighborsOf(aTailVertex);
    }

    private class IteratorForNeighborsOf implements Iterator<E>{
        private int _tailVertex;
        private int _nextHeadVertex;

        private IteratorForNeighborsOf(int givenTailVertex){
            this.setTailVertex(givenTailVertex);
            this.setNextHeadVertex(0);
        }

        private int tailVertex(){
            return this._tailVertex;
        }

        private void setTailVertex(int newTailVertex){
            this._tailVertex = newTailVertex;
        }

        private int nextHeadVertex(){
            return this._nextHeadVertex;
        }

        private void setNextHeadVertex(int newNextHeadVertex){
            this._nextHeadVertex = newNextHeadVertex;
        }

        public boolean hasNext(){                                                                                       //tailVertex기준, headVertex를 모두 순회하며 연결성을 확인.
            while(this.nextHeadVertex() < DirectedAdjacencyMatrixGraph.this.numberOfVertices()){
                if(DirectedAdjacencyMatrixGraph.this.adjacencyOfEdgeDoesExist(this.tailVertex(), this.nextHeadVertex())){
                    return true;
                }
                this.setNextHeadVertex(this.nextHeadVertex() + 1);                                                      //while loop이 끝나고 나면 nextHeadVertex는 tailVertex에 인접한 다음 headVertex를 가리키게된다.
            }
            return false;
        }

        public E next(){                                                                                                //hasNext와 함께 쓰이면서 다음 headVertex를 찾아낸다.
            E nextElement = (E) new Edge(this.tailVertex(), this.nextHeadVertex());
            this.setNextHeadVertex(this.nextHeadVertex() + 1);
            return nextElement;
        }
    }
}
