package fileIO;

import app.AppView;

import java.io.IOException;

public class BitInputManager {
    private static final boolean DEBUG_MODE = false;                                                                    //디버그 메시지를 정의해서 사용했다.
    private static void showDebugMessage(String aMessage) {
        if(BitInputManager.DEBUG_MODE) {
            AppView.outputLine("[debug] (BitInputManager) " + aMessage);
        }
    }

    private ExtendedBufferedInputStream _bitInputStream;                                                                //비트입력을 받을 extendedBufferedInputStream과 버퍼, 버퍼에 들어있는 비트수를 나타낼 변수를 선언

    private int _bitBuffer;
    private int _numberOfBitsInBuffer;

    //constructor
    public BitInputManager(ExtendedBufferedInputStream givenBitInputStream){                                            //생성시 입력된 스트림으로 초기화
        this.setBitInputStream(givenBitInputStream);
        this.setBitBuffer(0);
        this.setNumberOfBitsInBuffer(0);
    }

    //getter/setter
    private ExtendedBufferedInputStream bitInputStream(){
        return this._bitInputStream;
    }

    private void setBitInputStream(ExtendedBufferedInputStream newBitInputStream){
        this._bitInputStream = newBitInputStream;
    }

    private int bitBuffer(){
        return this._bitBuffer;
    }

    private void setBitBuffer(int newBitBuffer){
        this._bitBuffer = newBitBuffer;
    }

    private int numberOfBitsInBuffer(){
        return this._numberOfBitsInBuffer;
    }

    private void setNumberOfBitsInBuffer(int newNumberOfBitsInBuffer){
        this._numberOfBitsInBuffer = newNumberOfBitsInBuffer;
    }
    //end of getter/setter

    public int readBit() throws IOException{
        BitInputManager.showDebugMessage("numberOfBitsInBuffer : " + this.numberOfBitsInBuffer());            //java에서 한비트씩 읽는 방법이 없으므로 직접 구현한다.
        if(this.numberOfBitsInBuffer() == 0) {                                                                          //버퍼에 들어있는 비트가 0이라면
            this.setBitBuffer(this.bitInputStream().read());                                                            //read를 통해 8비트(한바이트)를 읽어들이고
            BitInputManager.showDebugMessage("bitBuffer : " + this.bitBuffer());
            this.setNumberOfBitsInBuffer(8);                                                                            //8비트 읽었으므로 버퍼에 비트 수를 8로 만들어준다.
        }
        this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() - 1);                                                  //한비트씩 순차로 읽어야하므로 첫비트 읽을땐 7번, 두번째는 6번.... 마지막은 0번 right shift하여 lsb를 가져온다.
        return ((this.bitBuffer() >> this.numberOfBitsInBuffer()) & 1);                                                 //실제 버퍼에는 8비트가 들어있지만, 읽어들일때마다 한비트씩 읽어 없어진거로 간주, 이를 반복한다. 1과 and 연산하여 lsb를 얻을 수 있다.
    }
                /*
                final exam practice
                 */
                public int readBit() throws IOException{
                    if(this.numberOfBitsInBuffer() == 0){
                        this.setBitBuffer(this.bitInputStream().read());
                        this.setNumberOfBitsInBuffer(8);
                    }
                    this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() - 1);
                    return ((this.bitBuffer() >> this.numberOfBitsInBuffer()) & 1);
                }

                public int readBit() throws IOException{
                    if(this.numberOfBitsInBuffer() == 0){
                        this.setBitBuffer(this.bitInputStream().read());
                        this.setNumberOfBitsInBuffer(8);
                    }
                    this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() - 1);
                    return ((this.bitBuffer() >> this.numberOfBitsInBuffer()) & 1);
                }

                public int readBit() throws IOException{
                    if(this.numberOfBitsInBuffer() == 0){
                        this.setBitBuffer(this.bitInputStream().read());
                        this.setNumberOfBitsInBuffer(8);
                    }
                    this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() - 1);
                    return ((this.bitBuffer() >> this.numberOfBitsInBuffer()) & 1);
                }

                public int readBit() throws IOException{
                    if(this.numberOfBitsInBuffer() == 0){
                        this.setBitBuffer(this.bitInputStream().read());
                        this.setNumberOfBitsInBuffer(8);
                    }
                    this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() - 1);
                    return ((this.bitBuffer() >> this.numberOfBitsInBuffer()) & 1);
                }
                /*
                final exam practice
                 */
}
