package experiment;

import app.AppView;
import sort.HeapSort;
import sort.InsertionSort;
import sort.QuickSortByPivotLeft;

public class ExperimentManagerForThreeSorts extends ExperimentManager{
    private static final boolean DEBUG_MODE = false;
    private static void showDebugMessage(String aMessage){
        if(ExperimentManagerForThreeSorts.DEBUG_MODE){
            AppView.output(aMessage);
        }
    }

    private static final InsertionSort<Integer> InsertionSort = new InsertionSort<Integer>(true);
    private static final HeapSort<Integer> HeapSort = new HeapSort<Integer>(true);
    private static final QuickSortByPivotLeft<Integer> QuickSort = new QuickSortByPivotLeft<Integer>(true);

    private long[] _measurementForInsertionSort;
    private long[] _measurementForQuickSort;
    private long[] _measurementForHeapSort;
    private long[] _estimationForInsertionSort;
    private long[] _estimationForQuickSort;
    private long[] _estimationForHeapSort;

    //getter/setter
    public long[] measurementForInsertionSort(){
        return this._measurementForInsertionSort;
    }

    public long[] measurementForQuickSort(){
        return this._measurementForQuickSort;
    }

    public long[] measurementForHeapSort(){
        return this._measurementForHeapSort;
    }

    public long[] estimationForInsertionSort(){
        return this._estimationForInsertionSort;
    }

    public long[] estimationForQuickSort(){
        return this._estimationForQuickSort;
    }

    public long[] estimationForHeapSort(){
        return this._estimationForHeapSort;
    }

    private void setMeasurementForInsertionSort(long[] newMeasurementForInsertionSort){
        this._measurementForInsertionSort = newMeasurementForInsertionSort;
    }

    private void setMeasurementForQuickSort(long[] newMeasurementForQuickSort){
        this._measurementForQuickSort = newMeasurementForQuickSort;
    }

    private void setMeasurementForHeapSort(long[] newMeasurementForHeapSort){
        this._measurementForHeapSort = newMeasurementForHeapSort;
    }

    private void setEstimationForInsertionSort(long[] newEstimationForInsertionSort){
        this._estimationForInsertionSort = newEstimationForInsertionSort;
    }

    private void setEstimationForQuickSort(long[] newEstimationForQuickSort){
        this._estimationForQuickSort = newEstimationForQuickSort;
    }

    private void setEstimationForHeapSort(long[] newEstimationForHeapSort){
        this._estimationForHeapSort = newEstimationForHeapSort;
    }

    //정렬방식대로 정렬된 데이터를 각 방식의 결과를 저장할 배열에 저장해줌
   protected void performMeasuring(ListOrder anOrder){
        Integer[] experimentList = this.dataSet().listWithOrder(anOrder);
        this.setMeasurementForInsertionSort(this.experiment().durationOfSort(ExperimentManagerForThreeSorts.InsertionSort, experimentList));
        ExperimentManagerForThreeSorts.showDebugMessage("[Debug] end of insertion sort\n");
        this.setMeasurementForQuickSort(this.experiment().durationOfSort(ExperimentManagerForThreeSorts.QuickSort, experimentList));
        ExperimentManagerForThreeSorts.showDebugMessage("[Debug] end of quick sort\n");
        this.setMeasurementForHeapSort(this.experiment().durationOfSort(ExperimentManagerForThreeSorts.HeapSort, experimentList));
        ExperimentManagerForThreeSorts.showDebugMessage("[Debug] end of heap sort\n");
    }

    //각 데이터에 따른 예상 소요시간을 출력하는 함수들
    private void estimateForRandomList(){
        this.setEstimationForInsertionSort(Estimation.estimateByQuadratic(this.measurementForInsertionSort(), this.parameterSetForMeasurement()));      //랜덤리스트를 삽입정렬로 : O(n^2)
        this.setEstimationForQuickSort(Estimation.estimatedByNLogN(this.measurementForQuickSort(), this.parameterSetForMeasurement()));                 //랜덤리스트를 퀵정렬로   : O(nLogn)
        this.setEstimationForHeapSort(Estimation.estimatedByNLogN(this.measurementForHeapSort(), this.parameterSetForMeasurement()));                   //랜덤리스트를 힙정렬로   : O(nLogn)
    }

    private void estimateForAscendingList(){
        this.setEstimationForInsertionSort(Estimation.estimateByLinear(this.measurementForInsertionSort(), this.parameterSetForMeasurement()));         //오름차순리스트를 삽입정렬로 : O(n)
        this.setEstimationForQuickSort(Estimation.estimateByQuadratic(this.measurementForQuickSort(), this.parameterSetForMeasurement()));              //오름차순리스트를 퀵정렬로   : O(n^2)
        this.setEstimationForHeapSort(Estimation.estimatedByNLogN(this.measurementForHeapSort(), this.parameterSetForMeasurement()));                   //오름차순리스트를 힙정렬로   : O(nLogn)
    }

    private void estimateForDescendingList(){
        this.setEstimationForInsertionSort(Estimation.estimateByQuadratic(this.measurementForInsertionSort(), this.parameterSetForMeasurement()));      //내림차순리스트를 삽입정렬로 : O(n^2)
        this.setEstimationForQuickSort(Estimation.estimateByQuadratic(this.measurementForQuickSort(), this.parameterSetForMeasurement()));              //내림차순리스트를 퀵정렬로   : O(n^2)
        this.setEstimationForHeapSort(Estimation.estimatedByNLogN(this.measurementForHeapSort(), this.parameterSetForMeasurement()));                   //내림차순리스트를 힙정렬로   : O(nLogn)
    }

    //private으로 구현한 측정함수와 예측함수를 여기서 실행시킬수 있도록 한다.
    public void performExperiment(ListOrder anOrder){
        this.performMeasuring(anOrder);                                                                                 //인자로 주어진 정렬방식에 따라 성능을 측정한다.
        if(anOrder.equals(ListOrder.Random)){                                                                           //인자로 주어진 정렬방식에 알맞은 성능 예측 출력을 한다.
            this.estimateForRandomList();
        }
        else if(anOrder.equals(ListOrder.Ascending)){
            this.estimateForAscendingList();
        }
        else{
            this.estimateForDescendingList();
        }
    }

    //측정 결과, 예측 결과의 특정 인덱스 값을 알아올 수 있도록 하는 공개함수를 정의한다.
    public long measurementForInsertionSortAt(int anIndex){
        return this.measurementForInsertionSort()[anIndex];
    }

    public long measurementForQuickSortAt(int anIndex){
        return this.measurementForQuickSort()[anIndex];
    }

    public long measurementForHeapSortAt(int anIndex){
        return this.measurementForHeapSort()[anIndex];
    }
    public long estimationForInsertionSortAt(int anIndex){
        return this.estimationForInsertionSort()[anIndex];
    }

    public long estimationForQuickSortAt(int anIndex){
        return this.estimationForQuickSort()[anIndex];
    }

    public long estimationForHeapSortAt(int anIndex){
        return this.estimationForHeapSort()[anIndex];
    }
}
