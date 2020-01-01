package sort;

public class InsertionSort<E extends Comparable<E>> extends Sort<E> {
    //constructor
    public InsertionSort(boolean givenSortingOrder) {
        super(givenSortingOrder);
    }

    public boolean sort(E[] aList) {
        if (aList.length <= 1) {                                                                                        //리스트의 사이즈가 1보다 작다면 정렬할 수 없음. false반환.
            return false;
        }
        int minLoc = 0;                                                                                                 //가장 작은 요소의 위치를 0으로 초기화하고
        for (int i = 1; i < aList.length; i++) {                                                                        //배열의 모든 요소들을 순회하며
            if (this.compare(aList[minLoc], aList[i]) > 0) {                                                            //minLoc과 해당 위치의 요소의 크기를 비교해서 i번째 요소가 더 작다면
                minLoc = i;                                                                                             //minLoc을 i로 바꿔줌.
            }
        }                                                                                                               //반복문이 끝나면 minLoc은 리스트 상의 가장 작은 원소의 위치임.
        this.swap(aList, minLoc, 0);                                                                                 //인덱스 0번으로 옮겨줌. 센티넬 역할을 한다.

        //이해가 필요하다.
        for(int i = 2; i < aList.length; i++){
            E insertedElement = aList[i];
            int j = i - 1;
            while(this.compare(aList[j], insertedElement) > 0){                                                         //앞 요소와 뒷 요소를 계속 비교해가면서 들어갈 위치 찾기
                aList[j + 1] = aList[j];
                j--;
            }
            aList[j + 1] = insertedElement;
        }
        return true;
    }
}
                /*
                final exam practice
                 */
                public boolean sort(E[] aList){
                    if(aList.length <= 1){
                        return false;
                    }
                    int maxIndex = 0;
                    for(int i = 1; i < aList.length; i++){
                        if(this.compare(aList[maxIndex], aList[i]) < 0)
                            maxIndex = i;
                    }
                    this.swap(aList, maxIndex, aList.length - 1);

                    for(int i = aList.length - 3; i >= 0 i--){
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

                public boolean sort(E[] aList){
                    if(aList.length <= 1){
                        return false;
                    }
                    int maxIndex = 0;
                    for(int i = 1; i < aList.length; i++){
                        if(this.compare(aList[maxIndex], aList[i]) < 0)
                            maxIndex = i;
                    }
                    this.swap(aList, maxIndex, aList.length - 1);

                    for(int i = aList.length - 3; i >= 0; i--){
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

                public boolean sort(E[] aList){
                    if(aList.length <= 1)
                        return false;
                    int maxIndex = 0;
                    for(int i = 1; i < aList.length; i++){
                        if(this.compare(aList[maxIndex], aList[i]) < 0)
                            maxIndex = i;
                    }
                    this.swap(aList, maxIndex, aList.length - 1);

                    for(int i = aList.length - 3; i >= 0; i--){
                        E insertedElement = aList[i];
                        int j = i + 1;
                        while(this.compare(aList[j] < insertedElement) < 0){
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