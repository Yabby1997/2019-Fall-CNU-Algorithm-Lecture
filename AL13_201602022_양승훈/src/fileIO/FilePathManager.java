package fileIO;

import java.io.File;

public class FilePathManager {                                                                                   //확장자를 반환해주는 함수
    public static String getFileExtension(File file){
        String fileName = file.getName();
        int lastIndexOf = fileName.lastIndexOf(".");                                                                //.이 등장하는 마지막 인덱스를 반환하는 함수
        if(lastIndexOf == -1){
            return "";                                                                                                  // -1이 반환되었다면 없는것
        }
        else{
            return fileName.substring(lastIndexOf);                                                                     //있다면 마지막 . 이후의 것을 반환 -> 확장자 반환
        }
    }

    public static String getFilePathAndNameWithoutExtension(File file){                                                 //확장자를 제외한 경로와 이름을 반환해주는 함수
        String filePathAndName = file.getAbsolutePath();
        int lastIndexOf = filePathAndName.lastIndexOf(".");
        if(lastIndexOf == -1){
            return filePathAndName;                                                                                     //마찬가지로 extension이 없다면 그 자체가 경로와 이름
        }
        else{
            return filePathAndName.substring(0, lastIndexOf);                                                           //0부터 lastIndexOf까지, 즉 extension을 제외한 부분 반환
        }
    }
}
