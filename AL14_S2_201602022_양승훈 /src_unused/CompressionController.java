package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import fileIO.*;

import huffman.HuffmanCode;
import huffman.HuffmanEncoder;
import huffman.HuffmanTree;
import huffman.Huffman_CONST;

public class CompressionController {
    //member
    private File _originalFile;
    private File _compressedFile;

    private ExtendedBufferedInputStream _originalInputStream;
    private ExtendedBufferedOutputStream _compressedOutputStream;

    private BitOutputManager _bitOutputManager;

    private HuffmanEncoder _huffmanEncoder;

    //constructor
    protected CompressionController(){};

    //getter setter
    private File originalFile(){
        return this._originalFile;
    }

    private File compressedFile(){
        return this._compressedFile;
    }

    private ExtendedBufferedInputStream originalInputStream(){
        return this._originalInputStream;
    }

    private ExtendedBufferedOutputStream compressedOutputStream(){
        return this._compressedOutputStream;
    }

    private BitOutputManager bitOutputManager(){
        return this._bitOutputManager;
    }

    private HuffmanEncoder huffmanEncoder(){
        return this._huffmanEncoder;
    }

    private void setOriginalFile(File newOriginalFile){
        this._originalFile = newOriginalFile;
    }

    private void setCompressedFile(File newCompressedFile){
        this._compressedFile = newCompressedFile;
    }

    private void setOriginalInputStream(ExtendedBufferedInputStream newOriginalInputStream){
        this._originalInputStream = newOriginalInputStream;
    }

    private void setCompressedOutputStream(ExtendedBufferedOutputStream newCompressedOutputStream){
        this._compressedOutputStream = newCompressedOutputStream;
    }

    private void setBitOutputManager(BitOutputManager newBitOutputManager){
        this._bitOutputManager = newBitOutputManager;
    }

    private void setHuffmanEncoder(HuffmanEncoder newHuffmanEncoder){
        this._huffmanEncoder = newHuffmanEncoder;
    }

    private int readByteCode() throws IOException{
        try{
            return this.originalInputStream().read();                                                                   //원본파일로부터 바이트단위로 읽어온다. 오류발생시 오류메시지 출력
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 원본 파일 읽기를 실패하였습니다.");
            throw e;
        }
    }

