import java.util.Scanner;

public final class AppView {		//입출력을 담당하는 클래
	
	private static Scanner scanner = new Scanner(System.in);
	
	private AppView() {}												//Java에는 static class 개념이 없다. 대신 생성자를 private으로 정의해서 다른 클래스에서 호출할 수 없도록 해주면 된다. 
	
	public static void outputLine(String aString) {
		System.out.println(aString);
	}
	
	public static void output(String aString){
		System.out.print(aString);;
	}
	
	public static int inputNumberOfVertices() {
		int numberOfVertices;
		String scannedToken;
		while(true) {
			AppView.output("? vertex 수를 입력하시오 : ");
			scannedToken = AppView.scanner.next();						//scanner를 통해 입력된 string, scannedToken에 저장.
			try {
				numberOfVertices = Integer.parseInt(scannedToken);		//scan된 scannedToken을 int로 변환.
				return numberOfVertices;								//문제 없다면 변환된 numberOfVertices반환.
			}
			catch(NumberFormatException e){
				AppView.outputLine("(오류) vertex 수 입력에 오류가 있습니다 : " + scannedToken);	//문제 발생시 오류발생 메시지와 함께 입력된 scannedToken을 보여준다.
				//마찬가지로 static함수 호출할때는 this를 사용하면 안된다. this는 객체를 가리키고, AppView객체는 사용하지 않는다.
			}
		}
	}
	
	public static int inputNumberOfEdges() {							//inputNumberOfVertices와 동일한 방식으로 edge를 입력받는다.
		int numberOfEdges;
		String scannedToken;
		while(true) {
			AppView.output("? edge 수를 입력하시오 : ");
			scannedToken = AppView.scanner.next();
			try {
				numberOfEdges = Integer.parseInt(scannedToken);
				return numberOfEdges;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(오류) edge 수 입력에 오류가 있습니다 : " + scannedToken);
			}
		}
	}
	
	public static int inputTailVertex() {								//동일한 방식으로 tailVertex를 입력받는다.
		int tailVertex;
		String scannedToken;
		while(true) {
			AppView.output("? tail vertex 를 입력하시오 : ");
			scannedToken = AppView.scanner.next();
			try {
				tailVertex = Integer.parseInt(scannedToken);
				return tailVertex;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(오류) tail vertex 입력에 오류가 있습니다 : " + scannedToken);
			}
		}
	}
	
	public static int inputHeadVertex() {								//동일한 방식으로 headVertex를 입력받는다.
		int headVertex;
		String scannedToken;
		while(true) {
			AppView.output("? head vertex 를 입력하시오 : ");
			scannedToken = AppView.scanner.next();
			try {
				headVertex = Integer.parseInt(scannedToken);
				return headVertex;
			}
			catch(NumberFormatException e) {
				AppView.outputLine("(오류) head vertex 입력에 오류가 있습니다 : " + scannedToken);
			}
		}
	}
}
