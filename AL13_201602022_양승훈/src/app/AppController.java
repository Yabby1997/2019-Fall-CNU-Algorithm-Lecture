package app;

public class AppController {

	//압축과 해제, 검증을 담당할 객체들
	private CompressionController _compressionController;
	private DecompressionController _decompressionController;
	private ValidationController _validationController;

	private CompressionController compressionController(){																//Lazy Instantiation 방식으로 getter 구현
		if(this._compressionController == null){
			this._compressionController = new CompressionController();
		}
		return this._compressionController;
	}

	private DecompressionController decompressionController(){
		if(this._decompressionController == null){
			this._decompressionController = new DecompressionController();
		}
		return this._decompressionController;
	}

	private ValidationController validationController(){
		if(this._validationController == null){
			this._validationController = new ValidationController();
		}
		return this._validationController;
	}

	private MainMenu selectMenu(){
			AppView.outputLine("");
			AppView.output("? 해야 할 작업을 선택 하시오 (압축 = 1, 해제 = 2, 검증 = 3, 종료 = 4) : ");
			try{																											//메뉴 선택에서 입력 예외가 발생하면 처리하기위해 try catch
				int selectedMenuNumber = AppView.inputInteger();
				MainMenu selectedMenuValue = MainMenu.value(selectedMenuNumber);
			if(selectedMenuValue == MainMenu.ERROR){
				AppView.outputLine("! 오류 : 작업 선택이 잘못 되었습니다. (잘못된 번호 : " + selectedMenuNumber + " )");
			}
			return selectedMenuValue;
		}
		catch(NumberFormatException e){
			AppView.outputLine("! 오류 : 입력된 메뉴 번호가 정수형 숫자가 아닙니다.");
			return MainMenu.ERROR;
		}
	}

	public void run(){
		AppView.outputLine("<<< Huffman Code 를 이용한 파일 압축/해제 프로그램을 시작합니다. >>>");
		MainMenu selectedManuValue = this.selectMenu();
		while(selectedManuValue != MainMenu.END){
			switch(selectedManuValue){
				case COMPRESS :
					this.compressionController().run();
					break;
				case DECOMPRESS :
					this.decompressionController().run();
					break;
				case VALIDATE :
					this.validationController().run();
					break;
				default :
					break;
			}
			selectedManuValue = this.selectMenu();
		}
		AppView.outputLine("");
		AppView.outputLine("<<< Huffman Code 를 이용한 파일 압축/해제 프로그램을 종료합니다. >>>");
	}
}
