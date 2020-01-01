package fileIO;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class BitOutputManager {
    private BufferedOutputStream _bitOutputStream;

    private int _bitBuffer;
    private int _numberOfBitsInBuffer;

    //constructor
    public BitOutputManager(BufferedOutputStream givenBitOutputStream){
        this.setBitOutputStream(givenBitOutputStream);                                                                  //bit buffer clear
        this.setNumberOfBitsInBuffer(0);
        this.setBitBuffer(0);
    }

    //getter/setter
    private BufferedOutputStream bitOutputStream(){
        return this._bitOutputStream;
    }

    private int bitBuffer(){
        return this._bitBuffer;
    }

    private int numberOfBitsInBuffer(){
        return this._numberOfBitsInBuffer;
    }

    private void setBitOutputStream(BufferedOutputStream newBitOutputStream){
        this._bitOutputStream = newBitOutputStream;
    }

    private void setBitBuffer(int newBitBuffer){
        this._bitBuffer = newBitBuffer;
    }

    private void setNumberOfBitsInBuffer(int newNumberOfBitsInBuffer){
        this._numberOfBitsInBuffer = newNumberOfBitsInBuffer;
    }

    public void writeBit(boolean bitValue) throws IOException{                                                          //bit buffer에 bit를 추가
        this.setBitBuffer(this.bitBuffer() << 1);
        if(bitValue){                                                                                                   //bit value가 있으면 1을 기록
            this.setBitBuffer(this.bitBuffer() + 1);
        }
        this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() + 1);                                                  //버퍼에 한비트 추가되었으므로

        if(this.numberOfBitsInBuffer() == 8){                                                                           //버퍼가 사이즈 8, 즉 한바이트로 가득차면
            this.bitOutputStream().write(this.bitBuffer());                                                             //비트들로 다 쓰여진 한바이트를 bufferedOutputStream에 쓴다.
            this.setBitBuffer(0);                                                                                       //다음 입력들을 기록하기위해 비트버퍼를 0으로 초기화, 입력 비트 개수도 0으로 초기화해준다.
            this.setNumberOfBitsInBuffer(0);
        }
    }

                /*
                final exam practice
                 */
                public void writeBit(boolean bitValue) throws IOException{
                    this.setBitBuffer(this.bitBuffer() << 1);
                    if(bitValue){
                        this.setBitBuffer(this.bitBuffer() + 1);
                    }
                    this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() + 1);

                    if(this.numberOfBitsInBuffer() == 8){
                        this.bitOutputStream().write(bitBuffer());
                        this.setBitBuffer(0);
                        this.setNumberOfBitsInBuffer(0);
                    }
                }

                public void writeBit(boolean bitValue) throws IOException{
                    this.setBitBuffer(this.bitBuffer() << 1);

                    if(bitValue){
                        this.setBitBuffer(this.bitBuffer() + 1);
                    }
                    this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() + 1);

                    if(this.numberOfBitsInBuffer() == 8){
                        this.bitOutputStream().write(this.bitBuffer());
                        this.setBitBuffer(0);
                        this.setNumberOfBitsInBuffer(0);
                    }
                }

                public void writeBit(boolean bitValue) throws IOException{
                    this.setBitBuffer(this.bitBuffer() << 1);
                    if(bitValue){
                        this.setBitBuffer(this.bitBuffer() + 1);
                    }
                    this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() + 1);
                    if(this.numberOfBitsInBuffer() == 8){
                        this.bitOutputStream().write(this.bitBuffer());
                        this.setBitBuffer(0);
                        this.setNumberOfBitsInBuffer(0);
                    }
                }

                public void writeBit(boolean bitValue) throws IOException{
                    this.setBitBuffer(this.bitBuffer() << 1);
                    if(bitValue){
                        this.setBitBuffer(this.bitBuffer() << 1);
                    }
                    this.setNumberOfBitsInBuffer(this.numberOfBitsInBuffer() + 1);
                    if(this.numberOfBitsInBuffer() == 8){
                        this.bitOutputStream().write(this.bitBuffer());
                        this.setNumberOfBitsInBuffer(0);
                        this.bitBuffer(0);
                    }
                }

                /*
                final exam practice
                 */

    public void flush() throws IOException{
        if(this.numberOfBitsInBuffer() > 0 && this.numberOfBitsInBuffer() < FileIO_CONST.NUMBER_OF_BITS_OF_BYTE){       //기록 안된 남은 비트들을 위해서. 0보단 큰데 8개보단 작은 크기의 비트들이 추가로 있을수도 있다.
            int numberOfShiftLeftsToMakeLeftJustifiedInType = FileIO_CONST.NUMBER_OF_BITS_OF_BYTE - this.numberOfBitsInBuffer();            //남은 비트들을 한바이트에 맞게 기록하려면 shift해줘야 한다. 8빼기 비트수를 통해 몇번 할지 결정
            this.setBitBuffer(this.bitBuffer() << numberOfShiftLeftsToMakeLeftJustifiedInType);                         //계산된 만큼 left shift를 통해 맞춰준다.
            this.bitOutputStream().write(this.bitBuffer());                                                             //맞춰진 bit들을 추가로 써준다.
            this.setBitBuffer(0);                                                                                       //마찬가지로 초기화해준다.
            this.setNumberOfBitsInBuffer(0);
        }
    }

                /*
                final exam practice
                 */
                public void flush() throws IOException{
                    if(this.numberOfBitsInBuffer() > 0 && this.numberOfBitsInBuffer() < 8){
                        int numberOfShifts = 8 - this.numberOfBitsInBuffer();
                        this.setBitBuffer(this.bitBuffer() << numberOfShifts);
                        this.bitOutputStream().write(this.bitBuffer());
                        this.setBitBuffer(0);
                        this.setNumberOfBitsInBuffer(0);
                    }
                }

                public void flush() throws IOException{
                    if(this.numberOfBitsInBuffer() > 0 && this.numberOfBitsInBuffer() < 8){
                        int numberOfShifts = 8 - this.numberOfBitsInBuffer();
                        this.setBitBuffer(this.bitBuffer() << numberOfShifts);
                        this.bitOutputStream().write(this.bitBuffer());
                        this.setBitBuffer(0);
                        this.setNumberOfBitsInBuffer(0);
                    }
                }

                public void flush() throws IOException{
                    if(this.numberOfBitsInBuffer() > 0 && this.numberOfBitsInBuffer() < 8){
                        int numberOfShifts = 8 - this.numberOfBitsInBuffer();
                        this.setBitBuffer(this.bitBuffer() << numberOfShifts);
                        this.bitOutputStream().write(this.bitBuffer());
                        this.setBitBuffer(0);
                        this.setNumberOfBitsInBuffer(0);
                    }
                }

                public void flush() throws IOException{
                    if(this.numberOfBitsInBuffer() > 0 && this.numberOfBitsInBuffer() < 8){
                        int numberOfShifts = 8 - this.numberOfBitsInBuffer();
                        this.bitOutputStream().write(this.bitBuffer() << numberOfShifts);
                        this.setBitBuffer(0);
                        this.numberOfBitsInBuffer(0);
                    }
                }
                /*
                final exam practice
                 */
}