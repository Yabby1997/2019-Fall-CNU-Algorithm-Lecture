package experiment;

import model.FindClosestPair;
import model.PointSet;

public class ExperimentForComparingAllPairs extends Experiment{
    public ExperimentForComparingAllPairs(FindClosestPair givenFindClosestPair, ParameterSet givenParameterSet){
        super(givenFindClosestPair, givenParameterSet);
    }

    @Override
    public long durationOfSingleSolve(PointSet pointSet){
        Timer.start();
        this.findClosestPair().solveByComparingAllPairs(pointSet);
        Timer.stop();
        return Timer.duration();
    }
}
