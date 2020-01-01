
public class Main_AL02_201602022 {

	public static void main(String[] args) {					//MVC모델에 따라 main함수에서는 AppController의 생성과 실행만 담당한다. 실질적인 기능은 AppController와 AppView등에서 구현.
		AppController appController = new AppController();
		appController.run();
	}

}
