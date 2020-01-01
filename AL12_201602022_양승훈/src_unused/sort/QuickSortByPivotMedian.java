package sort;

public class QuickSortByPivotMedian<E extends Comparable<E>> extends QuickSort<E> {
    public QuickSortByPivotMedian(boolean givenSortingOrder){
        super(givenSortingOrder);
    }

    @Override
    protected int pivot(E[] aList, int left, int right){
        if((right - left) < 3){
            return left;
        }
        int mid = (left + right) / 2;
        if(this.compare(aList[left], aList[mid]) < 0){
            if(this.compare(aList[mid], aList[right]) < 0){                                                             //left < mid < right 이라면
                return mid;
            }
            else{
                if(this.compare(aList[left], aList[right]) < 0){                                                        //left < right < mid인 경우
                    return right;
                }
                else{
                    return left;                                                                                        //right < left < mid의 경우
                }
            }
        }
        else{
            if(this.compare(aList[left], aList[right]) < 0){                                                            //mid < left < right인 경우
                return left;
            }
            else{
                if(this.compare(aList[mid], aList[right]) < 0){                                                         //mid < right < left인 경우
                    return right;
                }
                else{
                    return mid;                                                                                         //right < mid < left의 경우
                }
            }
        }
    }
}
