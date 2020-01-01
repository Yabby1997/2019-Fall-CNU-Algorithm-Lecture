package sort;

public class QuickSort<E extends Comparable<E>> extends Sort<E>{
    public QuickSort(boolean givenSortingOrder){
        super(givenSortingOrder);
    }

    protected int pivot(E[] aList, int left, int right){
        return left;                                                                                                    //피봇으로 그냥 레프트를 준다. 다른 클래스에서 다른방식으로 구현할수도 있으므로 protected
    }

    protected int partition(E[] aList, int left, int right){                                                            //다른 오버라이드 될 함수에서 사용가능이어야 하므로 protected
        int pivot = this.pivot(aList, left, right);
        this.swap(aList, left, pivot);                                                                                  //소트를 위해 피봇과 left를 바꿔준다.
        E pivotElement = aList[left];
        int toRight = left;
        int toLeft = right + 1;                                                                                         //센티넬을 위해서 마지막 요소를 제일 큰 값으로 했었음.

        do{
            do{ toRight++; } while(this.compare(aList[toRight], pivotElement) < 0);                                     //좌측 파티션엔 피봇보다 작은값이 있으면 된다.
            do{ toLeft--; } while(this.compare(aList[toLeft], pivotElement) > 0);                                       //우측 파티션엔 피봇보다 큰 값이 있으면 된다.
            if(toRight < toLeft){
                this.swap(aList, toRight, toLeft);                                                                      //찾아진 요소 toRight가 toLeft보다 작으면, 즉 서로의 파티션을 침범하지 않았으면 스왑.
            }
        }while(toRight < toLeft);                                                                                       //서로의 파티션을 침범하는 때 까지 반복.
        this.swap(aList, left, toLeft);                                                                                 //이건 왜 하는거지?
        return toLeft;                                                                                                  //pivot 위치를 반환.
    }

    protected void quickSortRecursively(E[] aList, int left, int right){
        if(left < right){
            int mid = partition(aList, left, right);                                                                    //좌 우에 작은값 큰값들만 모아서 분할
            quickSortRecursively(aList, left, mid - 1);                                                           //분할된것 기준으로 재귀적으로 다시 반복.
            quickSortRecursively(aList, mid + 1, right);
        }
    }

    public boolean sort(E[] aList){
        if(aList.length < 1){
            return false;                                                                                               //리스트의 사이즈가 1 미만이면 정렬할 수 없다.
        }
        if(aList.length > 1){
            int maxLoc = 0;
            for(int i = 1; i < aList.length; i++) {
                if (this.compare(aList[maxLoc], aList[i]) < 0) {                                                        //최대값을 찾아 가장오른쪽에 둠. 센티넬
                    maxLoc = i;
                }
            }
            this.swap(aList, maxLoc, aList.length - 1);
            this.quickSortRecursively(aList, 0, aList.length - 2);
        }
        return true;
    }
}
