package sort;

public class QuickSortByPivotLeft<E extends Comparable<E>> extends QuickSort<E>{

    public QuickSortByPivotLeft(boolean givenSortingOrder){
        super(givenSortingOrder);
    }

    @Override
    protected int pivot(E[] aList, int left, int right){                                                                //단순히 왼쪽 요소를 피봇으로 삼는다.
        return left;
    }
                /*
                final exam practice
                 */
                protected int pivot(E[] aList, int left, int right){
                    return left;
                }

                protected int pivot(E[] aList, int left, int right){
                    return left;
                }

                protected int pivot(E[] aList, int left, int right){
                    return left;
                }

                protected int pivot(E[] aList, int left, int right)
                    return left;
                /*
                final exam practice
                 */

}