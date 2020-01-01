package model;

public abstract class ReferenceListOrderedByCoordinate extends ReferenceList{
    private PointSet _pointSet;

    public ReferenceListOrderedByCoordinate(PointSet givenPointSet){
        super(givenPointSet.size());
        this.setPointSet(givenPointSet);
        this.generateReferenceListOrderedByCoordinate();                                                                //실제로 값들을 정렬하는게 아니고 원래 배열의 인덱스를 정렬해놓은 배열
    }

    protected PointSet pointSet(){
        return this._pointSet;
    }

    protected void setPointSet(PointSet newPointSet){
        this._pointSet = newPointSet;
    }

    protected abstract int coordinateReferencedByIndex(int i);

    private void swap(int i, int j){
        Integer temp = this.elementAt(i);                                                                               //swap으 인한 문제점?
        this.setElementAt(this.elementAt(j), i);
        this.setElementAt(temp, j);
    }

    private int compareCoordinate(int i, int j){
        int coordinateReferencedByI = this.coordinateReferencedByIndex(i);
        int coordinateReferenceByJ = this.coordinateReferencedByIndex(j);
        if(coordinateReferencedByI < coordinateReferenceByJ){
            return -1;
        }
        else if(coordinateReferencedByI > coordinateReferenceByJ){
            return 1;
        }
        else{                                                                                                           //좌표가 같다면
            if(this.elementAt(i) < this.elementAt(j)){                                                                  //원래 리스트에서의 인덱스를 기준으로 비교
                return -1;
            }
            else if(this.elementAt(j) > this.elementAt(i)){
                return 1;
            }
            else{
                return 0;
            }
        }
    }

    private int pivotByMedian(int left, int right) {
        int mid = (left + right) / 2;                                                                                   //중앙값과 양쪽끝, 3개값중 중간값을 사용
        if (this.compareCoordinate(left, mid) < 0) {                                                                    // left---mid
            if (this.compareCoordinate(mid, right) < 0) {                                                               // left---mid---right
                return mid;
            }
            else if (this.compareCoordinate(left, right) < 0) {                                                         // left---right---mid
                return right;
            }
            else {                                                                                                      // right---left---mid
                return left;
            }
        }
        else{                                                                                                           // mid---left
            if(this.compareCoordinate(right, mid) < 0){                                                                 // right---mid---left
                return mid;
            }
            else if(this.compareCoordinate(right, left) < 0) {                                                          // mid---right---left
                return right;
            }
            else{                                                                                                       // mid---left---right
                return left;
            }
        }
    }

    private int partition(int left, int right){
        int pivotIndexByMedian = this.pivotByMedian(left, right);                                                       //pivot을 median방식으로 선정
        this.swap(left, pivotIndexByMedian);                                                                            //left와 pivot을 바꿔준다.
        int pivotIndex = left;                                                                                          //pivot을 맨 왼쪽으로 옮겨놨으므로 pivot의 인덱스를 left로 바꿈.

        int toRight = left;                                                                                             //left에서 right로
        int toLeft = right + 1;                                                                                         //right에서 left로
        do {
            do {toRight++;}while(this.compareCoordinate(toRight, pivotIndex) < 0);                                      //pivot의 값보다 큰걸 찾아가고
            do {toLeft--;}while (this.compareCoordinate(toLeft, pivotIndex) > 0);                                       //pivot의 값보다 작은걸 찾아간다.
            if (toRight < toLeft) {
                this.swap(toRight, toLeft);                                                                             //찾아진 값이 있어야하는 위치에 있지 않다면 swap을 통해 자리를 바꿔준다.
            }
        }while(toRight < toLeft);                                                                                       //left가 right를 넘어가기까지 반복함.
        this.swap(toLeft, pivotIndex);                                                                                  //끝났을 때 left의 위치가 pivot이 들어갈 위치가 된다.
        return toLeft;
    }

    private void qsortByCoordinateRecursively(int left, int right){
        if(left < right){
            int mid = this.partition(left, right);
            this.qsortByCoordinateRecursively(left, mid - 1);
            this.qsortByCoordinateRecursively(mid + 1, right);
        }
    }

    private void sortByCoordinate (){                                                                                   //제일 큰값은 맨 우측에 미리 두고 그 앞까지만 quickSort
        if(this.size() >= 2 ){
            int indexOfMaxCoordinate = 0;
            for(int index = 1; index < this.size(); index++){
                if(this.compareCoordinate(indexOfMaxCoordinate, index) < 0){
                    indexOfMaxCoordinate = index;
                }
            }
            this.swap (indexOfMaxCoordinate, this.size()-1);
            this.qsortByCoordinateRecursively(0, this.size()-2);
        }
    }

    private void generateReferenceListOrderedByCoordinate(){
        for(int index = 0; index < this.capacity(); index++){                                                           //레퍼런스(인덱스)를 사용한다. 배열에 인덱스를 넣는다.
            this.add(Integer.valueOf(index));
        }
        this.sortByCoordinate();
    }
}
