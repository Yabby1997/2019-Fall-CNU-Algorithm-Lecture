package experiment;

import app.AppView;
import sort.QuickSortByPivotRandom;
import sort.QuickSortWithInsertionSort;

public class ExperimentManagerForQuickSortWithInsertionSort extends ExperimentManager{                                  //삽입정렬 적용 구간의 크기를 바꿔가며 테스트해 quick과 insertion사이에서 어느시점에서 성능이 역전되는지를 확인
    private static final boolean DEBUG_MODE = false;

    private static void showDebugMessage(String aMessage){
        if(DEBUG_MODE){
            AppView.output(aMessage);
        }
    }

    protected static final int DEFAULT_NUMBER_OF_ITERATION = 10;
    protected static final int DEFAULT_INCREMENT_SIZE = 10000;
    protected static final int DEFAULT_STARTING_SIZE = DEFAULT_INCREMENT_SIZE;
    protected static final int DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT = 10;

    private static final int DEFAULT_INSERTION_SORT_STARTING_SIZE = 10;
    private static final int DEFAULT_INSERTION_SORT_INCREMENT_SIZE = 10;
    private static final int DEFAULT_INSERTION_SORT_NUMBER_OF_ITERATION = 9;

    public ExperimentManagerForQuickSortWithInsertionSort(){
        this.setParameterSetForMeasurement(new ParameterSetForMeasurement(DEFAULT_STARTING_SIZE, DEFAULT_NUMBER_OF_ITERATION, DEFAULT_INCREMENT_SIZE, DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT));
        this.setParameterSetForMaxSizeOfInsertionSort(new ParameterSetForIteration(DEFAULT_INSERTION_SORT_STARTING_SIZE, DEFAULT_INSERTION_SORT_NUMBER_OF_ITERATION, DEFAULT_INSERTION_SORT_INCREMENT_SIZE));
    }

    //비교에 사용할 정렬들, random pivot과 insertion sort
    private static final QuickSortByPivotRandom<Integer> QuickSortByPivotRandom = new QuickSortByPivotRandom<Integer>(true);
    private static final QuickSortWithInsertionSort<Integer> QuickSortWithInsertionSort = new QuickSortWithInsertionSort<Integer>(true);


    private ParameterSetForIteration _parameterSetForMaxSizeOfInsertionSort;
    private long[] _measurementForQuickSortByPivotRandom;
    private long[][] _measurementForQuickSortWithInsertionSort;                                                         //최대크기, 데이터크기에 따라 측정한 값을 저장하기위해 2차원배열로 선언

    //getter/setter
    public ParameterSetForIteration parameterSetForMaxSizeOfInsertionSort(){
        return this._parameterSetForMaxSizeOfInsertionSort;
    }

    private long[] measurementForQuickSortByPivotRandom(){
        return this._measurementForQuickSortByPivotRandom;
    }

    private long[][] measurementForQuickSortWithInsertionSort(){
        return this._measurementForQuickSortWithInsertionSort;
    }

    public void setParameterSetForMaxSizeOfInsertionSort(ParameterSetForIteration newParameterSet){
        this._parameterSetForMaxSizeOfInsertionSort = newParameterSet;
    }

    private void setMeasurementForQuickSortByPivotRandom(long[] newMeasurement){
        this._measurementForQuickSortByPivotRandom = newMeasurement;
    }

    private void setMeasurementForQuickSortWithInsertionSort(long[][] newMeasurements){
        this._measurementForQuickSortWithInsertionSort = newMeasurements;
    }

    private void setMeasurementForQuickSortWithInsertionSort(long[] newMeasurement, int measurementIndex){              //2차원 배열이므로
        this.measurementForQuickSortWithInsertionSort()[measurementIndex] = newMeasurement;
    }

    public long measurementForQuickSortByPivotRandomAt(int anIndex) {
        return this.measurementForQuickSortByPivotRandom()[anIndex];
    }

    public long measurementForQuickSortWithInsertionSortAt(int aMeasurementIndex, int anElementIndex) {                        //삽입정렬은
        return this.measurementForQuickSortWithInsertionSort()[aMeasurementIndex][anElementIndex];
    }


    @Override
    protected void performMeasuring(ListOrder anOrder){
        Integer[] list = this.dataSet().listWithOrder(anOrder);
        this.setMeasurementForQuickSortWithInsertionSort(new long[this.parameterSetForMaxSizeOfInsertionSort().numberOfIteration()][]);
        this.setMeasurementForQuickSortByPivotRandom(this.experiment().durationOfSort(ExperimentManagerForQuickSortWithInsertionSort.QuickSortByPivotRandom, list));                //랜덤 pivot으로 성능 측정
        ExperimentManagerForQuickSortWithInsertionSort.showDebugMessage("[Debug] end of QuickSortByPivotRandom\n");

        for(int iteration = 0; iteration < this.parameterSetForMaxSizeOfInsertionSort().numberOfIteration(); iteration++){                                                          //9반 반복
            int size = this.parameterSetForMaxSizeOfInsertionSort().startingSize() + this.parameterSetForMaxSizeOfInsertionSort().incrementSize() * iteration;                      //매 반복마다 사이즈는 반복*증가사이즈가 더해진 값
            QuickSortWithInsertionSort.setMaxSizeForInsertionSort(size);                                                                                                            //insertionSort구간의 크기를 정함
            this.setMeasurementForQuickSortWithInsertionSort(this.experiment().durationOfSort(ExperimentManagerForQuickSortWithInsertionSort.QuickSortWithInsertionSort, list), iteration);     //2차원 배열에 각 반복의 성능 기록
            ExperimentManagerForQuickSortWithInsertionSort.showDebugMessage("[Debug] end of QuickSortWithIteration " + size + "\n");
        }
    }

    @Override
    public void performExperiment(ListOrder anOrder) {
        performMeasuring(anOrder);
    }
}

