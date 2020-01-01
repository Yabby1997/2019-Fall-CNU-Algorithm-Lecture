package experiment;

import java.nio.channels.DatagramChannel;

public class ExperimentDataSet {
    private int _maxDataSize;

    private Integer[] _randomList;
    private Integer[] _ascendingList;
    private Integer[] _descendingList;

    //constructor
    public ExperimentDataSet(){
        this.setRandomList(null);
        this.setAscendingList(null);
        this.setDescendingList(null);
    }

    public ExperimentDataSet(int givenMaxDataSize){                                                                     //인자로 받은 데이터사이즈로 generate를 하고, 실패시 null로 초기화해 생성.
        if(!this.generate(givenMaxDataSize)){
            this.setRandomList(null);
            this.setAscendingList(null);
            this.setDescendingList(null);
        }
    }

    //getter/setter
    public int maxDataSize(){
        return this._maxDataSize;
    }

    public Integer[] randomList(){
        return this._randomList;
    }

    public Integer[] ascendingList(){
        return this._ascendingList;
    }

    public Integer[] descendingList(){
        return this._descendingList;
    }

    private void setMaxDataSize(int newMaxDataSize){
        this._maxDataSize = newMaxDataSize;
    }

    private void setRandomList(Integer[] newRandomList){
        this._randomList = newRandomList;
    }

    private void setAscendingList(Integer[] newAscendingList){
        this._ascendingList = newAscendingList;
    }

    private void setDescendingList(Integer[] newDescendingList){
        this._descendingList = newDescendingList;
    }

    public boolean generate(int aMaxDataSize){
        if(aMaxDataSize <= 0){                                                                                          //데이터 사이즈가 0이하라면 만들어낼 수 없다.
            return false;
        }
        else{
            this.setMaxDataSize(aMaxDataSize);
            this.setRandomList(DataGenerator.randomList(this.maxDataSize()));                                           //만들어뒀던 데이터 제너레이터를 이용해 랜덤리스트를 만든다.
            this.setAscendingList(DataGenerator.ascendingList(this.maxDataSize()));                                     //랜덤리스트와 마찬가지로 오름/내림차순 데이터도 만든다.
            this.setDescendingList(DataGenerator.descendingList(this.maxDataSize()));
            return true;                                                                                                //생성에 성공했으므로 true 반
        }
    }

    public Integer[] listWithOrder(ListOrder anOrder){
        if(anOrder.equals(ListOrder.Random)){                                                                           //인자로 전달받은 정렬방식에 따라서 알맞은 리스트를 반환해준다.
            return this.randomList();
        }
        else if(anOrder.equals(ListOrder.Ascending)){
            return this.ascendingList();
        }
        else if(anOrder.equals(ListOrder.Descending)){
            return this.descendingList();
        }
        else{
            return null;
        }
    }
}
