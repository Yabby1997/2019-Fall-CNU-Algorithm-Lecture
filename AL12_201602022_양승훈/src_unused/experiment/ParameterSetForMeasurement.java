package experiment;

public class ParameterSetForMeasurement extends ParameterSetForIteration {                                              //데이터의 크기가 작아 측정에 오차가 많이 발생한다면 여러번 반복하여 평균치를 이용한다.
    private static final int DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT = 1;

    private int _numberOfRepetitionOfSingleSort;                                                                        //그러기위해 반복회수를 설정해줄 수 있는 변수를 만든다.

    public ParameterSetForMeasurement(){
        super();
        this.setNumberOfRepetitionOfSingleSort(DEFAULT_NUMBER_OF_REPETITION_OF_SINGLE_SORT);
    }

    public ParameterSetForMeasurement(int givenStartingSize, int givenNumberOfIteration, int givenIncrementSize, int givenNumberOfRepetitionOfSingleSort){
        super(givenStartingSize, givenNumberOfIteration, givenIncrementSize);
        this.setNumberOfRepetitionOfSingleSort(givenNumberOfRepetitionOfSingleSort);
    }

    public int numberOfRepetitionOfSingleSort(){
        return this._numberOfRepetitionOfSingleSort;
    }

    public void setNumberOfRepetitionOfSingleSort(int newNumberOfRepetitionOfSingleSort){
        this._numberOfRepetitionOfSingleSort = newNumberOfRepetitionOfSingleSort;
    }
}
