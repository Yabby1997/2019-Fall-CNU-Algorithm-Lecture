package sort;

public class QuickSortWithInsertionSort<E extends Comparable<E>> extends QuickSortByPivotRandom<E>{                     //삽입정렬 하지 않는 퀵소트는 기본적으로 랜덤한 피봇을 선정하도록 한다.
    private static final int MAX_SIZE_FOR_INSERTION_SORT = 20;                                                          //20개 이하의 요소에 대해서는 퀵소트보다 삽입 소트가 더 빠르게 작동한다.

    public QuickSortWithInsertionSort(boolean givenSortingOrder){
        super(givenSortingOrder);
    }

    private boolean insertionSort(E[] aList, int left, int right){                                                      //삽입정렬 함수
        for(int i = (right - 1); i >= left; i--){
            E insertedElement = aList[i];
            int j = i + 1;
            while(this.compare(aList[j], insertedElement) < 0){
                aList[j - 1] = aList[j];
                j++;
            }
            aList[j - 1] = insertedElement;
        }
        return true;
    }

    @Override
    public void quickSortRecursively(E[] aList, int left, int right){
        int currentSize = right - left;
        if(currentSize > 0){                                                                                            //리스트의 사이즈가 유효하고
            if(currentSize <= QuickSortWithInsertionSort.MAX_SIZE_FOR_INSERTION_SORT){                                  //20보다 작다면 퀵소트보다 삽입 정렬이 더 효율적이므로 삽입정렬로 처리
                this.insertionSort(aList, left, right);
            }
            else{                                                                                                       //그렇지 않다면 퀵소트를 한다.
                int mid = partition(aList, left, right);
                quickSortRecursively(aList, left, mid - 1);
                quickSortRecursively(aList, mid + 1, right);
            }
        }
    }
}
