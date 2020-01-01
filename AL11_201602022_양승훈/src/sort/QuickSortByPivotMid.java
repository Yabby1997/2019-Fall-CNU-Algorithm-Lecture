package sort;

public class QuickSortByPivotMid<E extends Comparable<E>> extends QuickSort<E>{
    public QuickSortByPivotMid(boolean givenSortingOrder){
        super(givenSortingOrder);
    }

    @Override
    protected int pivot(E[] aList, int left, int right){                                                                //중앙에 위치한 요소를 피봇으로 삼는다.
        return ((left + right) / 2);
    }
                /*
                final exam practice
                 */
                protected int pivot(E[] aList, int left, int right){
                    return ((left + right) / 2);
                }

                protected int pivot(E[] aList, int left, int right){
                    return (left + right) / 2;
                }

                protected int pivot(E[] aList, int left, int right){
                    return (left + right) / 2;
                }
                /*
                final exam practice
                 */
}
