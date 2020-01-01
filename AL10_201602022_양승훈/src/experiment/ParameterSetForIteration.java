package experiment;

public class ParameterSetForIteration {
    private static final int DEFAULT_NUMBER_OF_ITERATION = 10;
    private static final int DEFAULT_INCREMENT_SIZE = 1000;
    private static final int DEFAULT_SATRTING_SIZE = DEFAULT_INCREMENT_SIZE;

    private int _startingSize;
    private int _numberOfIteration;
    private int _incrementSize;

    //constructor
    public ParameterSetForIteration(){
        this.setStartingSize(DEFAULT_SATRTING_SIZE);
        this.setIncrementSize(DEFAULT_INCREMENT_SIZE);
        this.setNumberOfIteration(DEFAULT_NUMBER_OF_ITERATION);
    }

    public ParameterSetForIteration(int givenStartingSize, int givenNumberOfIteration, int givenIncrementSize){
        this.setStartingSize(givenStartingSize);
        this.setNumberOfIteration(givenNumberOfIteration);
        this.setIncrementSize(givenIncrementSize);
    }

    //getter/setter
    public int startingSize(){
        return this._startingSize;
    }

    public int numberOfIteration(){
        return this._numberOfIteration;
    }

    public int incrementSize(){
        return this._incrementSize;
    }

    private void setStartingSize(int newStartingSize){
        this._startingSize = newStartingSize;
    }

    private void setNumberOfIteration(int newNumberOfIteration){
        this._numberOfIteration = newNumberOfIteration;
    }

    private void setIncrementSize(int newIncrementSize){
        this._incrementSize = newIncrementSize;
    }

    public int maxDataSize(){                                                                                           //최대 데이터의 사이즈를 계산해 반환
        return (this.startingSize() + (this.incrementSize() * (this.numberOfIteration() - 1)));
    }
}