    private void sendAllByteCodesToHuffmanEncoderForCountingFrequencies() throws IOException{
        try{
            this.openOriginalInputStream();                                                                             //오리지널 파일 읽기위해 스트림 열음
            int byteCode = this.readByteCode();                                                                         //오리지널 파일의 바이트를 읽어옴
            while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM){                                                        //파일의 끝까지
                this.huffmanEncoder().incrementFrequencyOf(byteCode);                                                   //해당하는 byteCode의 frequency를 증가시킨다.
                byteCode = this.readByteCode();                                                                         //반복적으로 읽어들임.
            }
            this.closeOriginalInputStream();                                                                            //스트림 닫아줌
        }
        catch(IOException e){                                                                                           //오류 발생시 error메시지 출력
            AppView.outputLine("! 오류 : 원본 파일을 읽어 빈도를 세는 작업을 실패했습니다.");
            throw e;
        }
    }

                /*
                final exam practice
                 */
                private void sendAllByteCodesToHuffmanEncoderForCountingFrequencies() throws IOException{
                    try{
                        this.openOriginalInputStream();
                        int byteCode = this.readByteCode();
                        while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM){
                            this.huffmanEncoder().incrementFrequencyOf(byteCode);
                            byteCode = this.readByteCode();
                        }
                        this.closeOriginalInputStream();
                    }
                    catch(IOexception e){
                        throw e;
                    }
                }

                private void sendAllByteCodesToHuffmanEncoderForCOuntingFrequencies() throws IOException{
                    try{
                        this.openOriginalInputStream();
                        int byteCode = this.readByteCode();
                        while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM) {
                            this.huffmanEncoder().incrementFrequencyOf(byteCOde);
                            byteCode = this.readByteCode();
                        }
                        this.closeOriginalInputStream();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private void sendAllByteCodesToHuffmanEncoderForCountingFrequencies() throws IOException{
                    try{
                        this.openOriginalInputStream();
                        int byteCode = this.readByteCode();
                        while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM){
                            this.huffmanEncoder().incrementFrequencyOf(byteCode);
                            byteCode = this.readByteCode();
                        }
                        this.closeOriginalInputStream();
                    }
                    catch(IOException e)
                        throw e;
                }

                private void sendAllBYteCodesToHuffmanEncoderForCOuntingFrequencies() throws IOException{
                    try{
                        this.openOriginalInputStream();
                        int byteCode = this.readByteCode();
                        while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM){
                            this.huffmanEncoder().incrementFrequencyOf(byteCode);
                            byteCode = this.readByteCode();
                        }
                        this.closeOriginalInputStream();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private void sendAllByteCodesToHuffmanEncoderForCountingFrequencies() throws IOException{
                    try{
                        this.openOriginalInputStream();
                        int byteCode = this.readByteCode();
                        while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM){
                            this.huffmanEncoder().incrementFrequencyOf(byteCode);
                            byteCode = this.readByteCode();
                        }
                        this.closeOriginalInputStream();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }
                /*
                final exam practice
                 */


    private boolean initOriginalFile(){
        AppView.outputLine("");
        AppView.outputLine("? 압축할 원본 파일의 경로와 이름을 입력하시오 : ");
        String filePath = AppView.inputFilePath();
        String fileName = AppView.inputFileName();
        String filePathAndName = filePath + "/" + fileName;                                                             //입력받은 path와 name을 통해 file의 경로를 완성
        this.setOriginalFile(new File(filePathAndName));                                                                //original file을 입력받은 file path로
        if(this.originalFile().exists()){
            return true;                                                                                                //입력된 파일이 존재하면 true
        }
        else{
            AppView.outputLine("! 오류 : 파일 (" + filePathAndName + ") 이 존재하지 않습니다.");                        //파일이 없다는 메시지 출력하고 false 반환.
            return false;
        }
    }


                /*
                final exam practice
                 */
                private boolean initOriginalFile(){
                    String filePAth = AppView.inputFilePath();
                    String fileName = AppView.inputFileName();
                    String pathAndName = filePAth + "/" + fileName;
                    this.setOriginalInputStream(new File(pathAndName));
                    if(this.originalFile().exist){
                        return true;
                    }
                    else{
                        return false;
                    }

                    private boolean initORiginalFile(){
                        String path = AppView.inputFilePath();
                        String name = AppView.inputFileName();
                        String pathAndName = path + "/" + name;
                        this.setOriginalFile(new File(pathAndName));
                        if(this.originalFile().exist())
                            return true;
                        else
                            return false;
                    }

                    private boolean initOriginalFile(){
                        String path = AppView.inputPath();
                        String name = AppView.inputFileName();
                        String absPath = path + "/" + name;
                        this.setOriginalFile(new File(absPath));
                        if(this.originalFile().exist())
                            return true;
                        else
                            return false;
                    }

                    private boolean initOriginalFile(){
                        String path = AppView.inputFilePath();
                        String name = AppView.inputFileName();
                        String abs = path + "/" + name;
                        this.setOriginalFile(new File(abs));
                        if(this.originalFile().exists())
                            return true;
                        else
                            return false;
                    }
                }
                /*
                final exam practice
                 */

    private void initCompressedFile(){
        AppView.outputLine("");
        String filePathAndName = this.originalFile().getAbsolutePath() + App_CONST.COMPRESSED_FILE_EXTENSION;           //압축시 입력받았던 originalFile의 경로/이름에 압축 확장자 상수를 추가해서 결과물 파일로 생성.
        this.setCompressedFile(new File(filePathAndName));
        if(this.compressedFile().exists()){                                                                             //결과 파일로 만든게 이미 존재하는 파일이면
            AppView.outputLine("! 경고 : 파일 (" + filePathAndName + ") 이 이미 존재합니다.");
            AppView.outputLine("- 압축 파일 이름을 다른것으로 바꾸어 처리합니다 : ");
            String compressedFilePathAndNameWithoutExtension = FilePathManager.getFilePathAndNameWithoutExtension(this.originalFile());     //original파일의 extension제거
            String originalFileExtension = FilePathManager.getFileExtension(this.originalFile());                                           //extension을 따로저장
            String compressedFilePathAndNameWithInfix = compressedFilePathAndNameWithoutExtension + App_CONST.COMPRESSED_FILE_INFIX;        //extension대신 infix
            String compressedFilePathAndName = null;
            int compressedFileSerialNumber = 0;
            do{
                compressedFileSerialNumber++;                                                                           //original path(without extension) + infix + serial num + original extension + compressed extension
                compressedFilePathAndName = compressedFilePathAndNameWithInfix + compressedFileSerialNumber + originalFileExtension + App_CONST.COMPRESSED_FILE_EXTENSION;
                this.setCompressedFile(new File(compressedFilePathAndName));
            }while(this.compressedFile().exists());
            AppView.outputLine("- 새로운 압축 파일의 이름은 " + compressedFilePathAndName + " 입니다. ");
        }
    }
                /*
                final exam practice
                 */
                private void initCompressedFile(){
                    String absPAth = this.originalFile().getAbsolutePath() + App_CONST.COMPRESSED_FILE_EXTENSION;
                    this.setCompressedFile(new File(absPAth));
                    if(this.compressedFile().exist()) {
                        String compressedFilePathAndNAmeWithoutExtension = FilePathManager.getFilePathAndNameWithoutExtension(this.originalFile());
                        String originalFileExtension = FilePathManager.getFileExtension(this.originalFile());
                        String compressedFileAndNAmeWithInfix = compressedFilePathAndNAmeWithoutExtension + App_CONST.COMPRESSED_FILE_INFIX;
                        String compressedFilePathAndName = null;
                        int compressedFileSerialNumber = 0;
                        do{
                            compressedFileSerialNumber++;
                            compressedFilePathAndName = compressedFileAndNAmeWithInfix + serialNumber + extension + App_CONST.COMPRESSED_FILE_EXTENSION;
                            this.setCompressedFile(new File(compressedFilePathAndName));
                        }while(this.compressedFile().exists());
                    }
                }

                private void initCompressedFile(){
                    String compressedFilePathAndName = this.originalFile().getAbsolutePath() + App_CONST.COMPRESSED_FILE_EXTENSION;
                    this.setCompressedFile(new File(compressedFilePathAndNAme));
                    if(this.compressedFile().exists()){
                        String compressedFilePathAndNameWithoutExtension = FilePathManager.getFilePathAndNameWithoutExtension((this.originalFile()));
                        String extension = FilePathManager.getFileExtension(this.originalFile());
                        String compressedFilePathAndNameWithInfix = compressedFilePathAndNameWithoutExtension + App_CONST.COMPRESSED_FILE_INFIX;
                        String compressedFilePathAndName = null;
                        int serialNumber = 0;
                        do{
                            serialNumber++;
                            compressedFilePathAndName = compressedFilePathAndNameWithInfix + serialNumber + extension + App_CONST.COMPRESSED_FILE_EXTENSION;
                            this.setCompressedFile(new File(compressedFilePathAndName));
                        }while(this.compressedFile().exists());
                    }
                }

                private void initCompressedFile(){
                    String filePathAndName = this.originalFile().getAbsolutePath() + App_CONST.COMPRESSED_FILE_EXTENSION;
                    this.setCompressedFile(new File(filePathAndName));
                    if(this.compressedFile().exists()){
                        String pathAndNameWithoutExtension = FilePathManager.getFilePathAndNameWithoutExtension(this.compressedFile());
                        String extension = FilePathManager.getFileExtension(this.originalFile());
                        String pathAndNameWithInfix = pathAndNameWithoutExtension + App_CONST.COMPRESSED_FILE_INFIX;
                        String pathAndName = null;
                        int serialNumber = 0;
                        do{
                            serialNumber++;
                            pathAndName = pathAndNameWithInfix + serialNumber + extension + App_CONST.COMPRESSED_FILE_EXTENSION;
                            this.setCompressedFile(new File(pathAndName));
                        }while(this.compressedFile().exists());
                    }
                }

                private void initCompressedFile(){
                    String fileWithExtension = this.originalFile().getAbsolutePath() + App_CONST.COMPRESSED_FILE_EXTENSION;
                    this.setCompressedFile(new File(fileWithExtension));
                    if(this.compressedFile())
                }
                /*
                final exam practice
                 */

    //허프만트리를 직렬화하여 압축될 파일에 기록
    private void writeSerializedHuffmanTree() throws IOException{
        int numberOfNodesInSerializedHuffmanTree = this.huffmanEncoder().numberOfNodesOfSerializedHuffmanTree();
        this.compressedOutputStream().writeShort((short)numberOfNodesInSerializedHuffmanTree);                                          //일단 노드의 개수가 기록되어야함
        for(int i = 0; i < numberOfNodesInSerializedHuffmanTree; i++){
            try{
                this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.LEFT_OF_NODE]);
                this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.RIGHT_OF_NODE]);
            }
            catch(IOException e){
                AppView.outputLine("! 오류 : [직렬화 된 허프만 트리]를 파일에 쓰는 작업을 실패하였습니다.");
                throw e;
            }
        }
    }
                /*
                final exam practice
                 */
                private void writeSerializedHuffmanTree() throws IOException{
                    int numberOfNodesInSerializedHuffmanTree = this.huffmanEncoder().numberOfNodesOfSerializedHuffmanTree();
                    this.compressedOutputStream().writeShort((short) numberOfNodesInSerializedHuffmanTree);
                    try {
                        for (int i = 0; i < numberOfNodesInSerializedHuffmanTree; i++) {
                            this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.LEFT_OF_NODE]);
                            this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.RIGHT_OF_NODE]);
                        }
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private void writeSerializedHuffmanTree() throws IOException{
                    int numberOfNodes = this.huffmanEncoder().numberOfNodesOfSerializedHuffmanTree();
                    this.compressedOutputStream().writeShort((short) numberOfNodes);
                    try{
                        for(int i = 0; i < numberOfNodes; i++){
                            this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.LEFT_OF_NODE]);
                            this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.RIGHT_OF_NODE]);
                        }
                        catch(IOException e){
                            throw e;
                        }
                    }
                }

                private void writeSerializedHuffmanTree() throws IOException{
                    int numberOfNodes = this.huffmanEncoder().numberOfNodesOfSerializedHuffmanTree();
                    this.compressedOutputStream().writeShort((short) numberOfNodes);
                    try{
                        for(int i = 0; i < numberOfNodes; i++){
                            this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.LEFT_OF_NODE]);
                            this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.RIGHT_OF_NODE]);
                        }
                    }
                    catch(IOException e)
                        throw e;
                }

                private void writeSerializedHuffmanTree() throws IOException{
                    int numberOfNodes = this.huffmanEncoder().numberOfNodesOfSerializedHuffmanTree();
                    this.compressedOutputStream().writeShort(numberOfNOdes);
                    try{
                        for(int i= 0; i < numberOfNodes; i ++){
                            this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.LEFT_OF_NODE]);
                            this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.RIGHT_OF_NODE]);
                        }
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private void writeSerializedHuffmanTree() throws IOException{
                    int numberOfNodes = this.huffmanEncoder().numberOFNodesOfSerializedHuffmanTree();
                    this.compressedOutputStream().writeShort(numberOfNodes);
                    try {
                        for (int i = 0; i < numberOfNodes; i++) {
                            this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.LEFT_OF_NODE]);
                            this.compressedOutputStream().writeShort(this.huffmanEncoder().serializedHuffmanTree()[i][Huffman_CONST.RIGHT_OF_NODE]);
                        }
                    }
                    catch(IOException e){
                        throw e;
                    }
                }
                /*
                final exam practice
                 */

    //압축된 데이터 비트 수를 압축될 파일에 기록
    private void writeNumberOfBitsOfCompressedData() throws IOException {
        try{
            this.compressedOutputStream().writeLong(this.huffmanEncoder().numberOfBitsOfCompressedData());
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : [압축된 데이터 비트 수]를 파일에 쓰는 작업을 실패하였습니다.");
            throw e;
        }
    }
                /*
                final exam practice
                 */
                private void writeNumberOfBitsOfCompressedData() throws IOException{
                    try{
                        this.compressedOutputStream().writeLong(this.huffmanEncoder().numberOfBitsOfCompressedData());
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private void writeNumberOfBitsOfCOmpressedData() throws IOException{
                    try{
                        this.compressedOutputStream().writeLong(this.huffmanEncoder().numberOfBitsOfCompressedData());
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private void writeNumberOfBitsOfCOmpressedData() throws IOException{
                    try{
                        this.compressedOutputStream().writeLong(this.huffmanEncoder().numberOfBitsOfCompressedData());
                    }
                    catch(IOException e) {
                        throw e;
                    }
                }

                private void writeNumberOFBitsOfCompressedData() throws IOException{
                    try{
                        this.compressedOutputStream().writeLong(this.huffmanEncoder().numberOfBitsOfCompressedData());
                    }
                    catch(IOException e){
                        throw e;
                    }
                }
                /*
                final exam practice
                 */

    private void openOriginalInputStream() throws FileNotFoundException{
        try{
            FileInputStream originalFileInputStream = new FileInputStream(this.originalFile());                         //오리지널 파일을 통해 스트림을 연다
            this.setOriginalInputStream(new ExtendedBufferedInputStream(originalFileInputStream));                      //열린스트림으로 originalFileInputStream set
        }
        catch(FileNotFoundException e) {
            AppView.outputLine("! 오류 : 원본 파일을 열 수 없습니다.");
            throw e;
        }
    }
                /*
                final exam practice
                 */
                private void openOriginalInputStream() throws IOException{
                    try{
                        FileInputStream originalFileInputStream = new FileInputStream(this.originalFile());
                        this.setOriginalFileInputStream(new ExtendedBufferedInputStream(originalFileInputStream));
                    }
                    catch(FileNotFoundException e) {
                        throw e;
                    }
                }

                private void openOrignalInputStream() throws FileNotFoundException{
                    try {
                        FileInputStream originalFileInputStream = new FileInputStream(this.originalFile());
                        this.setOriginalInputStream(new ExtendedBufferedInputStream(originalFileInputSTream));
                    }
                    catch(FileNotFoundException e){
                        throw e;
                    }
                }

                private void openOriginalInputStream() throws FileNotFoundException{
                    try{
                        FileInputStream originalFileInputStream = new FileInputStream(this.originalFile());
                        this.setOriginalInputStream(new ExtendedBufferedInputStream(originalFIleInputStream());
                    }
                    catch(FileNotFoundException e){
                        throw e;
                    }
                }

                private void openOriginalInputStream() throws FileNotFoundException{
                    try{
                        FileInputStream originalFileInputStream = new FileInputStream(this.originalFIle());
                        this.setOriginalInputStream(originalFileInputStream);
                    }
                    catch(FileNotFoundException e){
                        throw e;
                    }
                }

                private void openOriginalInputStream() throws FileNotFoundException{
                    try {
                        FileInputStream originalFileInputStream = new FileInputStream(this.originalFile());
                        this.setOriginalInputStream(originalFileInputStream);
                    }
                    catch(FileNotFoundException e)
                        throw e;
                }
                /*
                final exam practice
                 */

    private void openCompressedOutputStream() throws FileNotFoundException {
        try {
            FileOutputStream compressedFileOutputStream = new FileOutputStream(this.compressedFile());                  //압축결과 파일을 스트림으로 연다.
            this.setCompressedOutputStream(new ExtendedBufferedOutputStream(compressedFileOutputStream));               //열린 스트림으로 compressedFileOutputStream set
        } catch (FileNotFoundException e) {
            AppView.outputLine("! 오류 : 압축 파일을 열 수 없습니다.");
            throw e;
        }
    }
                /*
                final exam practice
                 */
                private void openCompressedOutputStream() throws FileNotFoundException{
                    try{
                        FileOutputStream compressedFileOutputStream = new FIleOutputStream(this.compressedFile());
                        this.setCompressedOutputStream(new ExtendedBufferedOutputStream(compressedOutputStream()));
                    } catch(FileNotFoundException e){
                        throw e;
                    }
                }

                private void openCompressedOutputStream() throws FileNotFoundException{
                    try{
                        FileOutputStream compressedFileOutputStream = new FileOutputStream(this.compressedFile());
                        this.setCompressedOutputStream(new ExtendedBufferedOutputStream(compressedFileOutputStream()));
                    }
                    catch(FileNotFoundException e){
                        throw e;
                    }
                }

                private void openCompressedOutputStream() throws FileNotFoundException{
                    try{
                        FileOutputStream compressedFileOutputStream = new FileOutputStream(this.compressedFile());
                        this.setCompressedOutputStream(new ExtendedBufferedOutputStream(compressedFileOutputStream));
                    }
                    catch(FileNotFoundException e){
                        throw e;
                    }
                }

                private void openCompressedOutputStream() throws IOException{
                    try{
                        FileOutputStream compressedFileOutputStream = new FileOutputStream(this.compressedFile());
                        this.setCompressedOutputStream(compressedFileOutputStream);
                    }
                    catch(FileNotFoundException e){
                        throw e;
                    }
                }
                /*
                final exam practice
                 */

    private void flushBits() throws IOException{
        try{
            this.bitOutputManager().flush();
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 마지막 남아있는 비트들을 쓰는 작업을 실패하였습니다.");
            throw e;
        }
    }
                /*
                final exam practice
                 */
                private void flushBits() throws IOException{
                    try{
                        this.bitOutputManager().flush();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private void flushBits() throw IOException{
                    try{
                        this.bitOutputManager().flush();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private void flushBits() throws IOException{
                    try{
                        this.bitOutputManager().flush();
                    }
                    catch(IOException e)
                        throw e;
                }

                private void flushBits() throws IOException{
                    try{
                        this.bitOutputManager().flush();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }
                /*
                final exam practice
                 */

    private void writeHuffmanCode(HuffmanCode huffmanCode) throws IOException{
        for(int i = 0; i < huffmanCode.length(); i++){                                                                    //huffman코드의 길이만큼 i를 늘려가며 반복
            try{
                this.bitOutputManager().writeBit(huffmanCode.bitAtIndex(i));                                            //인자로 입력받은 huffmanCode의 i번째 인덱스를 기록
            }
            catch(IOException e){
                AppView.outputLine("! 오류 : [허프만 코드]를 파일에 쓰는 작업을 실패하였습니다.");
            }
        }
    }
                /*
                final exam practice
                 */
                private void writeHuffmanCode(HuffmanCode huffmanCode) throws IOException{
                    try {
                        for (int i = 0; i < huffmanCode.length(); i++) {
                            this.bitOutputManager().writeBit(huffmanCode.bitAtIndex(i));
                        }
                    }
                    catch(IOException e){}
                }

                private void writeHuffmanCode(HuffmanCode huffmanCode) throws IOException{
                    try{
                        for(int i = 0; i < huffmanCode.length(); i++){
                            this.bitOutputManager().writeBit(huffmanCode.bitAtIndex(i));
                        }
                    }
                    catch(IOException e){}
                }

                private void writeHuffmanCode(HuffmanCode huffmanCode) throws IOException{
                    try{
                        for(int i = 0; i < huffmanCode.length(); i++){
                            this.bitOutputManager().writeBit(huffmanCode.bitAtIndex(i));
                        }
                    }
                    catch(IOException e){}
                }

                private void writeHuffmanCode(HuffmanCode huffmanCode) throws IOException{
                    try{
                        for(int i = 0; i < huffmanCode.length(); i++){
                            this.bitOutputManager().writeBit(huffmanCode.bitAtIndex(i));
                        }
                    }
                    catch(IOException e){}
                }

                private void writeHuffmanCode(HuffmanCode huffmanCode) throws IOException{
                    try{
                        for(int i = 0; i < huffmanCode.length(); i++){
                            this.bitOutputManager().writeBit(huffmanCode.bitAtIndex(i));
                        }
                    }
                    catch(IOException e){
                        throw e;
                    }
                }
                /*
                final exam practice
                 */

    private void writeAllCompressedBits() throws IOException{                                                           //byteCode를 읽고 huffmanCode로, writeHuffmanCode를 통해 파일에 작성
        try{
            int byteCode = this.readByteCode();
            while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM){                                                        //스트림의 끝 까지 오리지널 파일로부터 바이트코드를 계속해서 읽어들인다.
                HuffmanCode huffmanCode = this.huffmanEncoder().huffmanCodeOfByteCode(byteCode);                        //읽어드린 바이트코드는 허프만 인코더를 통해 허프만 코드로 변환
                this.writeHuffmanCode(huffmanCode);                                                                     //기록한다.
                byteCode = this.readByteCode();                                                                         //다시 바이트코드를 읽어냄
            }
            this.flushBits();
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 압축 파일에 비트 데이터 쓰기를 실패 하였습니다.");
            throw e;
        }
    }
                /*
                final exam practice
                 */
                private void writeAllCompressedBits() throws IOException{
                    try{
                        int byteCode = this.readByteCode();
                        while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM){
                            HuffmanCode huffmanCode = this.huffmanEncoder().huffmanCodeOfByteCode(byteCode);
                            this.writeHuffmanCode(huffmanCode);
                            byteCode = this.readByteCode();
                        }
                        this.flushBits();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private void writeAllCompressedBits() throws IOException{
                    try{
                        int byteCode = this.readByteCode();
                        while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM){
                            HuffmanCode huffmanCode = this.huffmanEncoder().huffmanCodeOfByteCode(byteCode);
                            this.writeHuffmanCode(huffmanCode);
                            byteCode = this.readByteCode();
                        }
                        this.flushBits();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }

                private void writeAllCompressedBits() throws IOException{
                    try{
                        int byteCode = this.readByteCode();
                        while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM) {
                            HuffmanCode huffmanCode = this.huffmanEncoder().huffmanCodeOfByteCode(byteCode);
                            this.writeHuffmanCode(huffmanCode);
                            byteCode = this.readByteCode();
                        }
                        this.flushBits();
                    }
                    catch(FileNotFoundException e){
                        throw e;
                    }
                }

                private void writeAllCompressedBits() throws IOException{
                    try{
                        int byteCode = this.readByteCode();
                        while(byteCode != FileIO_CONST.END_OF_INPUT_STREAM){
                            this.writeHuffmanCode(this.huffmanEncoder().huffmanCodeOfByteCode(byteCode));
                            byteCode = this.readByteCode();
                        }
                        this.flushBits();
                    }
                    catch(IOException e){
                        throw e;
                    }
                }
                /*
                final exam practice
                 */

    //스트림 닫기
    private void closeOriginalInputStream() throws IOException{
        try{
            this.originalInputStream().close();
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 원본 파일 닫기에 실패하였습니다.");
        }
    }
                /*
                final exam practice
                 */
                private void closeOriginalInputStream() throws IOException{
                    try{
                        this.originalInputStream().close();
                    }
                }
                /*
                final exam practice
                 */

    private void closeCompressedoutputStream() throws IOException{
        try{
            this.compressedOutputStream().close();
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 압축 파일 닫기에 실패하였습니다.");
        }
    }
                /*
                final exam practice
                 */
                private void closeCompressedOutputStream() throws IOException(){
                    try {
                        this.compressedOutputStream().close();
                    } catch (IOException e) {

                    }
                }
                /*
                final exam practice
                 */

    private void showStatistics(){                                                                                      //원본의 용량, 압축본의 용량, 그로부터 계산된 압축률을 보임
        AppView.outputLine("> 압축정보 : ");
        long originalFileSize = this.originalFile().length();
        long compressedFileSize = this.compressedFile().length();
        AppView.outputLine("- 원본 파일 : " + this.originalFile().getAbsolutePath() + " (" + originalFileSize + " Byte)");
        AppView.outputLine("- 압축 파일 : " + this.compressedFile().getAbsolutePath() + " (" + compressedFileSize + " Byte)");
        double compressionRatio = (double)compressedFileSize / (double)originalFileSize;
        AppView.outputLine("- 압축률 : " + String.format("%2.1f %%", compressionRatio * 100));
    }

    private void compress() throws IOException{
        this.setHuffmanEncoder(new HuffmanEncoder());

        this.sendAllByteCodesToHuffmanEncoderForCountingFrequencies();                                                  //압축할 데이터의 frequency들 측정한다.
        this.huffmanEncoder().buildHuffmanCode();                                                                       //frequency를 이용해 huffmancode를 만들어낸다.

        this.openCompressedOutputStream();                                                                              //입출력 스트림 open
        this.openOriginalInputStream();

        this.setBitOutputManager(new BitOutputManager(this.compressedOutputStream()));
        this.writeSerializedHuffmanTree();                                                                              //먼저 직렬화 시킨 허프만 트리부터 파일에 쓴 후
        this.writeNumberOfBitsOfCompressedData();                                                                       //압축될 비트 수를 쓰고

        this.writeAllCompressedBits();                                                                                  //압축 된 데이터를 쓴다.

        this.closeOriginalInputStream();                                                                                //입출력 스트림 close
        this.closeCompressedoutputStream();
    }
                /*
                final exam practice
                 */
                private void compress() throws IOException{
                    this.setHuffmanEncoder(new HuffmanEncoder());
                    this.sendAllByteCodesToHuffmanEncoderForCOuntingFrequencies();
                    this.huffmanEncoder().buildHuffmanCode();
                    this.openCompressedOutputStream();
                    this.openOriginalInputStream();
                    this.setBitOutputManager(new BitOutputManager(this.compressedOutputStream()));
                    this.writeSerializedHuffmanTree();
                    this.writeNumberOfBitsOfCOmpressedData();
                    this.writeAllCompressedBits();
                    this.closeOriginalInputStream();
                    this.closeCompressedoutputStream();
                }

                private void compress() throws IOException{
                    this.setHuffmanEncode(new HuffmanEncoder());
                    this.sendAllByteCodesToHuffmanEncoderForCOuntingFrequencies();
                    this.huffmanEncoder().buildHuffmanCode();
                    this.openOriginalInputStream();
                    this.openCompressedOutputStream();
                    this.setBitOutputManager(new BitOutputManager(this.compressedOutputStream()));
                    this.writeSerializedHuffmanTreE();
                    this.writeNumberOfBitsOfCOmpressedData();
                    this.writeAllCompressedBits();
                    this.closeOriginalInputStream();
                    this.closeCompressedOutputStream();
                }

                private void compress() throws IOException{
                    this.setHuffmanEncoder(new HuffmanEncoder());
                    this.sendAllByteCodesToHuffmanEncoderForCountingFrequencies();
                    this.huffmanEncoder().buildHuffmanCode();
                    this.openOriginalInputStream();
                    this.openCompressedOutputStream();
                    this.setBitOutputManager(new BitOutputManager(this.compressedOutputStream()));
                    this.writeSerializedHuffmanTree();
                    this.writeNumberOfBitsOfCOmpressedData();
                    this.writeAllCompressedBits();
                    this.closeOriginalInputStream();
                    this.closeCompressedoutputStream();
                }

                private void compress() throws IOException{
                    this.sertHuffmanEncoder(new HuffmanEncoder());
                    this.openCompressedOutputStream();
                    this.openOriginalInputStream();
                    this.sendAllByteCodesToHuffmanEncoderForCOuntingFrequencies();
                    this.huffmanEncoder().buildHuffmanCode();
                    this.setBitOutputManager(new BitOutputManager(this.compressedOutputStream()));
                    this.writeSerializedHuffmanTree();
                    this.writeNumberOfBitsOfCOmpressedData();
                    this.writeAllCompressedBits();
                    this.closeOriginalInputStream();
                    this.closeCompressedoutputStream();
                }
                /*
                final exam practice
                 */

    protected void run(){
        if(this.initOriginalFile()){                                                                                    //original file 가져오는데 성공해야함.
            this.initCompressedFile();
            try{                                                                                                        //original file 을 압축. 만약 IOException 발생하면 압축에 문제가 있는 것.
                this.compress();
                AppView.outputLine("");
                AppView.outputLine("! 압축을 성공적으로 마쳤습니다.");
                this.showStatistics();
            }
            catch(IOException e){
                AppView.outputLine("! 오류 : 압축을 실행하는 동안에 파일 처리 오류가 발생하였습니다.");
            }
        }
    }
}
