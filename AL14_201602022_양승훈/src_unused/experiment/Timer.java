package experiment;

public final class Timer {                                                                                              //상속을 막아준다.
    private static long startTime;                                                                                      //시작시간과 끝 시간을 기록할 변수
    private static long stopTime;

    private Timer(){}                                                                                                   //객체를 생성할 수 없는 static class이다.

    public static void start(){
        Timer.startTime = System.nanoTime();
    }

    public static void stop(){
        Timer.stopTime = System.nanoTime();
    }

    public static long duration(){                                                                                      //끝시간과 시작시간을 이용해 결린 시간을 계산하고 마이크로 단위로 변환해 반환
        return (Timer.stopTime - Timer.startTime) / 1000;
    }
}
