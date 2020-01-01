package experiment;

import model.FindClosestPair;
import model.PointSet;

public class ExperimentForDivideAndConquer extends Experiment{
    public ExperimentForDivideAndConquer(FindClosestPair givenFindClosestPair, ParameterSet givenParameterSet){
        super(givenFindClosestPair, givenParameterSet);
    }

    @Override
    public long durationOfSingleSolve(PointSet pointSet){
        Timer.start();
        this.findClosestPair().solveByDivideAndConquer(pointSet);
        Timer.stop();
        return Timer.duration();
    }
}
