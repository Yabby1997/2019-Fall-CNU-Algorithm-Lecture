package app;

import experiment.ExperimentManager;
import experiment.ExperimentManagerForThreeSorts;
import experiment.ExperimentManagerForQuickSorts;
import experiment.ExperimentManagerForQuickSortWithInsertionSort;
import experiment.ListOrder;

public class AppController {

	private ExperimentManagerForThreeSorts _managerForThreeSorts;
	private ExperimentManagerForQuickSorts _managerForQuickSorts;
	private ExperimentManagerForQuickSortWithInsertionSort _managerForQuickSortWithInsertionSort;

	//getter/setter
	private ExperimentManagerForThreeSorts managerForThreeSorts() {
		return this._managerForThreeSorts;
	}

	private void setManagerForThreeSorts(ExperimentManagerForThreeSorts newManagerForThreeSorts) {
		this._managerForThreeSorts = newManagerForThreeSorts;
	}

	private ExperimentManagerForQuickSorts managerForQuickSorts(){
		return this._managerForQuickSorts;
	}

	private void setManagerForQuickSorts(ExperimentManagerForQuickSorts newManagerForQuickSorts){
		this._managerForQuickSorts = newManagerForQuickSorts;
	}

	private ExperimentManagerForQuickSortWithInsertionSort managerForQuickSortWithInsertionSort(){
		return this._managerForQuickSortWithInsertionSort;
	}

	private void setManagerForQuickSortWithInsertionSort(ExperimentManagerForQuickSortWithInsertionSort newManagerForQuickSortWithInsertionSort){
		this._managerForQuickSortWithInsertionSort = newManagerForQuickSortWithInsertionSort;
	}
	private void showTableTitle(ListOrder anOrder){
		AppView.outputLine("> " + anOrder.orderName() + " 데이터에 대한 측정 : ");
	}

	//테이블의 머리부분을 출력
	private void showTableHeadForThreeSorts(){
		AppView.outputLine(String.format("%8s", " ") + String.format("%26s", "<Insertion Sort>") + String.format("%26s", "  <Quick Sort>   ") + String.format("%26s", "  <Heap Sort>    "));
		AppView.outputLine(String.format("%7s", "데이터 크기") + String.format("%26s", "Measure (Estimate)") +
				String.format("%26s", "Measure (Estimate)") +
				String.format("%26s", "Measure (Estimate)")
		);
	}

	//3가지 정렬이 아닌 quicksort에 대한 머리부분 출력
	private void showTableHeadForQuickSorts(){
		AppView.outputLine(String.format("%7s ", "데이터 크기") + String.format("%17s", "<Pivot Left>") +
				String.format("%17s", "<Pivot Mid>") +
				String.format("%17s", "<Pivot Median>") +
				String.format("%17s", "<Pivot Random>") +
				String.format("%17s", "<Insertion Sort>")
		);
	}

