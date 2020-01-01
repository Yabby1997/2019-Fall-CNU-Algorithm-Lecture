package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import fileIO.BitInputManager;
import fileIO.ExtendedBufferedInputStream;
import fileIO.ExtendedBufferedOutputStream;
import fileIO.FilePathManager;
import huffman.HuffmanDecoder;
import huffman.Huffman_CONST;

public class DecompressionController {
    private static final boolean DEBUG_MODE = false;                                                                    //디버그메시지
    private static void showDebugMessage(String aMessage) {
        if(DecompressionController.DEBUG_MODE) {
            AppView.outputLine("[debug] (DecompressionController) " + aMessage);
        }
    }

    private File _compressedFile;                                                                                       //압축된 파일과 압축 될 파일
    private File _decompressedFile;
    private ExtendedBufferedInputStream _compressedInputStream;                                                         //압축된 파일과 압축 될 파일을 읽고 쓸 스트림
    private ExtendedBufferedOutputStream _decompressedOutputStream;
    private BitInputManager _bitInputManager;                                                                           //압축파일을 한비트씩 읽기위한 bitInputManager
    private HuffmanDecoder _huffmanDecoder;                                                                             //허프만코드를 디코드하기위한 디코더

    //getter/setter
    private File compressedFile(){
        return this._compressedFile;
    }

    private void setCompressedFile(File newCompressedFile){
        this._compressedFile = newCompressedFile;
    }

    private File decompressedFile(){
        return this._decompressedFile;
    }

    private void setDecompressedFile(File newDecompressedFile){
        this._decompressedFile = newDecompressedFile;
    }

    private ExtendedBufferedInputStream compressedInputStream(){
        return this._compressedInputStream;
    }

    private void setCompressedInputStream(ExtendedBufferedInputStream newCompressedInputStream){
        this._compressedInputStream = newCompressedInputStream;
    }

    private ExtendedBufferedOutputStream decompressedOutputStream(){
        return this._decompressedOutputStream;
    }

    private void setDecompressedOutputStream(ExtendedBufferedOutputStream newDecompressedOutputStream){
        this._decompressedOutputStream = newDecompressedOutputStream;
    }

    private BitInputManager bitInputManager(){
        return this._bitInputManager;
    }

    private void setBitInputManager(BitInputManager newBitInputManager){
        this._bitInputManager = newBitInputManager;
    }

    private HuffmanDecoder huffmanDecoder(){
        return this._huffmanDecoder;
    }

    private void setHuffmanDecoder(HuffmanDecoder newHuffmanDecoder){
        this._huffmanDecoder = newHuffmanDecoder;
    }
    //end of getter/setter

    protected DecompressionController(){}

    private boolean initCompressedFile(){                                                                               //압축된파일의 경로와 이름을 받는다.
        DecompressionController.showDebugMessage("initCompressedFile called");
        AppView.outputLine("");
        AppView.outputLine("? 압축을 풀 파일의 경로와 이름을 입력하시오 : ");
        String filePath = AppView.inputFilePath();
        String fileName = AppView.inputFileName();
        String filePathAndName = filePath + "/" + fileName;                                                             //입력받은 path와 name을 통해 file의 경로를 완성
        this.setCompressedFile(new File(filePathAndName));                                                              //compressed file을 입력받은 file path로
        if(this.compressedFile().exists()){
            if((FilePathManager.getFileExtension(this.compressedFile()).equals(App_CONST.COMPRESSED_FILE_EXTENSION))) {
                return true;                                                                                            //파일이 존재하고 확장자가 App_CONST에 정의해둔 확장자와 같다면 성공
            }
            else{                                                                                                       //확장자가 다르다면 에러메시지 출력후 false 반환
                AppView.outputLine("! 오류 : 압축을 풀 파일 (" + filePathAndName + ") 의 확장자가 (" + App_CONST.COMPRESSED_FILE_EXTENSION + ") 이 아닙니다.");
                return false;
            }
        }
        else{
            AppView.outputLine("! 오류 : 파일 (" + filePathAndName + ") 이 존재하지 않습니다.");                        //파일이 없다면 에러 메시지 출력하고 false 반환.
            return false;
        }
    }

