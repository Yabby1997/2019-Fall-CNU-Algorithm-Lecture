package app;
import java.util.Scanner;

public final class AppView {		//입출력을 담당하는 클래
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static final boolean DEBUG_MODE = true;						//디버그 메시지 출력 여부를 나타내는 상수 
	
	private AppView() {}												//Java에는 static class 개념이 없다. 대신 생성자를 private으로 정의해서 다른 클래스에서 호출할 수 없도록 해주면 된다. 
	
	public static void outputLine(String aString) {
		System.out.println(aString);
	}
	
	public static void output(String aString){
		System.out.print(aString);;
	}
	
	public static void outputDebugMessage(String aString) {				//상수 DEBUG_MODE가 참값을 가질때만 출력하도록 함. 
		if(DEBUG_MODE) {
			System.out.print(aString);
		}
	}

	public static int inputInteger() throws NumberFormatException{
		return Integer.parseInt(AppView.scanner.next());																//scanner를 통해 들어온것을 정수로 변환해 반환해줌
	}

	public static String inputString(){
		return AppView.scanner.next();																					//문자열 입력
	}

	public static String inputFileName(){
		AppView.outputLine("? 파일 이름을 입력하시오 : ");
		return AppView.scanner.next();
	}

	public static String inputFilePath(){
		AppView.outputLine("? 파일 경로를 입력하시오 (현재 폴더를 사용하려면 '.'을 입력하시오) : ");
		return AppView.scanner.next();
	}

	public static boolean inputAnswerForUsingSamePath(){
		AppView.outputLine("? 두번째 파일의 경로를 첫번째 파일의 경로와 동일한 것으로 사용하겠습니까?");
		AppView.output("  동일한 경로를 사용하려면 'Y'나 'y'를 치고, 다른 경로를 사용하려면 공백이 아닌 다른 아무 키나 치시오 :");
		char answer = AppView.scanner.next().charAt(0);
		return (answer == 'Y' || answer =='y');																			//Y나 y가 입력되었을때만 참을, 아닌경우엔 false를 반환하도록 한다.
	}
}
