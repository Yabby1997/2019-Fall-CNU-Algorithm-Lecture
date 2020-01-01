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
}
