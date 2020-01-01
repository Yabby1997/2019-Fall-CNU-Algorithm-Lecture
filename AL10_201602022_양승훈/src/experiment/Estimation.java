package experiment;

public final class Estimation {                                                                                         //static class
    private Estimation(){}

    public static long[] estimateByLinear(long[] measuredTimes, ParameterSetForMeasurement aParameterSet){
        int length = aParameterSet.numberOfIteration();                                                                 //parameterSetForMeasurement를 통해 전달받은 iteration만큼
        long[] estimatedTimes = new long[length];                                                                       //예측되는 시간을 저장하는 배열
        double estimatedCoefficient = (double)measuredTimes[length - 1] / (double)length;                               //선형적으로 증가하는O(n) 경우 계수는 데이터 사이는 측정된 시간을 데이터 사이즈만큼으로 나눈게 계수가됨.
        for(int i = 1; i <= length; i++){                                                                               //최대로 오래걸린 시간을 그 데이터 사이즈만큼으로 나눈것을 coefficient로 삼는다.
            estimatedTimes[i - 1] = (long)(estimatedCoefficient * (double)(i));                                         //그리고 그를 통해 예측값의 배열을 만들어 반환해준다.
        }
        return estimatedTimes;
    }

    public static long[] estimateByQuadratic(long[] measuredTimes, ParameterSetForMeasurement aParameterSet){
        int length = aParameterSet.numberOfIteration();
        long[] estimatedTimes = new long[length];
        double estimatedCoefficient = (double)measuredTimes[length - 1] / (double)(length * length);                    //O(n^2)의 경우도 마찬가지로 최대로 오래걸린시간과 그 데이터 사이즈로 coefficient를 만듬.
        for(int i = 1; i <= length; i++){
            estimatedTimes[i - 1] = (long)(estimatedCoefficient * (double)(i * i));                                     //그 coefficient로 예측값의 배열을 만들어 반환해준다.
        }
        return estimatedTimes;
    }

    public static long[] estimatedByNLogN(long[] measuredTimes, ParameterSetForMeasurement aParameterSet){
        int length = aParameterSet.numberOfIteration();
        int incrementSize = aParameterSet.incrementSize();
        int N = aParameterSet.maxDataSize();
        long[] estimatedTimes = new long[length];
        double estimatedCoefficient = (double)measuredTimes[length - 1] / ((double)(N) * Math.log((double)(N)));        //O(nLogn)의 경우도 coefficient를 통해 예측값 배열 만들어 반환한다.
        for(int i = 1; i <= length; i++){
            estimatedTimes[i - 1] = (long)(estimatedCoefficient * (double)(i * incrementSize) * (Math.log((double)(i * incrementSize))));
        }
        return estimatedTimes;
    }
}
