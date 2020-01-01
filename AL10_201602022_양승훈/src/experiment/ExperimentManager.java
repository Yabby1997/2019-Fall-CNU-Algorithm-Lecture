package experiment;

import app.AppView;

public abstract class ExperimentManager {
    private static final boolean DEBUG_MODE = false;
    private static void showDebugMessage(String aString){
        if(ExperimentManager.DEBUG_MODE){
            AppView.output(aString);
        }
    }

    protected static final int DEFAULT_NUMBER_OF_ITERATION = 10;
    protected static final int DEFAULT_INCREMENT_SIZE = 1000;
    protected static final int DEFAULT_STARTING_SIZE = DEFAULT_INCREMENT_SIZE;
    protected static final int DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT = 1;

    private ExperimentDataSet _dataSet;
    private Experiment _experiment;
    private ParameterSetForMeasurement _parameterSetForMeasurement;

    //constructor
    public ExperimentManager(){
        this.setParameterSetWithDefaults();
        showDebugMessage("super - ExperimentManager");
    }

    //getter/setter
    public ExperimentDataSet dataSet(){
        return this._dataSet;
    }

    public Experiment experiment(){
        return this._experiment;
    }

    public ParameterSetForMeasurement parameterSetForMeasurement(){
        return this._parameterSetForMeasurement;
    }

    private void setDataSet(ExperimentDataSet newDataSet){
        this._dataSet = newDataSet;
    }

    private void setExperiment(Experiment newExperiment){
        this._experiment = newExperiment;
    }

    private void setParameterSetForMeasurement(ParameterSetForMeasurement newParameterSetForMeasurement){
        this._parameterSetForMeasurement = newParameterSetForMeasurement;
    }

    public void prepareExperiment(ParameterSetForMeasurement aParameterSet){                                            //인자로 받은 인자집합에 문제가 없다면 그를 인자로 설정, 측정을 진행한다.
        if(aParameterSet != null){
            this.setParameterSetForMeasurement(aParameterSet);                                                          //인자로 입력받은 파라미터 셋이 null이 아니라면 그 파라미터셋으로 설정
        }
        this.setExperiment(new Experiment(this.parameterSetForMeasurement()));                                          //매개변수 값들을 통해 experiment를 만든다.
        this.preparedDataSet();                                                                                         //실험데이터 준비
        this.performMeasuring(ListOrder.Random);                                                                        //자바 특성상 최초 성능 측정은 부정확하기때문에 측정을 한번 돌려준다.
    }

    protected void preparedDataSet(){
        this.setDataSet(new ExperimentDataSet(this.parameterSetForMeasurement().maxDataSize()));                        //데이터 셋을 만든다.
    }

    protected void setParameterSetWithDefaults() {                                                                       //기본 상수로 매개변수 객체를 만든다.
        this.setParameterSetForMeasurement(new ParameterSetForMeasurement(DEFAULT_STARTING_SIZE, DEFAULT_NUMBER_OF_ITERATION, DEFAULT_INCREMENT_SIZE, DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT));
    }

    protected abstract void performMeasuring(ListOrder anOrder);

    protected abstract void performExperiment(ListOrder anOrder);
}
