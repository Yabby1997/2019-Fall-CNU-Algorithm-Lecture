package experiment;

import java.util.Random;

public final class DataGenerator {
    private DataGenerator(){}                                                                                           //스태틱클래스로, 객체 생성하지 않을 것.

    public static Integer[] ascendingList(int aSize){
        if(aSize > 0){                                                                                                  //유효한 사이즈가 입력된다면
            Integer[] list = new Integer[aSize];
            for(int i = 0; i < aSize; i ++){
                list[i] = i;                                                                                            //사이즈 크기의 증가되는 리스틀 만들어 반환
            }
            return list;
        }
        return null;                                                                                                    //그렇지 않다면 null을 반환한다.
    }

    public static Integer[] descendingList(int aSize){
        if(aSize > 0){                                                                                                  //유효한 사이즈가 입력된다면
            Integer[] list = new Integer[aSize];
            for(int i = 0; i < aSize; i ++){
                list[i] = aSize - i - 1;                                                                                //사이즈 크기의 증가되는 리스틀 만들어 반환
            }
            return list;
        }
        return null;                                                                                                    //그렇지 않다면 null을 반환한다.
    }

    public static Integer[] randomList(int aSize){
        if(aSize > 0){
            Integer[] list = new Integer[aSize];
            Random random = new Random();
            for(int i = 0; i < aSize; i++){
                list[i] = random.nextInt(aSize);                                                                        //0부터 aSize -1 범위의 랜덤한 정수를 리스트 각 요소에 넣어줌.
            }
            return list;
        }
        return null;
    }

}
