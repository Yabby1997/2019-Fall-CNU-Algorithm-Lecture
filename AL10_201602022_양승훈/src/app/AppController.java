package app;

import experiment.ExperimentManager;
import experiment.ExperimentManagerForThreeSorts;
import experiment.ListOrder;

public class AppController {

	private ExperimentManagerForThreeSorts _managerForThreeSorts;

	//constructor
	public AppController(){
		this.setManagerForThreeSorts(null);
	}

	//getter/setter
	private ExperimentManagerForThreeSorts managerForThreeSorts() {
		return this._managerForThreeSorts;
	}

	private void setManagerForThreeSorts(ExperimentManagerForThreeSorts newManagerForThreeSorts) {
		this._managerForThreeSorts = newManagerForThreeSorts;
	}

	private void showTableTitle(ListOrder anOrder){
		AppView.outputLine("> " + anOrder.orderName() + " 데이터에 대한 측정 : ");
	}

	//테이블의 머리부분을 출력
	private void showTableHeadForThreeSorts(){
		AppView.outputLine(String.format("%8s", " ") + String.format("%26s", "<Insertion Sort>") + String.format("%26s", "  <Quick Sort>   ") + String.format("%26s", "  <Heap Sort>    "));
		AppView.outputLine(String.format("%7s", "데이터 크기") + String.format("%26s", "Measure (Estimate)") + String.format("%26s", "Measure (Estimate)") +String.format("%26s", "Measure (Estimate)"));
	}

	//테이블의 데이터 부분을 출력
	private void showTableContentForThreeSorts(){
		for(int iteration = 0; iteration < this.managerForThreeSorts().parameterSetForMeasurement().numberOfIteration(); iteration++){		//반복수만큼 반복하며 출력
			int startingSize = this.managerForThreeSorts().parameterSetForMeasurement().startingSize();
			int incrementSize = this.managerForThreeSorts().parameterSetForMeasurement().incrementSize();
			int sortingSize = startingSize + (incrementSize * iteration);
			AppView.outputLine(
					"[" + String.format("%7d", sortingSize) + "]" +
							String.format("%15d", this.managerForThreeSorts().measurementForInsertionSortAt(iteration)) +
							" (" + String.format("%8d", this.managerForThreeSorts().estimationForInsertionSortAt(iteration)) + " )" +
							String.format("%15d", this.managerForThreeSorts().measurementForQuickSortAt(iteration)) +
							" (" + String.format("%8d", this.managerForThreeSorts().estimationForQuickSortAt(iteration)) + " )" +
							String.format("%15d", this.managerForThreeSorts().measurementForHeapSortAt(iteration)) +
							" (" + String.format("%8d", this.managerForThreeSorts().estimationForHeapSortAt(iteration)) + " )"
			);
		}
	}

	//앞서 만든 출력함수들을 이용해 결과를 보여주는 함수
	private void showResultTableForThreeSorts(ListOrder anOrder){
		this.showTableTitle(anOrder);
		this.showTableHeadForThreeSorts();
		this.showTableContentForThreeSorts();
		AppView.outputLine("");
	}

	//인자로 정렬방식을 전달해 측정을하고 그 결과를 보임.
	private void measureAndShow(ListOrder anOrder){
		this.managerForThreeSorts().performExperiment(anOrder);
		this.showResultTableForThreeSorts(anOrder);
	}

	public void run(){
		AppView.outputLine("<<< 정렬 성능 비교 프로그램을 시작합니다 >>>");
		AppView.outputLine("");
		{
			AppView.outputLine(">> 3가지 정렬의 성능 비교 : 삽입, 퀵, 힙 <<");
			this.setManagerForThreeSorts(new ExperimentManagerForThreeSorts());
			this.managerForThreeSorts().prepareExperiment(null);
			this.measureAndShow(ListOrder.Random);
			this.measureAndShow(ListOrder.Ascending);
			this.measureAndShow(ListOrder.Descending);
		}
		AppView.outputLine("<<< 정렬 성능 비교 프로그램을 종료합니다 >>>");
	}
}