    private void initDecompressedFile(){
        DecompressionController.showDebugMessage("initDecompressedFile called");
        AppView.outputLine("");
        String filePathAndName = this.compressedFile().getAbsolutePath();                                                                                       //압축했던 파일이므로 (.../.../something.txt.MYZIP)

        String decompressedFilePathAndNameWithoutCFE = FilePathManager.getFilePathAndNameWithoutExtension(this.compressedFile());                               //CFE(.MYZIP)를 제거해준다. (.../.../something.txt)
        this.setDecompressedFile(new File(decompressedFilePathAndNameWithoutCFE));                                                                              //CFE제거된 파일명으로 임시파일만듬
        String decompressedFileExtension = FilePathManager.getFileExtension(this.decompressedFile());                                                           //CFE제거된 진짜 확장자 얻음. (.txt)
        String decompressedFilePathAndNameWithoutExtensionAndCFE = FilePathManager.getFilePathAndNameWithoutExtension(this.decompressedFile());                 //확장자도 제거 (.../.../something)
        String decompressedFilePathAndName = decompressedFilePathAndNameWithoutExtensionAndCFE + decompressedFileExtension;                                     //재조합해서 압축해제 파일 절대경로/이름 만듬 (.../.../something.txt)
        this.setDecompressedFile(new File(decompressedFilePathAndName));

        if(this.decompressedFile().exists()){                                                                                                                   //이미 존재한다면
            AppView.outputLine("! 경고 : 압축 해제 파일 (" + decompressedFilePathAndName + ") 이 이미 존재합니다.");
            AppView.outputLine("- 압축 해제 파일의 이름을 다른 것으로 바꾸어 처리합니다 : ");
            int decompressedFileSerialNumber = 0;
            do{
                decompressedFileSerialNumber++;                                                                                                                 //시리얼넘버를 추가로 넣어준다.
                decompressedFilePathAndName = decompressedFilePathAndNameWithoutExtensionAndCFE + App_CONST.DECOMPRESSED_FILE_INFIX + decompressedFileSerialNumber + decompressedFileExtension; //(.../.../something_UNZIP_n.txt)
                this.setDecompressedFile(new File(decompressedFilePathAndName));
            }while(this.decompressedFile().exists());
            AppView.outputLine("- 새로운 압축 해제 파일의 이름은 " + decompressedFilePathAndName + " 입니다. ");
        }
    }

    private short[][] readSerializedHuffmanTree() throws IOException{
        DecompressionController.showDebugMessage("readSerializedHuffmanTree called");
        short numberOfNodesInSerializedHuffmanTree = this.compressedInputStream().readShort();                          //압축된 파일의 허프만트리를 읽기전에 먼저 short단위만큼 읽어들이면 직렬화된 허프만트리의 노드를 얻을 수 있다.
        short[][] serializedHuffmanTree = new short[numberOfNodesInSerializedHuffmanTree][2];                           //노드의 개수를 알았으므로 그를 토대로 직렬화 허프만트리를 저장할 공간을 확보한다.
        for(int i = 0; i < numberOfNodesInSerializedHuffmanTree; i++){                                                  //노드수만큼 반복하며 좌측노드와 우측노드를 순서대로 short단위로 읽어 기록한다.
            try{
                serializedHuffmanTree[i][Huffman_CONST.LEFT_OF_NODE] = this.compressedInputStream().readShort();
                serializedHuffmanTree[i][Huffman_CONST.RIGHT_OF_NODE] = this.compressedInputStream().readShort();
            }
            catch(IOException e){
                AppView.outputLine("! 오류 : [직렬화 된 허프만 트리]를 읽어오는 작업에 실패하였습니다.");
                throw e;
            }
        }
        return serializedHuffmanTree;                                                                                   //읽어들인 직렬화된 허프만트리를 반환해준다.
    }

                /*
                final exam practice
                 */
                private short[][] readSerializedHuffmanTree() throws IOException{
                    short numberOfNodesInSerializedHuffmanTree = this.compressionInputStream().readShort();
                    short[][] serializedHuffmanTree = new short[numberOfNodesInSerializedHuffmanTree][2];
                    for(int i = 0; i < numberOfNodesInSerializedHuffmanTree; i++){
                        try{
                            serializedHuffmanTree[i][0] = this.compressedInputStream().readShort();
                            serializedHuffmanTree[i][1] = this.compressedInputStream().readShort();
                        }
                        catch(IOException e){
                            throw e;
                        }
                    }
                    return serializedHuffmanTree;
                }

                private short[][] readSerializedHuffmanTree() throws IOException{
                    short numberOfNodes = this.compressedInputStream().readShort();
                    short[][] serializedHuffmanTree = new short[numberOfNodes][2];
                    for(int i = 0; i < numberOfNodes; i++){
                        try{
                            serializedHuffmanTree[i][0] = this.compressedInputStream().readShort();
                            serializedHuffmanTree[i][1] = this.compressedInputStream().readShort();
                        }
                        catch(IOException e){
                            throw e;
                        }
                    }
                    return serializedHuffmanTree;
                }

