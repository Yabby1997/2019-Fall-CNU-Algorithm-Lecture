
public class Edge {
	private int _tailVertex;					//모든 멤버 변수는 private으로 정의하고 필요하다면 getter/setter를 통해 참조/변경한다. 
	private int _headVertex;
	
	public Edge(int givenTailVertex, int givenHeadVertex) {
		this.setTailVertex(givenTailVertex);					//"캡슐화"가능하면 내부적으로도 getter/setter 사용을 권장한다.
		this.setHeadVertex(givenHeadVertex);
	}
	public int tailVertex() {
		return this._tailVertex;
	}
	
	public void setTailVertex(int newTailVertex) {
		this._tailVertex = newTailVertex;
	}
	
	public int headVertex() {
		return this._headVertex;
	}
	
	public void setHeadVertex(int newHeadVertex) {
		this._headVertex = newHeadVertex;
	}
}
