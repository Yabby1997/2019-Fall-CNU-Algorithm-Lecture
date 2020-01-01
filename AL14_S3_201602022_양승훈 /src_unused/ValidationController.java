package view;

import fileIO.FileIO_CONST;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;

public class ValidationController {
    private static final boolean DEBUG_MODE = false;                                                                    //디버그메시지
    private static void showDebugMessage(String aMessage) {
        if(ValidationController.DEBUG_MODE) {
            AppView.outputLine("[debug] (ValidationController) " + aMessage);
        }
    }

    private File _firstFile;                                                                                            //비교할 두 파일, 첫번째 파일의 경로, 두 파일의 입력스트림을 선언
    private File _secondFile;

    private String _firstFilePath;

    private BufferedInputStream _firstInputStream;
    private BufferedInputStream _secondInputStream;

    //constructor
    protected ValidationController(){}

    //getter/setter
    private File firstFile(){
        return this._firstFile;
    }

    private void setFirstFile(File newFirstFile){
        this._firstFile = newFirstFile;
    }

    private File secondFile(){
        return this._secondFile;
    }

    private void setSecondFile(File newSecondFile){
        this._secondFile = newSecondFile;
    }

    private String firstFilePath(){
        return this._firstFilePath;
    }

    private void setFirstFilePath(String newFirstFilePath){
        this._firstFilePath = newFirstFilePath;
    }

    private BufferedInputStream firstInputStream(){
        return this._firstInputStream;
    }

    private void setFirstInputStream(BufferedInputStream newFirstInputStream){
        this._firstInputStream = newFirstInputStream;
    }

    private BufferedInputStream secondInputStream(){
        return this._secondInputStream;
    }

    private void setSecondInputStream(BufferedInputStream newSecondInputStream){
        this._secondInputStream = newSecondInputStream;
    }
    //end of getter/setter

    private boolean initFirstFile(){                                                                                    //첫번째 파일의 경로와 이름을 입력받는다. 이 때 입력받은 주소는 두번째 파일에서 재활용할 수 있도록 저장한다.
        AppView.outputLine("");
        AppView.outputLine("? 첫번째 파일의 경로와 이름을 입력하시오 : ");
        this.setFirstFilePath(AppView.inputFilePath());
        String fileName = AppView.inputFileName();
        String filePathAndName = this.firstFilePath() + "/" + fileName;
        this.setFirstFile(new File(filePathAndName));
        if(this.firstFile().exists()){
            return true;
        }
        else{
            AppView.outputLine("! 오류 : 파일 (" + filePathAndName + ") 이 존재하지 않습니다.");
            return false;
        }
    }

    private boolean initSecondFile(){                                                                                   //두번째 파일의 경로와 이름을 입력받는다. 첫번째 파일의 경로를 재활용할 수 있다.
        AppView.outputLine("");
        AppView.outputLine("? 두번째 파일의 경로와 이름을 입력하시오 : ");
        String filePath = AppView.inputAnswerForUsingSamePath() ? this.firstFilePath() : AppView.inputFilePath();       //같은 경로를 사용할지를 물어 그렇다면 같은경로를, 아니라면 새로운 경로 입력을 받도록 한다.
        String fileName = AppView.inputFileName();
        String filePathAndName = filePath + "/" + fileName;
        this.setSecondFile(new File(filePathAndName));
        if(this.secondFile().exists()){
            return true;
        }
        else{
            AppView.outputLine("! 오류 : 파일 (" + filePathAndName + ") 이 존재하지 않습니다.");
            return false;
        }
    }

    private BufferedInputStream openInputStream(File file) throws FileNotFoundException {                               //입력스트림을 연다. 파일을 인자로받는다. 비교할 두 파일의 스트림을 여는데 이용한다.
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            return new BufferedInputStream(fileInputStream);
        }
        catch(FileNotFoundException e){
            AppView.outputLine("! 오류 : 파일 (" + file.getAbsolutePath() + ") 이 존재하지 않습니다.");
            throw e;
        }
    }

    private void closeInputStream(BufferedInputStream bufferedInputStream, File inputFile) throws IOException{          //입력스트림을 닫는다.
        try{
            bufferedInputStream.close();
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 파일 (" + inputFile.getAbsolutePath() + ") 닫기에 실패하였습니다.");
            throw e;
        }
    }

    private int readByteFromFirstInputStream() throws IOException{                                                      //첫번째 파일로부터 바이트 단위만큼 읽는다.
        try{
            return this.firstInputStream().read();
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 첫번째 파일로부터 읽어오기에 실패하였습니다.");
            throw e;
        }
    }

    private int readByteFromSecondInputStream() throws IOException{                                                     //두번째 파일로부터 바이트 단위만큼 읽는다.
        try{
            return this.secondInputStream().read();
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 두번째 파일로부터 읽어오기에 실패하였습니다.");
            throw e;
        }
    }

    private boolean validate() throws IOException{
        ValidationController.showDebugMessage("validate called");
        boolean isValid = false;
        this.setFirstInputStream(this.openInputStream(this.firstFile()));
        this.setSecondInputStream(this.openInputStream(this.secondFile()));                                             //파일을 열고 스트림을 설정한다.
        int firstReadByte = this.readByteFromFirstInputStream();                                                        //각 파일로부터 일단 바이트단위만큼 읽는다.
        int secondReadByte = this.readByteFromSecondInputStream();
        while (firstReadByte == secondReadByte) {                                                                       //읽어들인 두 바이트가 다르다면 valid하지 않다.
            if (firstReadByte == FileIO_CONST.END_OF_INPUT_STREAM) {                                                    //두 바이트가 같은데 거기다 문자열의 끝이라면 끝까지 동일한것이므로 반복을 멈추고 true를 반환한다.
                isValid = true;
                break;
            }
            firstReadByte = this.readByteFromFirstInputStream();                                                        //그게 아니라면 다음 비교를 위해 추가로 한바이트씩 더 읽는다.
            secondReadByte = this.readByteFromSecondInputStream();
        }
        this.closeInputStream(this.firstInputStream(), this.firstFile());
        this.closeInputStream(this.secondInputStream(), this.secondFile());
        return isValid;
    }

    protected void run(){
        while(!this.initFirstFile()){};
        while(!this.initSecondFile()){};
        try {
            if (this.validate()) {
                AppView.outputLine("> 두 파일의 내용은 동일합니다.");
            } else {
                AppView.outputLine("> 두 파일의 내용은 동일하지 않습니다.");
            }
        }
        catch(IOException e){
            AppView.outputLine("! 오류 : 압축을 실행하는 동안에 파일 처리 오류가 발생하였습니다.");
        }
    }
}
