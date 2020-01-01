
public class PairwiseDisjointSets {
	private static final int INITIAL_SINGLETON_SET_SIZE = 1;		//초기 사이즈 1을 상수로 선언. 초기의 vertex들은 각각 개별의 set에 하나씩 들어있다.
	private int _numberOfElements;
	private int[] _parentTree;
	
	public PairwiseDisjointSets(int givenNumberOfElements) {
		this.setNumberOfElements(givenNumberOfElements);			//멤버변수를 입력할때도 setter를 사용하자.
		this.setParentTree(new int[this.numberOfElements()]);		//멤버변수에 접근할때도 getter를 사용하자. 
		for(int rootOfSignletonSet = 0; rootOfSignletonSet < this.numberOfElements(); rootOfSignletonSet++) {		//초기에 각 요소들은 부모가 없는 singleton들이다.
			this.setSizeOfSetFor(rootOfSignletonSet,  INITIAL_SINGLETON_SET_SIZE);			//따라서 for loop를 돌며 사이즈를 INITIAL_SINGLETON_SET_SIZE로 초기화하는 과정을 거친다.
			//여기서 setSizeOfSetFor이 아닌 setParentOf를 실수로 사용하여 사이즈가 1로 초기화되며 find함수에서 무한 loop가 발생, 결과가 나오지 않는 문제가 발생했었다.... 
		}
	}
	
	private int numberOfElements() {						//getter setter는 모두 private으로 구현한다.
		return  this._numberOfElements;
	}
	
	private int[] parentTree() {
		return this._parentTree;
	}
	
	private void setNumberOfElements(int newNumberOfElements) {
		this._numberOfElements = newNumberOfElements;
	}
	
	private void setParentTree(int[] newParentTree) {
		this._parentTree = newParentTree;
	}
	
	private int parentOf(int aMember) {
		return this.parentTree()[aMember];					//this의 parentTree()함수를 통해 _parentTree를 반환받고 그 aMember번째 index에 접근한다.
	}														//아래의 다른 함수들에서도 이와같이 접근한다. 멤버 변수에 직접적으로 접근하는건 피하는편이 좋다.
	
	private void setParentOf(int aChildMember, int newParentMember) {
		this.parentTree()[aChildMember] = newParentMember;						//자식멤버의 index를 가진 요소는 부모멤버에 대한 정보를 가진다.
	}
	
	private boolean parentDoesExist(int aMember) {			//인덱스는 자식을 요소값은 부모를 나타낸다. 부모값이 0보다 커야 부모가 존재하는 것.
		return (this.parentOf(aMember) >= 0);
	}
	
	private int sizeOfSetFor(int aRoot) {
		return (-this.parentOf(aRoot));						//root인덱스는 부모가 없다. 따라서 요소에 저장되는값은 부모의 정보가 아니다. 우리는 대신 집합의 크기를 음수로 만들어 저장하기로 한다.
	}														//따라서 aRoot번째 요소의 값에 음수를 취해주면 해당 집합의 크기를 알 수 있다.
	
	private void setSizeOfSetFor(int aRoot, int newSize) {
		this.setParentOf(aRoot, -newSize);				//자식노드들의 부모를 설정해주듯, root의 부모를 size의 음수로 넣어주면 된다.
	}
	
	public int find(int aMember) {
		int rootCandidate = aMember;						//aMember에서부터 시작한다.
		while(this.parentDoesExist(rootCandidate)) {		//candidate가 부모를 가진다면 그건 root가 아니다.
			rootCandidate = this.parentOf(rootCandidate);	//따라서 candidate의 부모를 candidate로 하고 반복. 부모를 갖지 않는경우(=root)까지 반복한다.
			
		}
		int root = rootCandidate;							//while loop을 거친 후 최종의 candidate가 root다.
		
		int child = aMember;								//collapsing rule을 적용하기 위한 처리를 시작한다.
		int parent = this.parentOf(child);
		if(parent >= 0) {									//collapsing rule은 자식, 손자, 증손자 등의 부모를 바로 root로 만들어 tree의 높이를 줄이는 rule이다.
			while(parent != root) {							//parent가 root와 다르다면 loop
				this.setParentOf(child, root);				//child의 부모를 root로 지정 (collapsing rule 적용. 트리의 높이가 짧아진다.)
				child = parent;								//child 가 parent가 되
				parent = this.parentOf(child);				//parent는 child의 부모로해서 반복하며 root에 도달할때까지 collapse
			}
		}
		return root;
	}
	
	public void union(int aMemberA, int aMemberB) {
		int rootOfSetA = this.find(aMemberA);				//aMemberA가 속한 집합의 root
		int rootOfSetB = this.find(aMemberB);				//aMemberB가 속한 집합의 root
		int sizeOfSetA = this.sizeOfSetFor(rootOfSetA);		//weighting rule을 적하기 위해 두 집합의 사이즈를 비교한다.
		int sizeOfSetB = this.sizeOfSetFor(rootOfSetB);	
		
		if(sizeOfSetA < sizeOfSetB) {						//size가 작은 집합이 size가 큰 집합보다 높이가 낮을 가능성이 크다. 따라서 작은쪽이 큰쪽의 자식이 된다.
			this.setParentOf(rootOfSetA,  rootOfSetB);
			this.setSizeOfSetFor(rootOfSetB, (sizeOfSetA + sizeOfSetB));
		}
		else {
			this.setParentOf(rootOfSetB,  rootOfSetA);
			this.setSizeOfSetFor(rootOfSetA, (sizeOfSetA + sizeOfSetB));
		}
	}
}