                private short[][] readSerializedHuffmanTree() throws IOException{
                    short numberOfNodes = this.compressedInputStream().readShort();
                    short[][] serializedHuffmanTree = new short[numberOfNodes][2];
                    for(int i = 0; i < numberOfNodes; i++){
                        try{
                            serializedHuffmanTree[i][0] = this.compressedInputStream().readShort();
                            serializedHuffmanTree[i][1] = this.compressedInputStream().readShort();
                        }
                    }
                }

                private short[][] readSerializedHuffmanTree() throws IOException{
                    short numberOfNodes = this.decompressedOutputStream().readShort();
                    short[][] serializedHuffmanTree = new short[numberOfNodes][2];
                    for(short i = 0; i < numberOfNodes; i++){
                        try{
                            serializedHuffmanTree[i][0] = this.compressedInputStream().readShort();
                            serializedHuffmanTree[i][1] = this.compressedInputStream().readShort();
                        }
                        catch(IOException e){
                            throw e;
                        }
                    }
                    return serializedHuffmanTree;
                }

                private short[][] readSerializedHuffmanTree() throws IOException{
                    short numberOfNodes = this.compressedInputStream().readShort();
                    short[][] serializedHuffmanTree = new short[numberOfNodes][2];
                    for(short i = 0; i < nuberOfNodes; i++){
                        try{
                            serializedHuffmanTree[i][0] = this.compressedInputStream().readShort();
                            serializedHuffmanTree[i][1] = this.compressedInputStream().readShort();
                        }
                        catch(IOException e){
                            throw e;
                        }
                    }
                    return serializedHuffmanTree;
                }
                /*
                final exam practice
                 */



    private long readNumberOfBitsOfCompressedData() throws IOException{                                                 //압축된 데이터의 크기를 얻어온다. long단위로 읽는다. 노드개수, 허프만트리 다음에 위치한정보다.
        DecompressionController.showDebugMessage("readNumberOfBitsOfCompressedData called");
        try{
            return this.compressedInputStream().readLong();
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : [압축된 데이터 비트 수]를 읽어오는 작업에 실패하였습니다.");
            throw e;
        }
    }


