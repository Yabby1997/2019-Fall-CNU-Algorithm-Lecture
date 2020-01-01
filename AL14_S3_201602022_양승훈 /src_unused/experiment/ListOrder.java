package experiment;

public enum ListOrder {                                                                                                 //enum의 원소 3가지를 선언해준다.
    Random,
    Ascending,
    Descending;

    public String orderName(){                                                                                          //enum의 원소도 객체이기때문에 멤버함수를 가질 수 있다.
        if(this.equals(ListOrder.Random)){                                                                              //정렬 방식을 한글로 리턴해주는 함수를 만든다.
            return "무작위";
        }
        else if(this.equals(ListOrder.Ascending)){
            return "오름차순";
        }
        else{
            return "내림차순";
        }
    }
}