	//insertion sort에 대한 머리부분
	private void showTableHeadForQuickSortWithInsertionSort(){
		AppView.output(String.format("%7s ", "데이터 크기") + String.format("%7s ", "<Pivot Random>"));
		int numberOfIteration = this.managerForQuickSortWithInsertionSort().parameterSetForMaxSizeOfInsertionSort().numberOfIteration();
		for(int iterationOfMaxSize = 0; iterationOfMaxSize < numberOfIteration; iterationOfMaxSize++){											//시작크기, 증가크기, 최대크기를 각각 가져옴
			int startingMaxSize = this.managerForQuickSortWithInsertionSort().parameterSetForMaxSizeOfInsertionSort().startingSize();
			int incrementSizeOfMaxSize = this.managerForQuickSortWithInsertionSort().parameterSetForMaxSizeOfInsertionSort().incrementSize();
			int maxSortingSize = startingMaxSize + incrementSizeOfMaxSize * iterationOfMaxSize;
			AppView.output(String.format("  <Size%3d>", maxSortingSize));																	//각 반복에서 최대 어디까지 insertion sort를 할 것인지 출력
		}
		AppView.outputLine("");
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

	//quickSort 데이터 부분을 출력. 예측값 없이 측정값만 나옴
	private void showTableContentForQuickSorts(){
		for(int iteration = 0; iteration < this.managerForQuickSorts().parameterSetForMeasurement().numberOfIteration(); iteration++){		//반복수만큼 반복하며 출력
			int startingSize = this.managerForQuickSorts().parameterSetForMeasurement().startingSize();
			int incrementSize = this.managerForQuickSorts().parameterSetForMeasurement().incrementSize();
			int sortingSize = startingSize + (incrementSize * iteration);
			AppView.outputLine(
					"[" + String.format("%7d", sortingSize) + "]" +
							String.format("%17d", this.managerForQuickSorts().measurementForQuickSortByPivotLeftAt(iteration)) +
							String.format("%17d", this.managerForQuickSorts().measurementForQuickSortByPivotMidAt(iteration)) +
							String.format("%17d", this.managerForQuickSorts().measurementForQuickSortByPivotMedianAt(iteration)) +
							String.format("%17d", this.managerForQuickSorts().measurementForQuickSortByPivotRandomAt(iteration)) +
							String.format("%17d", this.managerForQuickSorts().measurementForQuickSortWithInsertionSortAt(iteration))
			);
		}
	}

	//insertion sort 데티어 부분 출력
	private void showTableContentForQuickSortWithInsertionSort(){
		int numberOfIteration = this.managerForQuickSortWithInsertionSort().parameterSetForMeasurement().numberOfIteration();
		for(int iteration = 0; iteration < numberOfIteration; iteration++){
			int startingSize = this.managerForQuickSortWithInsertionSort().parameterSetForMeasurement().startingSize();
			int incrementSize = this.managerForQuickSortWithInsertionSort().parameterSetForMeasurement().incrementSize();
			int sortingSize = startingSize + (incrementSize * iteration);
			AppView.output("[" + String.format("%7d", sortingSize) + "]");										//계산된 정렬할 데이터의 사이즈 출력부분
			AppView.output(String.format("%14d", this.managerForQuickSortWithInsertionSort().measurementForQuickSortByPivotRandomAt(iteration)));	//랜덤으로 정렬한 성능 출력부분

			int numberOfIterationOfMaxSize = this.managerForQuickSortWithInsertionSort().parameterSetForMaxSizeOfInsertionSort().numberOfIteration();
			for(int iterationOfMaxSize = 0; iterationOfMaxSize < numberOfIterationOfMaxSize; iterationOfMaxSize++){
				AppView.output(String.format("%11d", this.managerForQuickSortWithInsertionSort().measurementForQuickSortWithInsertionSortAt(iterationOfMaxSize, iteration)));	//삽입정렬의 최대사이즈를 늘려가며 정렬된 성능 출력 부분
			}
			AppView.outputLine("");
		}
	}

	//앞서 만든 출력함수들을 이용해 결과를 보여주는 함수
	private void showResultTableForThreeSorts(ListOrder anOrder){
		this.showTableTitle(anOrder);
		this.showTableHeadForThreeSorts();
		this.showTableContentForThreeSorts();
		AppView.outputLine("");
	}

	//quicksort 결과를 보임
	private void showResultTableForQuickSorts(ListOrder anOrder){
		this.showTableTitle(anOrder);
		this.showTableHeadForQuickSorts();
		this.showTableContentForQuickSorts();
		AppView.outputLine("");
	}

	//quickSortWithInsertionSort 결과를 보임
	private void showResultTableForQuickSortWithInsertionSort(ListOrder anOrder){
		this.showTableTitle(anOrder);
		this.showTableHeadForQuickSortWithInsertionSort();
		this.showTableContentForQuickSortWithInsertionSort();
		AppView.outputLine("");
	}

	//인자로 정렬방식과 experimentManager를 전달해 측정을하고 그 결과를 보임.
	private void measureAndShow(ExperimentManager anExperimentManager, ListOrder anOrder){
		anExperimentManager.performExperiment(anOrder);																	//인자로 주어진 experimentManager의 측정을 하고
		if(anExperimentManager.getClass().equals(ExperimentManagerForThreeSorts.class)) {								//equals를 통해 experimentManager로 어떤것이 왔는지를 확인해 그에 알맞게 결과 출력
			this.showResultTableForThreeSorts(anOrder);
		}
		else if(anExperimentManager.getClass().equals(ExperimentManagerForQuickSorts.class)){
			this.showResultTableForQuickSorts(anOrder);
		}
		else{
			this.showResultTableForQuickSortWithInsertionSort(anOrder);
		}
	}

	public void run() {
		AppView.outputLine("<<< 정렬 성능 비교 프로그램을 시작합니다 >>>");
		AppView.outputLine("");
		{
			AppView.outputLine(">> 3가지 정렬의 성능 비교 : 삽입, 퀵, 힙 <<");
			this.setManagerForThreeSorts(new ExperimentManagerForThreeSorts());
			this.managerForThreeSorts().prepareExperiment(null);
			this.measureAndShow(this.managerForThreeSorts(), ListOrder.Random);
			this.measureAndShow(this.managerForThreeSorts(), ListOrder.Ascending);
			this.measureAndShow(this.managerForThreeSorts(), ListOrder.Descending);
		}
		{
			AppView.outputLine(">> 5가지 퀵 정렬 버전의 성능 비교<<");
			this.setManagerForQuickSorts(new ExperimentManagerForQuickSorts());
			this.managerForQuickSorts().prepareExperiment(null);
			this.measureAndShow(this.managerForQuickSorts(), ListOrder.Random);
			this.measureAndShow(this.managerForQuickSorts(), ListOrder.Ascending);
			this.measureAndShow(this.managerForQuickSorts(), ListOrder.Descending);
		}
		{
			AppView.outputLine(">> 삽입 정렬을 사용하는 퀵 정렬의 성능 : 삽입정렬을 실행하는 크기 별 성능을 비교 <<");
			this.setManagerForQuickSortWithInsertionSort(new ExperimentManagerForQuickSortWithInsertionSort());
			this.managerForQuickSortWithInsertionSort().prepareExperiment(null);
			this.measureAndShow(this.managerForQuickSortWithInsertionSort(), ListOrder.Random);

			this.managerForQuickSortWithInsertionSort().parameterSetForMaxSizeOfInsertionSort().setStartingSize(10);
			this.managerForQuickSortWithInsertionSort().parameterSetForMaxSizeOfInsertionSort().setIncrementSize(10);
			this.managerForQuickSortWithInsertionSort().parameterSetForMaxSizeOfInsertionSort().setNumberOfIteration(20);
			this.measureAndShow(this.managerForQuickSortWithInsertionSort(), ListOrder.Random);
		}
		AppView.outputLine("<<< 정렬 성능 비교 프로그램을 종료합니다 >>>");
	}
}
