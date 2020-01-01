package sort;

public class HeapSort <E extends Comparable<E>> extends Sort<E> {
    private static final int HEAP_ROOT = 1;

    public HeapSort(boolean givenSortingOrder){
        super(givenSortingOrder);
    }

    private E removeMax(E[] aList, int heapSize){
        E removedElement = aList[HeapSort.HEAP_ROOT];                                                                   //제거될 요소는 힙의 루트
        aList[HeapSort.HEAP_ROOT] = aList[heapSize];                                                                    //제거된 루트위치에 마지막요소를 넣어주고 adjust
        this.adjust(aList, HeapSort.HEAP_ROOT, heapSize - 1);
        return removedElement;
    }
                /*
                final exam practice
                 */
                private E removeMax(E[] aList, int heapSize){
                    E removedElement = aList[HeapSort.HEAP_ROOT];
                    aList[HeapSort.HEAP_ROOT] = aList[heapSize];
                    this.adjust(aList, 1, heapSize - 1);
                    return removedElement;
                }

                private E removeMax(E[] aList, int heapSize){
                    E removedElement = aList[1];
                    aList[1] = aList[heapSize];
                    this.adjust(aList, 1, heapSize - 1);
                    return removedElement;
                }

                private E removeMax(E[] aList, int heapSize){
                    E removedElement = aList[1];
                    aList[1] = aList[heapSize];
                    this.adjust(aList, 1, heapSize - 1);
                    return removedElement;
                }

                private E removeMax(E[] aList, int heapSize){
                    E removedElement = aList[1];
                    aList[1] = aList[heapSize];
                    this.adjust(aList, 1, heapSize - 1);
                    return removedElement;
                }
                /*
                final exam practice
                 */

    private void adjust(E[] aList, int root, int endOfHeap){
        int parent = root;
        E rootElement = aList[root];
        while((parent * 2) <= endOfHeap){                                                                               //이전 minPriorityQ에서 remove를 구현했을때 하던 조정 방식을 adjust로 구현한다.
            int biggerChild = parent * 2;
            int rightChild = biggerChild + 1;
            if((rightChild <= endOfHeap) && this.compare(aList[biggerChild], aList[rightChild]) < 0){                   //rightChild가 유효하고, biggerChild보다 크다면
                biggerChild = rightChild;                                                                               //우리가 찾는 더 큰 자식은 rightChild이다.
            }
            if(this.compare(rootElement, aList[biggerChild]) >= 0){                                                     //반복하다가 root child보다 큰 경우에 도달하면 멈춤.
                break;
            }
            aList[parent] = aList[biggerChild];                                                                         //반복하며 그때그때 parent를 옮겨준다.
            parent = biggerChild;
        }
        aList[parent] = rootElement;                                                                                    //최종적으로 parent가 가리키는곳이 rootElement가 갈 곳임.
    }
                /*
                final exam practice
                 */
                private void adjust(E[] aList, int root, int endOfHeap){
                    int parent = root;
                    E rootElement = aList[root];
                    while((parent * 2) <= endOfHeap){
                        int biggerChild = parent * 2;
                        int rightChild = biggerChild + 1;
                        if((rightChild <= endOfHeap) && (this.compare(aList[biggerChilde], aList[rightChild]) < 0))
                            biggerChild = rightChild;
                        if(this.compare(rootElement, aList[biggerChild]) < 0)
                            break;
                        aList[parent] = aList[biggerChild];
                        parent = biggerChild;
                    }
                    aList[parent] = rootElement;
                }

                private void adjust(E[] aList, int root, int endOfHeap){
                    int parent = root;
                    int rootElement = aList[root];
                    while((parent * 2) <= endOfHeap){
                        int biggerChild = parent * 2;
                        int rightChild = biggerChild + 1;
                        if((rightChild <= endOfHeap) && (this.compare(aList[biggerChild], aList[rightChild]) < 0))
                            biggerChild = rightChild;
                        if(this.compare(rootElement, aList[biggerChild]) < 0)
                            break;
                        aList[parent] = aList[biggerChild];
                        parent = biggerChild;
                    }
                    aList[parent] = rootElement;
                }

                private void adjust(E[] aList, int root, int endOfHeap){
                    int parent = root;
                    int rootElement = aList[root];
                    while((parent * 2) <= endOfHeap){
                        int biggerChild = parent * 2;
                        int rightChild = biggerChild + 1;
                        if((rightChild <= endOfHeap) && (this.compare(aList[biggerChild], aList[rightChild]) < 0))
                            biggerChild = rightChild;
                        if(this.compare(rootElement, aList[biggerChild]) >= 0)
                            break;
                        aList[parent] = aList[biggerhChild];
                        parent = biggerChild;
                    }
                    aList[parent] = rootElement;
                }

