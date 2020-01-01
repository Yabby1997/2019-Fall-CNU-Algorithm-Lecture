package huffman;

public class HuffmanCode {
    private int _length;
    private boolean[] _code = new boolean[Huffman_CONST.MAX_CODE_LENGTH];

    //constructor
    protected HuffmanCode(){
        this.reset();
    }

    protected void reset(){
        this.setLength(0);
    }

    private void setLength(int newLength){
        this._length = newLength;
    }

    private boolean[] code(){
        return this._code;
    }

    public int length(){
        return this._length;
    }

    private void setBitAtIndex(int index, boolean bitValue){
        this.code()[index] = bitValue;
    }

    protected HuffmanCode clone(){
        HuffmanCode clone = new HuffmanCode();
        clone.setLength(this.length());
        for(int i = 0; i < this.length(); i++){
            clone.setBitAtIndex(i, this.bitAtIndex(i));                                                                 //복제할 huffmanCode의 i번째자리에 i번째 요소 넣어 복사
        }
        return clone;
    }

    protected void appendBit(boolean bitValue){
        if(this.length() < Huffman_CONST.MAX_CODE_LENGTH){                                                              //끝에 하나 추가하고 사이즈 증가 .
            this.setBitAtIndex(this.length(), bitValue);
            this.setLength(this.length() + 1);
        }
    }

    protected void removeLastBit(){
        if(this.length() > 0){
            this.setLength(this.length() - 1);                                                                          //사이즈를 감소시킴
        }
    }

    public boolean bitAtIndex(int index){
        if(index >= 0 && index < this.length()){
            return this.code()[index];
        }
        else{
            throw new IllegalStateException("IllegalStateException : Given Index is out of range");
        }
    }
}
