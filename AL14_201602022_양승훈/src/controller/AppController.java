package controller;

import view.AppView;
import model.PairOfPoints;
import model.Point;
import model.PointSet;
import model.FindClosestPair;

public class AppController {
	private PointSet _pointSet;
	private FindClosestPair _findClosestPair;

	public AppController(){
		this.setPointSet(null);
		this.setFIndClosestPair(new FindClosestPair());
	}

	private PointSet pointSet(){
		return this._pointSet;
	}

	private void setPointSet(PointSet newPointSet){
		this._pointSet = newPointSet;
	}

	private FindClosestPair findClosestPair(){
		return this._findClosestPair;
	}

	private void setFIndClosestPair(FindClosestPair newFindClosestPair){
		this._findClosestPair = newFindClosestPair;
	}

	private boolean doesContinueToSolve(){
		AppView.outputLine("");
		AppView.output("?? 문제를 풀려면 'Y' 또는 'y', 아니면 다른 아무거나 치시오 : ");
		char answer = AppView.inputChar();
		return ((answer == 'Y') || (answer == 'y'));
	}

	private boolean inputPointSet(){
		int numberOfPoints = this.inputNumberOfPoints();
		this.setPointSet(new PointSet(numberOfPoints));

		for(int count = 0; count < numberOfPoints; count++){
			Point point = this.inputPoint();
			if(!this.pointSet().add(point)){
				AppView.outputLine("[오류] 입력 받은 점을 점의 집합에 추가하기를 실패하였습니다.");
				return false;
			}
		}
		return true;
	}

	private int inputNumberOfPoints(){
		do{
			AppView.output("? 점의 개수를 입력하시오 : ");
			try{
				int numberOfPoints = AppView.inputInt();
				if(numberOfPoints < 2){
					AppView.outputLine("[오류] 점의 개수는 2개 이상이어야 합니다.");
				}
				else{
					return numberOfPoints;
				}
			}
			catch(NumberFormatException e){
				AppView.outputLine("[오류] 올바른 숫자가 입력되지 않았습니다.");
			}
		}while(true);
	}

	private Point inputPoint(){
		Point point = new Point();
		AppView.outputLine("");
		AppView.outputLine("! 점의 (X, Y)좌표를 차례로 입력해야 합니다 : ");
		point.x = this.inputCoordinateValue("X");
		point.y = this.inputCoordinateValue("Y");
		return point;
	}

	private int inputCoordinateValue(String coordinateName){
		do{
			AppView.output("? " + coordinateName + "좌표 값을 입력하시오 : ");
			try{
				return AppView.inputInt();
			}
			catch(NumberFormatException e){
				AppView.outputLine("[오류] 올바른 숫자가 입력되지 않았습니다.");
			}
		}while(true);
	}

	private void showPoint(Point point){
		AppView.output("(" + point.x + ", " + point.y + ")");
	}

	private void showPointSet(){
		AppView.outputLine("");
		AppView.outputLine("! 점들의 집합입니다 : ");

		for(int count = 0; count < this.pointSet().size(); count++){
			this.showPoint(this.pointSet().elementAt(count));
			AppView.outputLine("");
		}
	}

	public void showPairOfPoints(PairOfPoints pair){
		AppView.output("<");
		this.showPoint(pair.firstPoint());
		AppView.output(", ");
		this.showPoint(pair.secondPoint());
		AppView.output(">");
	}

	private void showClosestPair(PairOfPoints closestPair, String explanation){
		AppView.output("! " + explanation + " : ");
		this.showPairOfPoints(closestPair);
		AppView.outputLine(String.format(" Distance = %d ", closestPair.distance()));
	}

	public void run () {
		AppView.outputLine("<<< 최단거리 쌍 찾기를 시작합니다 >>>");
		while(this.doesContinueToSolve()){
			AppView.outputLine("");
			AppView.outputLine("[문제 풀이를 시작합니다]");
			if(!this.inputPointSet()){
				AppView.outputLine ("[오류] 점들이 정상적으로 입력되지 않았습니다.");
			}
			else{
				this.showPointSet();
				PairOfPoints closestPairByDivideAndConquer = this.findClosestPair().solveByDivideAndConquer(this.pointSet());
				this.showClosestPair(closestPairByDivideAndConquer, "Divide-and-Conquer 방식으로 찾은 최단거리 쌍");
				PairOfPoints closestPairComparingAllPairs = this.findClosestPair().solveByComparingAllPairs(this.pointSet());
				this.showClosestPair(closestPairComparingAllPairs, "모든 쌍의 거리를 비교하여 찾은 최단거리 쌍");
			}
		}
		AppView.outputLine("");
		AppView.outputLine("<<< 최단거리 쌍 찾기를 종료합니다 >>>");
	}
}
