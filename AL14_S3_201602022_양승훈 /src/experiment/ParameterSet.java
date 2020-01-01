package experiment;
public class ParameterSet {
    private static final int DEFAULT_NUMBER_OF_STEPS = 10;
    private static final int DEFAULT_STARTING_SIZE = 1000;
    private static final int DEFAULT_INCREMENT_SIZE = DEFAULT_NUMBER_OF_STEPS;
    private static final int DEFAULT_NUMBER_OF_REPETITIONS_OF_SAME_EXECUTION = 10;
    private static final int DEFAULT_MIN_RECURSION_SIZE = 150;

    private int _numberOfSteps;
    private int _startingSize;
    private int _incrementSize;
    private int _numberOfRepetitionsOfSameExecution;
    private int _minRecursionSize;

    //constructor
    public ParameterSet(){
        this(ParameterSet.DEFAULT_NUMBER_OF_STEPS, ParameterSet.DEFAULT_STARTING_SIZE, ParameterSet.DEFAULT_INCREMENT_SIZE, ParameterSet.DEFAULT_NUMBER_OF_REPETITIONS_OF_SAME_EXECUTION, ParameterSet.DEFAULT_MIN_RECURSION_SIZE);
    }

    public ParameterSet(int givenNumberOfSteps, int givenStartingSize, int givenIncrementSize, int givenNumberOfRepetitionsOfSameExecution, int givenMinRecursionSize){
        this.setNumberOfSteps(givenNumberOfSteps);
        this.setStartingSize(givenStartingSize);
        this.setIncrementSize(givenIncrementSize);
        this.setNumberOfRepetitionsOfSameExecution(givenNumberOfRepetitionsOfSameExecution);
        this.setMinRecursionSize(givenMinRecursionSize);
    }

    //getter/setter
    public int numberOfSteps(){
        return this._numberOfSteps;
    }

    public void setNumberOfSteps(int newNumberOfSteps){
        this._numberOfSteps = newNumberOfSteps;
    }

    public int startingSize(){
        return this._startingSize;
    }

    public void setStartingSize(int newStartingSize){
        this._startingSize = newStartingSize;
    }

    public int incrementSize(){
        return this._incrementSize;
    }

    public void setIncrementSize(int newIncrementSize){
        this._incrementSize = newIncrementSize;
    }

    public int numberOfRepetitionsOfSameExecution(){
        return this._numberOfRepetitionsOfSameExecution;
    }

    public void setNumberOfRepetitionsOfSameExecution(int newNumberOfRepetitionsOfSameExecution){
        this._numberOfRepetitionsOfSameExecution = newNumberOfRepetitionsOfSameExecution;
    }

    public int minRecursionSize(){
        return this._minRecursionSize;
    }

    public void setMinRecursionSize(int newMinRecursionSize){
        this._minRecursionSize = newMinRecursionSize;
    }
}