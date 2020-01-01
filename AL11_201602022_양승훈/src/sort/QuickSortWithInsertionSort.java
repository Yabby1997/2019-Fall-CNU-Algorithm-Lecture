package sort;

public class QuickSortWithInsertionSort<E extends Comparable<E>> extends QuickSortByPivotRandom<E>{                     //삽입정렬 하지 않는 퀵소트는 기본적으로 랜덤한 피봇을 선정하도록 한다.
    private static final int DEFAULT_MAX_SIZE_FOR_INSERTION_SORT = 20;                                                          //20개 이하의 요소에 대해서는 퀵소트보다 삽입 소트가 더 빠르게 작동한다.

    private int _maxSizeForInsertionSort;

    public QuickSortWithInsertionSort(boolean givenSortingOrder){
        super(givenSortingOrder);
        this.setMaxSizeForInsertionSort(DEFAULT_MAX_SIZE_FOR_INSERTION_SORT);
    }
                /*
                final exam practice
                 */
                public QuickSortWithInsertionSort(boolean givenSortingOrder){
                    super(givenSortingOrder);
                    this.setMaxSizeForInsertionSort(20);
                }

                public QuickSortWithInsertionSort(boolean givenSortingOrder){
                    super(givenSortingOrder);
                    this.setMaxSizeForInsertionSort(20);
                }

                public QuickSortWithInsertionSort(boolean givenSortingOrder){
                    super(givenSortingOrder);
                    this.setMaxSizeForInsertionSort(20);

                }
                /*
                final exam practice
                 */

    //getter/setter
    public int maxSizeForInsertionSort(){
        return this._maxSizeForInsertionSort;
    }

    public void setMaxSizeForInsertionSort(int newMaxSizeForInsertionSort){
        this._maxSizeForInsertionSort = newMaxSizeForInsertionSort;
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
                /*
                final exam practice
                 */
                private boolean insertionSort(E[] aList, int left, int right){
                    for(int i = (right - 1); i >= left; i--){
                        E insertedElement = aList[i];
                        int j = i + 1;
                        while(this.compare(aList[j], insertedElement < 0)){
                            aList[j - 1] = aList[j];
                            j++;
                        }
                        aList[j - 1] = insertedElement;
                    }
                    return true;
                }

                private boolean insertionSort(E[] aList, int left, int right){
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

                private boolean insertionSort(E[] aList, int left, int right){
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

                private boolean insertionSort(E[] aList, int left, int right){                                          // 9 4 2 1 5 6
                    for(int i = (right - 1); int i >= left; i--){                                                       // 9 4 2 1 5 6    5
                        E insertedElement = aList[i];                                                                   // 9 5 2 1 5 6    5  6
                        int j = i + 1;
                        while(this.compare(aList[j] < insertedElement) < 0){                                            // 6 < 5 x
                            aList[j - 1] = aList[j];                                                                    // 9 4 2 1 5 6      1
                            j++;                                                                                        // 9 4 2 1 5 6      1  5
                        }                                                                                               // 9 4 2 5 5 6      1  5
                        aList[j - 1] = insertedElement;                                                                 // 9 4 2 5 6 6      1
                    }
                    return true;
                }

                private boolean insertionSort(E[] aList, int left, int right){
                    for(int i = right - 1; i >= left; i--){
                        E insertedElement = aList[i];
                        j = i + 1;
                        for(this.compare(aList[j], insertedElement) < 0){
                            aList[j - 1] = aList[j];
                            j++;
                        }
                        aList[j - 1] = insertedElement;
                    }
                    return true;
                }

                private boolean insertionSort(E[] aList, int left, int right){                                          //센티넬을 찾아놓고 하기때문에 괜찮다!!!
                    for(int i = right - 1; i >= left; i--){
                        E insertedElement = aList[i];
                        int j = i + 1;
                        for(this.compare(aList[j], insertedElement) < 0){
                            aList[j - 1] = aList[j];
                            j++;
                        }
                        aList[j - 1] = insertedElement;
                    }
                    return true;
                }

                private boolean insertionSort(E[] aList, int left, int right){
                    for(int i = right - 1; i >= left; i--){
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

                private boolean insertionSort(E[] aList, int left, int right){
                    for(int i = right - 1; i >= left; i--){
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

                private boolean insertionSort(E[] alist, int left, int right){
                    for(int i = right - 1; i >= left; i--){
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
                /*
                final exam practice
                 */

    @Override
    public void quickSortRecursively(E[] aList, int left, int right){
        int currentSize = right - left;
        if(currentSize > 0){                                                                                            //리스트의 사이즈가 유효하고
            if(currentSize <= this.maxSizeForInsertionSort()){                                                          //20보다 작다면 퀵소트보다 삽입 정렬이 더 효율적이므로 삽입정렬로 처리
                this.insertionSort(aList, left, right);
            }
            else{                                                                                                       //그렇지 않다면 퀵소트를 한다.
                int mid = partition(aList, left, right);
                quickSortRecursively(aList, left, mid - 1);
                quickSortRecursively(aList, mid + 1, right);
            }
        }
    }
                /*
                final exam practice
                 */
                public void quickSortRecursively(E[] aList, int left, int right){
                    int currentSize = right - left;
                    if(currentSize > 0){
                        if(currentSize <= this.maxSizeForInsertionSort()){
                            this.insertionSort(aList, left, right);
                        }
                        else{
                            int mid = this.partition(aList, left, right);
                            this.quickSortRecursively(aList, left, mid - 1);
                            this.quickSortRecursively(aList, mid + 1, right);
                        }
                    }
                }

                public void quickSortRecursively(E[] aList, int left, int right){
                    int currentSize = right - left;
                    if(currentSize > 0){
                        if(currentSize <= this.maxSizeForInsertionSort()){
                            this.insertionSort(aList, left, right);
                        }
                        else{
                            int mid = this.partition(aList, left, right);
                            this.quickSortRecursively(aList, left, mid - 1);
                            this.quickSortRecursively(aList, mid + 1, right);
                        }
                    }
                }

                public void quickSortRecursively(E[] aList, int left, int right){
                    int currentSize = right - left;
                    if(currentSize > 0){
                        if(currentSize < 20){
                            this.insertionSort(aList, left, right);
                        }
                        else{
                            int mid = this.partition(aList, left, right);
                            this.quickSortRecursively(aList, left, mid - 1);
                            this.quickSortRecursively(aList, mid + 1, right);
                        }
                    }
                }
                /*
                final exam practice
                 */

}
