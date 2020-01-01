package graph;
																				//Edge를 상속받으므로 Edge에 구현된 함수를 사용할 수 있다. 
public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {	//WeightedEdge끼리 비교해야한다 -> Comparable로. Edge를 상속받아 weight만 추가로 구현해주면 된다. 
	private static final int DEFAULT_WEIGHT = 0;								//상수로 기본 weight 0 을 선언해준다.
	private int _weight;
	
	public WeightedEdge(int givenTailVertex, int givenHeadVertex) {				//WeightedEdge는 Edge를 상속받아 만들어진 클래스이다. 따라서 생성이될 때 상속받은 부분은 superclass인 Edge의 생성자가 처리하도록 한다.
		super(givenTailVertex, givenHeadVertex);								//이렇게 처리하지 않으면 기본 생성자를 호출한것으로 된다. 
		this.setWeight(DEFAULT_WEIGHT);											//WeightedEdge를 superclass인 Edge의 생성자를 통해 생성하고, WeightedEdge만의 멤버변수인 _weight를 디폴트값으로 설정해준다. 
	}
	
	public WeightedEdge(int givenTailVertex, int givenHeadVertex, int givenWeight) {
		super(givenTailVertex, givenHeadVertex);								//super의 생성자를 그대로 가져다 쓰면서 
		this.setWeight(givenWeight);											//추가 인자로받은 givenWeight를 weight로 설정해준다. 
	}
	
	public int weight() {
		return this._weight;
	}
	
	public void setWeight(int newWeight) {
		this._weight = newWeight;
	}
	
	@Override	
	public int compareTo(WeightedEdge otherEdge) {								//Comparable로 인해 자동 생성된 함수. 
		// TODO Auto-generated method stub
		if(this.weight() < otherEdge.weight()) {								//해당 엣지의 weight와 인자로 받은 다른 edge의 weight를 비교해서 해당 edge가 작으면 -1, 크면 +1, 같으면 0 반환. 
			return -1;															
		}
		else if(this.weight() > otherEdge.weight()) {
			return +1;
		}
		else {
			return 0;
		}
	}

}
