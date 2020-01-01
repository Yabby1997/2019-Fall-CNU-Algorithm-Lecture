package sort;

import java.util.Random;                                                                                                //랜덤한 데이터를 만들기위해 Random import

public class QuickSortByPivotRandom<E extends Comparable<E>> extends QuickSort<E>{
    private Random _random;

    public QuickSortByPivotRandom(boolean givenSortingOrder){
        super(givenSortingOrder);
        this.setRandom(new Random());
    }
                /*
                final exam practice
                 */
                public QuickSortByPivotRandom(boolean givenSortingOrder){
                    super(givenSortingOrder);
                    this.setRandom(new Random());
                }

                public QuickSortByPivotRandom(boolean givenSortingOrder){
                    super(givenSortingOrder);
                    this.setRandom(new Random());
                }

                public QuickSortByPivotRandom(boolean givenSortingOrder){
                    super(givenSortingOrder);
                    this.setRandom(new Random());
                }

                public QuickSortByPivotRandom(boolean givenSortingOrder){
                    super(givenSortingOrder);
                    this.setRandom(new Random());
                }
                /*
                final exam practice
                 */

    //getter setter
    public Random random(){
        return this._random;
    }

    public void setRandom(Random newRandom){
        this._random = newRandom;
    }

    @Override
    protected int pivot(E[] aList, int left, int right){
        int randomLocationFromLeft = this.random().nextInt(right - left + 1);                                    //right - left + 1 (범위크기) 에서 난수추첨하여 더한것을 피봇으로 삼음
        int pivot = left + randomLocationFromLeft;
        return pivot;
    }


                /*
                final exam practice
                 */
                protected int pivot(E[] aList, int left, int right){
                    int randomFromLeft = this.random().nextInt(right - left + 1);
                    int pivot = left + randomFromLeft;
                    return pivot;
                }

                protected int pivot(E[] aList, int left, int right){
                    int randomFromLeft = this.random().nextInt(right - left + 1);
                    int pivot = left + randomFromLeft;
                    return pivot;
                }

                protected int pivot(E[] aList, int left, int right){
                    int randomFromLeft = this.random().nextInt(right - left + 1);
                    int pivot = left + randomFromLeft;
                    return pivot;
                }

                protected int pivot(E[] aList, int left, int right){
                    int randomValue = this.random().nextInt(right - left + 1);
                    return left + randomValue;
                }

                protected int pivot(E[] aList, int left, int right){
                    int randomValue = this.random().nextInt(right - left + 1);
                    return left+ randomValue;
                }

                protected int pivot(E[] aList, int left, int right){
                    int randomValue = this.random().nextInt(right - left + 1);
                    return left + randomValue;
                }
                /*
                final exam practice
                 */
}