                /*
                final exam practice
                 */
                private long readNumberOfBitsCompressedData() throws IOException{
                    try{
                        return this.compressedInputStream().readLong();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private long readNumberOfBitsOfCompressedData() throws IOException{
                    try{
                        return this.compressedInputStream().readLong();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private long readNumberOfBitsOfCompressedData() throws IOException{
                    try{
                        return this.compressedInputStream().readLong();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private long readNumberOfBitsOfCompressedData() throws IOEXception{
                    try{
                        return this.compressedInputStream().readLong();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }
                /*
                final exam practice
                 */


    private void openDecompressedOutputStream() throws IOException{                                                     //압축된파일과 압축해제될 파일의 스트림을 연다.
        DecompressionController.showDebugMessage("openDecompressedOutputStream called");
        try {
            FileOutputStream decompressedFileOutputStream = new FileOutputStream(this.decompressedFile());
            this.setDecompressedOutputStream(new ExtendedBufferedOutputStream(decompressedFileOutputStream));
        } catch (FileNotFoundException e) {
            AppView.outputLine("! 오류 : 압축 해제 파일을 열 수 없습니다.");
            throw e;
        }
    }

    private void openCompressedInputStream() throws IOException{
        DecompressionController.showDebugMessage("openCompressedInputStream called");
        try{
            FileInputStream compressedFileInputStream = new FileInputStream(this.compressedFile());
            this.setCompressedInputStream(new ExtendedBufferedInputStream(compressedFileInputStream));
        }
        catch(FileNotFoundException e) {
            AppView.outputLine("! 오류 : 압축 파일을 열 수 없습니다.");
            throw e;
        }
    }

    private void writeDecompressedBits() throws IOException{
        DecompressionController.showDebugMessage("writeDecompressedBits called");
        try {
            long numberOfCompressedBits = this.readNumberOfBitsOfCompressedData();                                      //노드수, 허프만트리, 비트수, 압축데이터 순으로 구성되어있으므로 비트수를 읽은뒤 그를 기반으로 반복문을 돌리며 디코드한다.
            for (long i = 0; i < numberOfCompressedBits; i++) {                                                         //모든 비트를 읽어들이면서
                int byteCode = this.huffmanDecoder().decodeBit(this.bitInputManager().readBit());                       //decode 를 한다. bytecode가 찾아지면 그 값을 리턴하고, 그렇지않으면 -1을 리턴한다.
                if(byteCode != -1) {                                                                                    //반환값이 -1이 아니라면 bytecode가 찾아진것. 쓴다.
                    DecompressionController.showDebugMessage("byteCode : " + byteCode);
                    this.decompressedOutputStream().write(byteCode);
                }
            }
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : [허프만 코드]를 디코드하는 작업에 실패하였습니다.");
        }
    }

                /*
                final exam practice
                 */
                private void writeDecompressedBits() throws IOException{                                                //허프만트리를 읽은 후에나 할 수 있다. decodeBit
                    try{
                        long numberOfCompressedBits = this.readNumberOfBitsCompressedData();
                        for(long i = 0; i < numberOfCompressedBits; i++){
                            int byteCode = this.huffmanDecoder().decodeBit(this.bitInputManager().readBit());
                            if(byteCode != -1){
                                this.decompressedOutputStream().write(byteCode);
                            }
                        }
                    }
                    catch(IOException e){
                        AppView.outputLine("");
                    }
                }

                private void writeDecompressedBits() throws IOException{
                    try{
                        long numberOfCompressedBits = this.readNumberOfBitsOfCompressedData();
                        for(long i = 0; i < numberOfCompressedBits; i++){
                            int byteCode = this.huffmanDecoder().decodeBit(this.bitInputManager().readBit());
                            if(byteCode != -1){
                                this.decompressedOutputStream().write(byteCode);
                            }
                        }
                    }
                    catch(IOException e){
                        AppView.outputLine("");
                    }
                }

                private void writeDecompressedBits() throws IOException{
                    try{
                        long numberOfCompressedBits = this.readNumberOfBitsOfCompressedData();
                        for(long i = 0; i < numberOfCompressedBits; i++){
                            int byteCode = this.huffmanDecoder().decodeBit(this.bitInputManager().readBit());
                            if(byteCode != -1){
                                this.decompressedOutputStream().write(byteCoe);
                            }
                        }
                    }
                    catch(IOException e){
                        AppView.outputLine("");
                    }
                }

                private void writeDcompressedBits() throws IOException{
                    try{
                        long numberOfDecompressedBits = this.readNumberOfBitsOfCompressedData();
                        for(long i = 0; i < numberOfDecompressedBits; i++) {
                            int byteCode = this.huffmanDecoder().decodeBit(this.bitInputManager().readBit());
                            if (byteCode != -1) {
                                this.decompressedOutputStream().write(byteCode);
                            }
                        }
                    }
                    catch(IOException e){
                        AppView.outputLine("");
                    }
                }

                private void writeDecompressedBits() throws IOException{
                    try{
                        long numberOfDecompressedBits = this.compressedInputStream().readLong();
                        for(long i = 0; i < numberOfDecompressedBits; i++){
                            int byteCode = this.huffmanDecoder().decodeBit(this.bitInputManager().readBit());
                            if(byteCode != -1){
                                this.decompressedOutputStream().write(byteCode);
                            }
                        }
                    }
                    catch(IOException e){
                        AppView.outputLine("");
                    }
                }
                /*
                final exam practice
                 */



    private void closeCompressedInputStream() throws IOException{                                                       //압축파일과 압축해제된파일의 스트림을 닫는다.
        DecompressionController.showDebugMessage("closeCompressedInputStream called");
        try{
            this.compressedInputStream().close();
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 압축 파일 닫기에 실패하였습니다.");
        }
    }

    private void closeDecompressedOutputStream() throws IOException{
        DecompressionController.showDebugMessage("closeDecompressedOutputStream called");
        try{
            this.decompressedOutputStream().close();
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 압축 해제 파일 닫기에 실패하였습니다.");
        }
    }

    private void decompress() throws IOException{                                                                       //압축을 해제한다.
        DecompressionController.showDebugMessage("decompress called");                                        //압축파일과 압축해제될 파일의 스트림을열고
        this.openCompressedInputStream();
        this.openDecompressedOutputStream();

        this.setBitInputManager(new BitInputManager(this.compressedInputStream()));                                     //압축파일의 스트림으로 비트입력매니저를 설정하고
        this.setHuffmanDecoder(new HuffmanDecoder(this.readSerializedHuffmanTree()));                                   //허프만트리를 읽어들여 그를 토대로 디코더를 생성, 설정해준다.
        this.writeDecompressedBits();                                                                                   //디코드한 결과를 쓴다.

        this.closeCompressedInputStream();                                                                              //압축파일과 압축해제될 파일의 스트림을 닫는다.
        this.closeDecompressedOutputStream();
    }

                /*
                final exam practice
                 */
                private void decompress() throws IOException{
                    this.openCompressedInputStream();
                    this.openDecompressedOutputStream();
                    this.setBitInputManager(new BitInputManager(this.compressedInputStream()));
                    this.setHuffmanDecoder(new HuffmanDecoder(this.readSerializedHuffmanTree()));
                    this.writeDecompressedBits();
                    this.closeCompressedInputStream();
                    this.closeDecompressedOutputStream();
                }

                private void decompress() throws IOException{
                    this.openCompressedInputStream();
                    this.openDecompressedOutputStream();

                    this.setBitInputManager(new BitInputManager(this.compressedInputStream()));
                    this.setHuffmanDecoder(new HuffmanDecoder(this.readSerializedHuffmanTree()));

                    this.writeDecompressedBits();

                    this.closeCompressedInputStream();
                    this.closeDecompressedOutputStream();
                }

                private void decompress() throws IOException{
                    this.openCompressedInputStream();
                    this.openDecompressedOutputStream();

                    this.setBitInputManager(new BitInputManager(this.compressedInputStream()));
                    this.setHuffmanDecoder(new HuffmanDecoder(this.readSerializedHuffmanTree()));

                    this.writeDecompressedBits();

                    this.closeCompressedInputStream();
                    this.closeDecompressedOutputStream();
                }

                private void decompress() throws IOException{
                    this.openCompressedInputStream();
                    this.openDecompressedOutputStream();

                    this.setBitInputManager(new BitInputManager(this.compressedInputStream()));
                    this.setHuffmanDecoder(new HuffmanDecoder(this.readSerializedHuffmanTree()));

                    this.writeDecompressedBits();

                    this.closeCompressedInputStream();
                    this.closeDecompressedOutputStream();
                }
                /*
                final exam practice
                 */



    private void showStatistics() throws IOException{                                                                   //압축해제 결과를 출력한다.
        DecompressionController.showDebugMessage("showStatistics called");
        AppView.outputLine("> 압축 해제 정보 : ");
        long compressedFileSize = this.compressedFile().length();
        long decompressedFileSize = this.decompressedFile().length();
        AppView.outputLine("- 압축 파일 : " + this.compressedFile().getAbsolutePath() + " (" + compressedFileSize + " Byte)");
        AppView.outputLine("- 압축 해제 파일 : " + this.decompressedFile().getAbsolutePath() + " (" + decompressedFileSize + " Byte)");
    }


                /*
                final exam practice
                 */
                private void showStatistics() throws IOException{
                    AppView.outputLine("decompress result");
                    long compressedFileSize = this.compressedFile().length();
                    long decompressedFileSize = this.decompressedFile().length();
                    AppView.outputLine("compression file " + this.compressedFile().getAbsolutePath() + " is " + compressedFileSize + "bytes");
                    AppView.outputLine("decompressed file" + this.decompressedFile().getAbsolutePath() + " is " + decompressedFileSize + "bytes");
                }
                /*
                final exam practice
                 */


    protected void run(){
        DecompressionController.showDebugMessage("run called");
        if(this.initCompressedFile()){
            this.initDecompressedFile();
            try{
                this.decompress();
                AppView.outputLine("");
                AppView.outputLine("! 압축 해제를 성공적으로 마쳤습니다.");
                this.showStatistics();
            }
            catch(IOException e){
                AppView.outputLine("! 오류 : 압축을 실행하는 동안에 파일 처리 오류가 발생하였습니다.");
            }
        }
    }

                /*
                final exam practice
                 */
                protected void run(){
                    if(this.initCompressedFile()){
                        this.initDecompressedFile();
                        try{
                            this.decompress();
                            this.showStatistics();
                        }
                        catch(IOException e){
                            AppView.outputLine("");
                        }
                    }
                }

                protected void run(){
                    if(this.initCompressedFile()){
                        this.initDecompressedFile();
                        try{
                            this.decompress();
                            this.showStatistics();
                        }
                        catch(IOException e){
                            AppView.outputLine("");
                        }
                    }
                }

                protected void run(){
                    if(this.initCompressedFIle()){
                        this.initDecompressedFile();
                        try{
                            this.decompress();
                            this.showStatistics();
                        }
                        catch(IOException e){
                            AppView.outputLine("");
                        }
                    }
                }

                protected void run(){
                    if(this.initCompressedFile()){
                        this.initDecompressedFile();
                        try{
                            this.decompress();
                            this.showStatistics();
                        }
                        catch(IOException e){
                            AppView.outputLine("");
                        }
                    }
                }
                /*
                final exam practice
                 */


}
