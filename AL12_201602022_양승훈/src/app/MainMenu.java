package app;

public enum MainMenu {
    ERROR,
    COMPRESS,
    DECOMPRESS,
    VALIDATE,
    END;

    public static MainMenu value(int menuNumber){
        if(menuNumber <= 0 || menuNumber > END.ordinal()){                                                              //입력된 정수가 0보다 작거나 END의 수보다 크면 ERROR를 반환. ordinal함수가 enum의 순서 반환
            return MainMenu.ERROR;
        }
        else{
            return MainMenu.values()[menuNumber];                                                                       //입력된 정수번째것을 반환
        }
    }
}