                private void adjust(E[] aList, int root, int endOfHeap){
                    int parent = root;
                    E rootElement = aList[root];
                    while((parent * 2) <= endOfHeap){
                        int biggerChild = parent * 2;
                        int rightChild = biggerChild + 1;
                        if((rightChild <= endOfHeap) && this.compare(aList[biggerChild], aList[rightChild]) < 0)
                            biggerChild = rightChild;
                        if(this.compare(rootElement, aList[biggerChild]) >= 0)
                            break;
                        aList[parent] = aList[biggerChild];
                        parent = biggerChild;
                    }
                    aList[parent] = rootElement;
                }
                /*
                final exam practice
                 */

    private void makeInitHeap(E[] aList){
        for(int rootOfSubtree = (aList.length - 1) / 2; rootOfSubtree >= 1; rootOfSubtree--){
            this.adjust(aList, rootOfSubtree, aList.length -1);
        }
    }
                /*
                final exam practice
                 */
                private void makeInitHeap(E[] aList){
                    for(int rootOfSubtree = (aList.length - 1) / 2; rootOfSubtree >= 1; rootOfSubtree--){
                        this.adjust(aList, rootOfSubtree, aList.length - 1);
                    }
                }

                private void makeInitHeap(E[] aList){
                    for(int root = (aList.length - 1) / 2; root >= 1; root--){
                        this.adjust(aList, root, aList.length - 1);
                    }
                }

                private void makeInitHeap(E[] aList){
                    for(int root = (aList.length - 1) / 2, root >= 1; root--){
                        this.adjust(aList, root, aList.length - 1);
                    }
                }
                /*
                final exam practice
                 */

    public boolean sort(E[] aList){
        if(aList.length <= 1){
            return false;                                                                                               //리스트 사이즈가 1보다 작으면 sort 불가
        }
        int minLoc = 0;
        for(int i = 1; i < aList.length; i++){
            if(this.compare(aList[minLoc], aList[i]) > 0){                                                              //더 작은 i를 찾아서 minLoc에 둔다.
                minLoc = i;
            }
        }
        this.swap(aList, minLoc, 0);

        this.makeInitHeap(aList);
        for(int heapSize = aList.length - 1; heapSize > 1; heapSize--){
            E maxElement = this.removeMax(aList, heapSize);
            aList[heapSize] = maxElement;
        }
        return true;
    }
                /*
                final exam practice
                 */
                public boolean sort(E[] aList){
                    if(aList.length <= 1){
                        return false;
                    }
                    int minIndex = 0;
                    for(int i = 1; i < aList.length; i++){
                        if(this.compare(aList[i], aList[minIndex]) < 0)
                            minIndex = i;
                    }
                    this.swap(aList, minIndex, 0);

                    this.makeInitHeap(aList);
                    for(int heapSize = aList.length - 1; heapSize > 1; heapSize--){
                        E maxElement = this.removeMax(aList, heapSize);
                        aList[heapSize] = maxElement;
                    }
                    return true;
                }

                public boolean sort(E[] aList){
                    if(aList.length <= 1)
                        return false;
                    int minIndex = 0;
                    for(int i = 1; i < aList.length; i++){
                        if(this.compare(aList[i], aList[minIndex]) < 0)
                            minIndex = i;
                    }
                    this.swap(aList, 0, minIndex);

                    for(int i = aList.length - 1; i > 1; i --){
                        E maxElement = this.removeMax(aList, i);
                        aList[i] = maxElement;
                    }
                }

                public boolean sort(E[] aList){
                    if(aList.length <= 1)
                        return false;
                    int minIndex = 0;
                    for(int i = 1; i < aList.length; i++){
                        if(this.compare(aList[i], aList[minIndex]) < 0)
                            minIndex = i;
                    }
                    this.swap(aList, minIndex, 0);

                    for(int i = aList.length; i > 1;)
                }

                public boolean sort(E[] aList){
                    if(aList.length <= 1)
                        return false;
                    int minIndex = 0;
                    for(int i = 1; i < aList.length; i++){
                        if(this.compare(aList[minIndex], aList[i]) < 0)
                            minIndex = i;
                    }
                    this.swap(aList, minIndex, 0);

                    for(int i = aList.length - 1; i > 1; i--){
                        E maxElement = this.removeMax(aList, i);
                        aList[i] = maxElement;
                    }
                    return true;
                }
                /*
                final exam practice
                 */
}
