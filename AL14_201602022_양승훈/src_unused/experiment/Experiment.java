package experiment;

import view.AppView;
import sort.Sort;

public class Experiment {
    private static final boolean DEBUG_MODE = false;                                                                     //디버그 모드일 때 디버그 메시지를 출력하기 위한 상수와 변수 선언.
    private static void showDebugMessage(String aMessage){
        if(DEBUG_MODE){
            AppView.output(aMessage);
        }
    }

    private ParameterSetForMeasurement _parameterSetForMeasurement;

    //constructor
    public Experiment(ParameterSetForMeasurement givenParameterSet){
        this.setParameterSetForMeasurement(givenParameterSet);
    }

    //getter/setter
    public ParameterSetForMeasurement parameterSetForMeasurement(){
        return this._parameterSetForMeasurement;
    }

    private void setParameterSetForMeasurement(ParameterSetForMeasurement newParameterSetForMeasurement){
        this._parameterSetForMeasurement = newParameterSetForMeasurement;
    }

    private static Integer[] copyListOfGivenSize(Integer[] aList, int givenSize){                                       //여러 방식으로 성능을 측정해야하므로 동일한 리스트를 복제해서 사용해야한다.
        if(givenSize <= aList.length){                                                                                  //복사할 사이즈가 인자로 주어진 리스트보다 크면 복사할 수 없다.
            Integer[] copiedList = new Integer[givenSize];                                                              //복사받을 리스트를 주어진 사이즈 크기로 만들어주고,
            for(int i = 0; i < givenSize; i++){                                                                         //그 사이즈만큼 반복하면서 리스트를 복사,
                copiedList[i] = aList[i];
            }
            return copiedList;                                                                                          //복제된 리스트를 반환해준다.
        }
        return null;                                                                                                    //만약 복사하려는 사이즈가 주어진 리스트보다 큰 경우 복제가 안되므로 null반환.
    }

    public static long durationOfSingleSort(Sort<Integer> aSort, Integer[] aList){
        Timer.start();                                                                                                  //정렬 전에 타이머 시작, 정렬 후 타이머 종료
        {
            aSort.sort(aList);
        }
        Timer.stop();
        return Timer.duration();                                                                                        //측정된 시간을 반환해준다.
    }

    public long[] durationOfSort(Sort<Integer> sort, Integer[] list){                                                   //매개변수에 따라 정렬하는 시간을 데이터 크기별로 측정
        long[] durations = new long[this.parameterSetForMeasurement().numberOfIteration()];                             //각 반복마다 시간이얼마나 걸렸는지를 저장할 long 배열
        for(int i = 0, size = this.parameterSetForMeasurement().startingSize(); i < this.parameterSetForMeasurement().numberOfIteration(); i++, size += this.parameterSetForMeasurement().incrementSize()){
            long sumOfDurations = 0;                                                                                    //parameterSetForMeasurement를 통해 주어진 반복수 만큼, 측정할 데이터의 사이즈를 늘려가며 반복한다.
            for(int repeated = 0; repeated < this.parameterSetForMeasurement().numberOfRepetitionOfSingleSort(); repeated++){       //단일 소트에 대해서 여러번 반복해서 나온 결과의 평균값을 구하기 위한 for loop
                Integer[] currentList = Experiment.copyListOfGivenSize(list, size);                                     //매 반복마다 원본 리스트를 카피받아 사용한다.
                sumOfDurations += Experiment.durationOfSingleSort(sort, currentList);                                    //카피받은 리스트를 소트하는데 걸린시간들의 총 합을 구한다.
            }
            durations[i] = sumOfDurations / this.parameterSetForMeasurement().numberOfRepetitionOfSingleSort();         //구해진 시간들을 반복수로 나누어 평균치를 구한다.
            Experiment.showDebugMessage("[Debug.Experiment] " + sort.toString() + " (" + i + ") \n");         //각 sorting 결과를 디버그 메시지를 통해 출력한다.
        }
        return durations;                                                                                               //최종적으로 반환하는 duration 리스트는 각 사이즈별로 여러번 측정한 결과의 평균들이다.
    }
}
