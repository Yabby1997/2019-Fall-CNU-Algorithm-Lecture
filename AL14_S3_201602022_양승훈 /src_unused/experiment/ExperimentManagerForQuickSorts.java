package experiment;

import view.AppView;
import sort.QuickSort;
import sort.QuickSortByPivotLeft;
import sort.QuickSortByPivotMid;
import sort.QuickSortByPivotMedian;
import sort.QuickSortByPivotRandom;
import sort.QuickSortWithInsertionSort;

public class ExperimentManagerForQuickSorts extends ExperimentManager{
    private static final boolean DEBUG_MODE = false;

    private static void showDebugMessage(String aMessage) {
        if (DEBUG_MODE) {
            AppView.output(aMessage);
        }
    }

    private static final QuickSortByPivotLeft<Integer> QuickSortByPivotLeft = new QuickSortByPivotLeft<Integer>(true);
    private static final QuickSortByPivotMid<Integer> QuickSortByPivotMid = new QuickSortByPivotMid<Integer>(true);
    private static final QuickSortByPivotMedian<Integer> QuickSortByPivotMedian = new QuickSortByPivotMedian<Integer>(true);
    private static final QuickSortByPivotRandom<Integer> QuickSortByPivotRandom = new QuickSortByPivotRandom<Integer>(true);
    private static final QuickSortWithInsertionSort<Integer> QuickSortWithInsertionSort = new QuickSortWithInsertionSort<Integer>(true);

    private long[] _measurementForQuickSortByPivotLeft;
    private long[] _measurementForQuickSortByPivotMid;
    private long[] _measurementForQuickSortByPivotMedian;
    private long[] _measurementForQuickSortByPivotRandom;
    private long[] _measurementForQuickSortWithInsertionSort;

    //getter/setter
    private long[] measurementForQuickSortByPivotLeft(){
        return this._measurementForQuickSortByPivotLeft;
    }

    private long[] measurementForQuickSortByPivotMid(){
        return this._measurementForQuickSortByPivotMid;
    }

    private long[] measurementForQuickSortByPivotMedian(){
        return this._measurementForQuickSortByPivotMedian;
    }

    private long[] measurementForQuickSortByPivotRandom(){
        return this._measurementForQuickSortByPivotRandom;
    }

    private long[] measurementForQuickSortWithInsertionSort(){
        return this._measurementForQuickSortWithInsertionSort;
    }

    private void setMeasurementForQuickSortByPivotLeft(long[] newMeasurementForQuickSortByPivotLeft){
        this._measurementForQuickSortByPivotLeft = newMeasurementForQuickSortByPivotLeft;
    }

    private void setMeasurementForQuickSortByPivotMid(long[] newMeasurementForQuickSortByPivotMid){
        this._measurementForQuickSortByPivotMid = newMeasurementForQuickSortByPivotMid;
    }

    private void setMeasurementForQuickSortByPivotMedian(long[] newMeasurementForQuickSortByPivotMedian){
        this._measurementForQuickSortByPivotMedian = newMeasurementForQuickSortByPivotMedian;
    }

    private void setMeasurementForQuickSortByPivotRandom(long[] newMeasurementForQuickSortByPivotRandom){
        this._measurementForQuickSortByPivotRandom = newMeasurementForQuickSortByPivotRandom;
    }

    private void setMeasurementForQuickSortWithInsertionSort(long[] newMeasurementForQuickSortWithInsertionSort){
        this._measurementForQuickSortWithInsertionSort = newMeasurementForQuickSortWithInsertionSort;
    }

    @Override
    protected void performMeasuring(ListOrder anOrder){                                                                 //5가지 pivot선정방식에 따른 quickSort방식으로 모두 성능을 측정하도록 함.
        Integer[] list = this.dataSet().listWithOrder(anOrder);

        this.setMeasurementForQuickSortByPivotLeft(this.experiment().durationOfSort(ExperimentManagerForQuickSorts.QuickSortByPivotLeft, list));
        ExperimentManagerForQuickSorts.showDebugMessage("[Debug] end of QuickSortByPivotLeft\n");
        this.setMeasurementForQuickSortByPivotMid(this.experiment().durationOfSort(ExperimentManagerForQuickSorts.QuickSortByPivotMid, list));
        ExperimentManagerForQuickSorts.showDebugMessage("[Debug] end of QuickSortByPivotMid\n");
        this.setMeasurementForQuickSortByPivotMedian(this.experiment().durationOfSort(ExperimentManagerForQuickSorts.QuickSortByPivotMedian, list));
        ExperimentManagerForQuickSorts.showDebugMessage("[Debug] end of QuickSortByPivotMedian\n");
        this.setMeasurementForQuickSortByPivotRandom(this.experiment().durationOfSort(ExperimentManagerForQuickSorts.QuickSortByPivotRandom, list));
        ExperimentManagerForQuickSorts.showDebugMessage("[Debug] end of QuickSortByPivotRandom\n");
        this.setMeasurementForQuickSortWithInsertionSort(this.experiment().durationOfSort(ExperimentManagerForQuickSorts.QuickSortWithInsertionSort, list));
        ExperimentManagerForQuickSorts.showDebugMessage("[Debug] end of QuickSortWithInsertionSort\n");
    }

    //각 정렬 결과의 특정한 index의 요소를 반환하도록하는 public 함수
    public long measurementForQuickSortByPivotLeftAt(int anIndex){
        return this.measurementForQuickSortByPivotLeft()[anIndex];
    }

    public long measurementForQuickSortByPivotMidAt(int anIndex){
        return this.measurementForQuickSortByPivotMid()[anIndex];
    }

    public long measurementForQuickSortByPivotMedianAt(int anIndex){
        return this.measurementForQuickSortByPivotMedian()[anIndex];
    }

    public long measurementForQuickSortByPivotRandomAt(int anIndex){
        return this.measurementForQuickSortByPivotRandom()[anIndex];
    }

    public long measurementForQuickSortWithInsertionSortAt(int anIndex){
        return this.measurementForQuickSortWithInsertionSort()[anIndex];
    }

    public void performExperiment(ListOrder anOrder){
        this.performMeasuring(anOrder);
    }
}
