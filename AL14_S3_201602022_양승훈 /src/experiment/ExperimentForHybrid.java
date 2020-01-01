package experiment;

import model.FindClosestPair;
import model.PointSet;

public class ExperimentForHybrid extends Experiment{
    public ExperimentForHybrid(FindClosestPair givenFindClosestPair, ParameterSet givenParameterSet){
        super(givenFindClosestPair, givenParameterSet);
    }

    public long durationOfSingleSolve(PointSet pointSet){
        Timer.start();
        this.findClosestPair().solveByHybrid(pointSet);
        Timer.stop();
        return Timer.duration();
    }
}
