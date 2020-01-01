package model;

import java.lang.ref.Reference;

public class FindClosestPair {
    private static final int MAX_NUMBER_OF_NEIGHBORS = 7;
    private static final int DEFAULT_MIN_RECURSION_SIZE = 150;

    private PointSet _pointSet;
    private int _minRecursionSize;

    public FindClosestPair() {
        this.setPointSet(null);
        this.setMinRecursionSize(FindClosestPair.DEFAULT_MIN_RECURSION_SIZE);
    }

    private PointSet pointSet() {
        return this._pointSet;
    }

    private void setPointSet(PointSet newPointSet) {
        this._pointSet = newPointSet;
    }

    private int minRecursionSize(){
        return this._minRecursionSize;
    }

    public void setMinRecursionSize(int newMinRecursionSize){
        this._minRecursionSize = newMinRecursionSize;
    }

    public PairOfPoints solveByComparingAllPairs(PointSet pointSetForSolve) {                                           //브루트포스를 통해 모든점들을 비교하여 가장 짧은 거리를 갖는 쌍을 찾는다.
        if (pointSetForSolve == null || pointSetForSolve.size() <= 1) {
            return null;
        }
        Point closestPairPointI = null;
        Point closestPairPointJ = null;
        long minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < pointSetForSolve.size() - 1; i++) {
            Point pointI = pointSetForSolve.elementAt(i);
            for (int j = i + 1; j < pointSetForSolve.size(); j++) {
                Point pointJ = pointSetForSolve.elementAt(j);
                long distanceBetweenIJ = pointI.distanceTo(pointJ);
                if (distanceBetweenIJ < minDistance) {
                    minDistance = distanceBetweenIJ;
                    closestPairPointI = pointI;
                    closestPairPointJ = pointJ;
                }
            }
        }
        return new PairOfPoints(closestPairPointI, closestPairPointJ);
    }

    public PairOfPoints solveByDivideAndConquer(PointSet pointSetForSolve) {
        if (pointSetForSolve == null || pointSetForSolve.size() <= 1) {
            return null;
        }
        this.setPointSet(pointSetForSolve);
        ReferenceList Px = new ReferenceListOrderedByX(this.pointSet());                                                //x와 y reference list로 만듬. 정렬까지 된다.
        ReferenceList Py = new ReferenceListOrderedByY(this.pointSet());
        PairOfPoints closestPair = this.solveRecursively(Px, Py);                                                       //정렬된 reference list를 통해 재귀적으로 문제를 해결한다.
        return closestPair;
    }

    private PairOfPoints solveRecursively(ReferenceList Px, ReferenceList Py) {
        PairOfPoints closestPair;
        if (Px.size() <= 3) {                                                                                           //규모가 작다면 그냥 직접비교로 답을 구한다.
            closestPair = this.closestPairDirectlyFromSmallPointSet(Px);
            return closestPair;
        }
        int separationLine = this.separationLine(Px);                                                                   //그게 아니라면 중앙선을 긋고
        SeparatedPair separatedPairFromPx = this.separateReferenceList(Px, separationLine);                             //중앙선을 기준으로 referenceList를 나눈다.
        SeparatedPair separatedPairFromPy = this.separateReferenceList(Py, separationLine);
        ReferenceList Qx = separatedPairFromPx._leftList;
        ReferenceList Rx = separatedPairFromPx._rightList;
        ReferenceList Qy = separatedPairFromPy._leftList;
        ReferenceList Ry = separatedPairFromPy._rightList;

        PairOfPoints closestPairFromLeft = this.solveRecursively(Qx, Qy);                                               //가장 작은게 찾아질 때 까지 재귀해서 들어간다.
        PairOfPoints closestPairFromRight = this.solveRecursively(Rx, Ry);                                              //양쪽 다 계속 재귀해서 찾아낸다

        if (closestPairFromLeft == null) {                                                                              //왼쪽에서 찾아진 게 없으면 오른쪽에서 찾아진게 제일 작은거
            closestPair = closestPairFromRight;
        } else if (closestPairFromRight == null) {                                                                      //오른쪽에서 찾아진게 없으면 왼쪽에서 찾아진게 가장 작은거
            closestPair = closestPairFromLeft;
        } else if (closestPairFromLeft.distance() < closestPairFromRight.distance()) {                                  //둘다 찾아졌다면 둘중 작은게 가장 작은거
            closestPair = closestPairFromLeft;
        } else {
            closestPair = closestPairFromRight;
        }

        long delta = closestPair.distance();                                                                            //거리변수 delta
        PairOfPoints closestPairFromDeltaBand = this.closestPairFromDeltaBand(Py, separationLine, delta);               //찾아진 가장 작은 거리를 이용해서 분리 선 근처에서 찾아진 가장 짧은 거리의 쌍

        if (closestPairFromDeltaBand != null) {                                                                         //델타밴드에서 찾아진게 없다면 양 쪽에서 찾아진게 가장 짧은거
            if (closestPairFromDeltaBand.distance() < closestPair.distance()) {
                closestPair = closestPairFromDeltaBand;
            }
        }
        return closestPair;
    }

    public PairOfPoints solveByHybrid(PointSet pointSetForSolve){
        if(pointSetForSolve == null || pointSetForSolve.size() <= 1){
            return null;
        }
        this.setPointSet(pointSetForSolve);
        ReferenceList Px = new ReferenceListOrderedByX(this.pointSet());
        ReferenceList Py = new ReferenceListOrderedByY(this.pointSet());

        PairOfPoints closestPair = this.solveByHybridRecursively(Px, Py);
        return closestPair;
    }

    private PairOfPoints solveByHybridRecursively(ReferenceList Px, ReferenceList Py){
        if(Px.size() < this.minRecursionSize()){
            return this.solveByComparingAllPairsForSmallSet(Px);
        }
        PairOfPoints closestPair;
        int separationLine = Px.elementAt(Px.size() / 2);                                                                   //이외에는 이미 구현했던 분할정복방식과 같다.
        SeparatedPair separatedPairFromPx = this.separateReferenceList(Px, separationLine);
        SeparatedPair separatedPairFromPy = this.separateReferenceList(Py, separationLine);
        ReferenceList Qx = separatedPairFromPx._leftList;
        ReferenceList Rx = separatedPairFromPx._rightList;
        ReferenceList Qy = separatedPairFromPy._leftList;
        ReferenceList Ry = separatedPairFromPy._rightList;

        PairOfPoints closestPairFromLeft = this.solveByHybridRecursively(Qx, Qy);
        PairOfPoints closestPairFromRight = this.solveByHybridRecursively(Rx, Ry);

        if (closestPairFromLeft == null) {
            closestPair = closestPairFromRight;
        } else if (closestPairFromRight == null) {
            closestPair = closestPairFromLeft;
        } else if (closestPairFromLeft.distance() < closestPairFromRight.distance()) {
            closestPair = closestPairFromLeft;
        } else {
            closestPair = closestPairFromRight;
        }

        long delta = closestPair.distance();                                                                            //거리변수 delta
        PairOfPoints closestPairFromDeltaBand = this.closestPairFromDeltaBand(Py, separationLine, delta);               //찾아진 가장 작은 거리를 이용해서 분리 선 근처에서 찾아진 가장 짧은 거리의 쌍

        if (closestPairFromDeltaBand != null) {                                                                         //델타밴드에서 찾아진게 없다면 양 쪽에서 찾아진게 가장 짧은거
            if (closestPairFromDeltaBand.distance() < closestPair.distance()) {
                closestPair = closestPairFromDeltaBand;
            }
        }
        return closestPair;
    }

    private PairOfPoints solveByComparingAllPairsForSmallSet(ReferenceList Px){
        if(Px.size() <= 1){
            return null;
        }
        Point closestI = this.pointSet().pointReferencedByIndex(Px, 0);
        Point closestJ = this.pointSet().pointReferencedByIndex(Px, 1);
        long minDistance = closestI.distanceTo(closestJ);

        for(int i = 0; i < Px.size() - 1; i++){
            Point pointI = this.pointSet().pointReferencedByIndex(Px, i);
            for(int j = i + 1; j < Px.size(); j++){
                Point pointJ = this.pointSet().pointReferencedByIndex(Px, j);
                long distanceBetweenIJ = pointI.distanceTo(pointJ);
                if(distanceBetweenIJ < minDistance){
                    minDistance = distanceBetweenIJ;
                    closestI = pointI;
                    closestJ = pointJ;
                }
            }
        }
        PairOfPoints closestPair = new PairOfPoints(closestI, closestJ);
        return closestPair;
    }

    private PairOfPoints closestPairDirectlyFromSmallPointSet(ReferenceList Px) {                                       //작은 리스트에서는 직접 비교로 가장 짧은 쌍을 찾는다.
        if (Px.size() <= 1) {                                                                                           //점 개수가 하나 이하면 불가.
            return null;
        } else {
            Point p0 = this.pointSet().pointReferencedByIndex(Px, 0);                                            //최소 두개는 있다.
            Point p1 = this.pointSet().pointReferencedByIndex(Px, 1);

            Point closestPairPointI = p0;
            Point closestPairPointJ = p1;
            long minDistance = p0.distanceTo(p1);                                                                       //일단 최소 두개있으니까 두개를 비교하고

            if (Px.size() == 3) {
                Point p2 = this.pointSet().pointReferencedByIndex(Px, 2);                                        //만약 하나가 더 있다면 그 하나도 같이 비교해준다.
                if (p0.distanceTo(p2) < minDistance) {                                                                  //구해놓은 0-1의 거리보다 새로구한 0-2거리가 짧다면 바꿔준다.
                    closestPairPointJ = p2;
                    minDistance = p0.distanceTo(p2);
                }
                if (p1.distanceTo(p2) < minDistance) {                                                                  //마찬가지로 1-2거리가 짧다면 바꿔준다.
                    closestPairPointI = p1;
                    closestPairPointJ = p2;
                }
            }
            return new PairOfPoints(closestPairPointI, closestPairPointJ);                                              //그렇게 찾아진 점 쌍이 가장 가까운 점 쌍이다.
        }
    }

    private SeparatedPair separateReferenceList(ReferenceList referenceList, int separationLine){                       //레퍼런스 리스트를 주어진 라인으로 나눈다.
        SeparatedPair separatedPair = new SeparatedPair();                                                              //분리될 왼쪽 오른쪽 부분을 위한 객체 separatedpair
        int sizeOfReferenceList = referenceList.size();

        separatedPair._leftList = new ReferenceList(sizeOfReferenceList);
        separatedPair._rightList = new ReferenceList(sizeOfReferenceList);

        int separationX = this.pointSet().elementAt(separationLine).x;                                                  //분리선 번째 위치의 x좌표

        for(int index = 0; index < sizeOfReferenceList; index++){
            Integer pointReference = referenceList.elementAt(index);
            Point pointReferencedByIndexI = this.pointSet().elementAt(pointReference);                                  //레퍼런스리스트의 값은 실제 리스트의 인덱스이다.

            if(pointReferencedByIndexI.x < separationX){                                                                //레퍼런스로 얻어낸 정렬된 순서의 실제 리스트의 index번째 점의 x좌표가 분리선보다 작으면
                separatedPair._leftList.add(pointReference);                                                            //왼쪽에 해당하므로 왼쪽 리스트에 추가한다.
            }
            else if(pointReferencedByIndexI.x > separationX){
                separatedPair._rightList.add(pointReference);                                                           //크다면 오른쪽에 해당하므로 오른쪽 리스트에 넣어준다.
            }
            else{
                if (pointReference < separationLine){
                    separatedPair._leftList.add(pointReference);                                                        //그렇지 않다면(값이 같은경우) 인덱스로 비교해서 분리선보다 작은 인덱스라면 왼쪽에 넣고 아니면 오른쪽에 넣어준다.
                }
                else{
                    separatedPair._rightList.add(pointReference);
                }
            }
        }
        return separatedPair;                                                                                           //위의 반복을 통해 분리선을 기준으로 좌측과 우측을 분리했다. 그 리스트를 담은 객체 separatedPair를 반환해준다.
    }

    private PairOfPoints closestPairFromDeltaBand(ReferenceList Py, Integer separationLine, long delta) {               //py, 분리선과 delta거리를 이용한다.
        PairOfPoints closestPair = null;
        ReferenceList Sy = this.deltaBand(Py, separationLine, delta);                                                   //분리선과 델타거리 py를 이용해 레퍼런스 리스트 sy를 만들어낸다. 얻어진 Sy는 최소한 분리선까지 거리가 찾아진 최단거리보단 짧은 점들
        int Sy_size = Sy.size();
        int hereIndex = 0;
        closestPair = this.closestPairFromHereToNeighbors(Sy, hereIndex);                                               //인접한 위치에서만 가장 짧은 위치를 찾는다. 멀리갈필요 없음.
        for (hereIndex = 1; hereIndex < Sy_size - 1; hereIndex++) {                                                     //0은 찾아놨다. 1부터 레퍼런스리스트 Sy를 전부 돌면서 가장 짧은걸 찾는다.
            PairOfPoints closestPairToNeighbors = this.closestPairFromHereToNeighbors(Sy, hereIndex);
            if (closestPairToNeighbors.distance() < closestPair.distance()) {                                           //찾아놧던거보다 찾아진게 짧다면 바꿔준다.
                closestPair = closestPairToNeighbors;
            }
        }
        return closestPair;                                                                                             //반복 다 돌고 최종적으로 남아있는 제일 짧은 쌍이 델타밴드에서 찾아진 제일 짧은 쌍이다.
    }

    private ReferenceList deltaBand(ReferenceList Py, Integer separationLine, long delta){                              //실제 y의 리스트와 분리선, 델타거리를 인자로받아서
        ReferenceList Sy = new ReferenceList(Py.size());                                                                //레퍼런스리스트를 py의 크기로 만들고
        int separationX = this.pointSet().elementAt(separationLine).x;                                                  //분리선의 x좌표를 얻어오고
        long sqrtOfDelta = (long) Math.sqrt(delta);                                                                     //델타거리의 루트를 구한다
        for(int index = 0; index < Py.size(); index++){                                                                 //y로 정렬된 레퍼런스 리스트 py를 순회하면서
            Integer pointReference = Py.elementAt(index);                                                               //py의 각 요소 즉 실제 리스트의 인덱스를 얻어오고
            Point point = this.pointSet().elementAt(pointReference);                                                    //그를 이용해 실제 리스트의 점을 얻어오고
            if(Math.abs(point.x - separationX) <= sqrtOfDelta){                                                         //실제점의 x좌표와 분리선의 x좌표간의 거리가 실제 최소거리보다 작은지 확인한다.
                Sy.add(pointReference);                                                                                 //작다면 레퍼런스리스트 Sy에 추가해준다.
            }
        }
        return Sy;                                                                                                      //반복이 끝나고 얻어진 레퍼런스리스트를 반환해준다.
    }

    private PairOfPoints closestPairFromHereToNeighbors(ReferenceList Sy, int hereIndex){                               //정렬된 레퍼런스리스트 Sy와 index를 받는다.
        Point herePoint = this.pointSet().pointReferencedByIndex(Sy, hereIndex);                                        //정렬된 레퍼런스리스트 Sy의 index번째 점을 가져온다.
        Point closestNeighborPoint = this.pointSet().pointReferencedByIndex(Sy, hereIndex+1);                    //가장 가까운게 다음 인덱스에 있는 점으로 설정
        long minDistance = herePoint.distanceTo(closestNeighborPoint);                                                  //가장가까운점으로의 거리를 구한다.
        int lastNeighborIndex = Math.min((hereIndex + MAX_NUMBER_OF_NEIGHBORS), (Sy.size() - 1));                       //비교해야할 인덱스는 최대 7인덱스 후이거나 레퍼런스 리스트의 사이즈가 더 작다면 사이즈보다 1뺀거만큼이다.
        for(int neighborIndex = (hereIndex + 2); neighborIndex <= lastNeighborIndex; neighborIndex++){                  //+1은 이미 제일작은거인척 구해놨다. +2부터 하나씩 구해본다 .
            Point neighborPoint = this.pointSet().pointReferencedByIndex(Sy, neighborIndex);                            //거리를 내고
            long distanceFromHereToNeighbor = herePoint.distanceTo(neighborPoint);                                      //이미 찾아놓은 거리랑 비교 (최초엔 +1을 구해놨다) 해서 더 작은걸 고른다.
            if(distanceFromHereToNeighbor < minDistance){
                minDistance = distanceFromHereToNeighbor;
                closestNeighborPoint = neighborPoint;                                                                   //갱신되면 가장 짧은 근처 점도 바뀐다.
            }
        }
        return new PairOfPoints(herePoint, closestNeighborPoint);                                                       //반복이 끝났을 때 최종적으로 남아있는 점이 제일 작은점임. 쌍으로 만들어서 반환
    }

    private Integer separationLine(ReferenceList Px) {
        return Px.elementAt(Px.size() / 2);                                                                      //x로 정렬된 리스트의 중간 인덱스에 위치한 요소를 분리선으로 정한다.
    }

    private class SeparatedPair {                                                                                       //내부클래스 분리쌍
        private ReferenceList _leftList;
        private ReferenceList _rightList;

        public SeparatedPair() {

        }
    }
}
