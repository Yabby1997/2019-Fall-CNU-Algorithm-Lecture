package priorityQ;

public class MinPriorityQByHeap<E extends Comparable<E>> extends MinPriorityQ<E>{			//generic type으로 E를 쓸 것인데 해당 클래스 내부에서 E끼리의 비교가 발생할 수 있음을 명시한다. 제한을 둔다.
    private static final int HEAP_ROOT = 1;						//최소우선순위큐는 heap으로 구성되는데 heap은 완전 이진트리의 형태를 갖는다. 배열로 이진트리를 나타낼 때 root를 인덱스 1로한다.

    private E[] _heap;											//배열로 구현하는 힙

    public MinPriorityQByHeap() {
        super();
    }


    @SuppressWarnings("unchecked")
    public MinPriorityQByHeap(int givenCapacity) {
        super(givenCapacity);
        this.setHeap((E[]) new Comparable[this.capacity() + 1]);	//E는 Comparable을 상속받고있으므로 Object대신 Comparable을 사용해 주어야 한다.
    }

    //heap관련 getter setter는 클래스 내부에서만 사용하도록 private으로 선언한다.
    private E[] heap() {
        return this._heap;
    }

    private void setHeap(E[] newHeap) {
        this._heap = newHeap;
    }

    public boolean add(E anElement) {
        if(this.isFull()) {											                                                    //heap이 가득 찼다면 더 이상 원소가 들어갈 수 없으므로 false 반환.
            return false;
        }
        else {
            int positionForAdd = this.size() + 1;															            //맨 끝부터 시작 사이즈도 1 증가.
            this.setSize(positionForAdd);																	            //배열로 구현한 이진트리에서, index / 2 는 해당 요소의 부모를 가리키는 index다.
            while((positionForAdd) > 1 && (anElement.compareTo(this.heap()[positionForAdd / 2]) < 0)) {		            //확인하는 위치가 root보다 아랫단이며, 해당 위치의 부모와 비교할 때 부모보다 더 작으면(1대신 root쓰는기 맞지않나?)
                this.heap()[positionForAdd] = this.heap()[positionForAdd / 2];								            //부모가 아래로 내려오고
                positionForAdd = positionForAdd / 2;														            //확인하는 위치를 부모위치로 잡고 반복.
            }
            this.heap()[positionForAdd] = anElement;														            //root까지 올라가버렸거나, 더이상 부모보다 작은경우가 아니라면 해당 위치가 삽입될 위치이다.
            return true;																					            //성공적으로 add됐기 때문에 true 반환.
        }
    }

    public E min() {
        if(this.isEmpty()) {										                                                    //만약 heap이 비어있다면 아무것도 없는것이다. 반환해줄게 없다. null반환.
            return null;
        }
        else {														                                                    //비어있지 않다면 root에 가장작은 값이 있게 된다. add할때 항상 비교를하며 삽입하였기 때문.
            return this.heap()[MinPriorityQByHeap.HEAP_ROOT];
        }
    }

    public E removeMin() {
        if(this.isEmpty()) {
            return null;											                                                    //min()에서와 마찬가지로 heap이 비어있다면 제거할 수 없다. null반환.
        }
        else {
            E rootElement = this.heap()[MinPriorityQByHeap.HEAP_ROOT];	                                                //제거될 root는 heap의 첫번째(root)요소이다.
            this.setSize(this.size() - 1);							                                                    //제거될테니 사이즈 1 감소
            if(this.size() > 0) {									                                                    //제거됐을때 사이즈가 0이 아니라면, rootElement제외하고도 다른 요소들이 heap에 있으므로, 재정렬을 시켜주어야 한다.
                E lastElement = this.heap()[this.size() + 1];		                                                    //제거 후 맨 마지막에 위치할 요소를 지정해둠.
                int parent = MinPriorityQByHeap.HEAP_ROOT;				                                                //root가 부모인경우부터 시작.
                while((parent * 2) < this.size()) {				                                                        //parent * 2를하면 왼쪽 자식, * 2 + 1을하면 오른쪽 자식인데, *2가 사이즈보다 작다 -> 즉 유효해야만 자식이 있는것이다. 자식이 있는지 판별하는거
                    int smallerChild = parent * 2;					                                                    //왼쪽 자식 smallerChild
                    if((smallerChild < this.size()) && (this.heap()[smallerChild].compareTo(this.heap()[smallerChild + 1]) > 0)){	//왼쪽 자식이 "유효하고" 오른쪽 자식보다 크다면
                        smallerChild++;								                                                    //오른쪽이 더 작았으니까 smallerChild++ 즉, 오른쪽 자식이 smallerChild가 됨.
                    }
                    if(lastElement.compareTo(this.heap()[smallerChild]) <= 0) {			                                //마지막 요소랑 smallerChild랑 비교했는데 마지막 요소가 더 작거나 같다?
                        break;															                                //그럼 마지막 요소가 부모로 와야한다 break;
                    }
                    this.heap()[parent] = this.heap()[smallerChild];			                                        //더 작은 child가 새 parent가 되고
                    parent = smallerChild;										                                        //child위치를 parent로 잡고 반복
                }
                this.heap()[parent] = lastElement;
            }												                                                            //제거됐을 때 사이즈가 0이라면 rootElement하나만 heap에 있었다는 거니까 반환해주고 끝내면 된다.
            return rootElement;
        }
    }
}
