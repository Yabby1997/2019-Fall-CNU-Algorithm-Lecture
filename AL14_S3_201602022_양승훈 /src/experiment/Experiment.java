package experiment;

import model.FindClosestPair;
import model.PointSet;

public abstract class Experiment {
    private ParameterSet _parameterSet;
    private FindClosestPair _findClosestPair;

    protected Experiment(FindClosestPair givenFindClosestPair, ParameterSet givenParameterSet){
        this.setFindClosestPair(givenFindClosestPair);
        this.setParameterSet(givenParameterSet);
    }

    private ParameterSet parameterSet(){
        return this._parameterSet;
    }

    private void setParameterSet(ParameterSet newParameterSet){
        this._parameterSet = newParameterSet;
    }

    protected FindClosestPair findClosestPair(){
        return this._findClosestPair;
    }

    private void setFindClosestPair(FindClosestPair newFindClosestPair){
        this._findClosestPair = newFindClosestPair;
    }

    public abstract long durationOfSingleSolve(PointSet pointSet);

    public long averageDurationOfSingleSolves(PointSet pointSet){
        long sum = 0;
        for(int count = 0; count < this.parameterSet().numberOfRepetitionsOfSameExecution(); count++){
            sum += this.durationOfSingleSolve(pointSet);
        }
        long average = sum / this.parameterSet().numberOfRepetitionsOfSameExecution();
        return average;
    }

    public long minDurationAmongSingleSolves(PointSet pointSet){
        long minDuration = this.durationOfSingleSolve(pointSet);
        for(int count = 1; count < this.parameterSet().numberOfRepetitionsOfSameExecution(); count++){
            long duration = this.durationOfSingleSolve(pointSet);
            if(duration < minDuration)
                minDuration = duration;
        }
        return minDuration;
    }
}
