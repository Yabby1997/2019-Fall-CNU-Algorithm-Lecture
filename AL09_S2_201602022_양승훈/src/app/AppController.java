package app;

import experiment.DataGenerator;
import sort.QuickSort;
import sort.QuickSortByPivotRandom;
import sort.QuickSortByPivotMedian;
import sort.QuickSortByPivotLeft;
import sort.QuickSortByPivotMid;
import sort.QuickSortWithInsertionSort;
import sort.Sort;

public class AppController {
	private static final int TEST_SIZE = 100;

	private static final QuickSort<Integer> QuickSortByPivotLeft = new QuickSortByPivotLeft<Integer>(true);
	private static final QuickSort<Integer> QuickSortByPivotMedian = new QuickSortByPivotMedian<Integer>(true);
	private static final QuickSort<Integer> QuickSortByPivotMid = new QuickSortByPivotMid<Integer>(true);
	private static final QuickSort<Integer> QuickSortByPivotRandom = new QuickSortByPivotRandom<Integer>(true);
	private static final QuickSort<Integer> QuickSortWithInsertionSort = new QuickSortWithInsertionSort<Integer>(true);

	private Integer[] _list;
	private String _listTypeName;

	private Integer[] list(){
		return this._list;
	}

	private String listTypeName(){
		return this._listTypeName;
	}

	private void setList(Integer[] newList){
		this._list = newList;
	}

	private void setListTypeName(String newListTypeName){
		this._listTypeName = newListTypeName;
	}

	private String sortingOrderName(Sort<Integer> aSort){
		if (aSort.nonDecreasingOrder()) {
			return "오름차순";
		}
		else{
			return "내림차순";
		}
	}

	private void outputValidationMessage(Sort<Integer> aSort, Integer[] aList){
		AppView.output("- [" + this.listTypeName() + "] 에 대한 [" + this.sortingOrderName(aSort) + "]의 [" + aSort.getClass().getSimpleName() + "] 결과는 ");
		if(this.sortedListIsValid(aList, aSort.nonDecreasingOrder())){
			AppView.outputLine("올바릅니다.");
		}
		else{
			AppView.outputLine("잘못되었습니다.");
		}
	}

	private Integer[] copyList(Integer[] aList){																		//리스트 사이즈만큼 순회하며 새로운 복사된 리스트를 만들어 반환
		Integer[] copiedList = new Integer[aList.length];
		for(int i = 0; i < aList.length; i++){
			copiedList[i] = aList[i];
		}
		return copiedList;
	}

	private boolean sortedListIsValid(Integer[] aList, boolean nonDecreasing){											//유효한지를 알아보기위해 리스트와 정렬방식을 인자로받음
		if(nonDecreasing){
			for(int i = 0; i < aList.length - 1; i++){
				if(aList[i].compareTo(aList[i + 1]) > 0){																//오름차순일 때 다음 요소보다 값이 큰 요소가 발견되면 정렬이 잘못된것
					return false;
				}
			}
			return true;
		}
		else{
			for(int i = 0; i < aList.length - 1; i++){
				if(aList[i].compareTo(aList[i + 1]) < 0){																//내림차순일 때 다음 요소보다 값이 작은 요소가 발견되면 정렬이 잘못된것
					return false;
				}
			}
			return true;
		}
	}

	private void validateSort(Sort<Integer> aSort){																		//정렬 할 리스트를 인자로받음
		Integer[] list = copyList(this.list());																			//인자로받은 리스트를 카피함
		aSort.sort(list);        																						//카피한 리스트를 정렬해서
		this.outputValidationMessage(aSort, list); 																		//결과 출력
	}

	private void validateSorts(){
		this.validateSort(AppController.QuickSortByPivotLeft);																	//각 소팅방식별로 소트를 검증한다.
		this.validateSort(AppController.QuickSortByPivotMid);
		this.validateSort(AppController.QuickSortByPivotMedian);
		this.validateSort(AppController.QuickSortByPivotRandom);
		this.validateSort(AppController.QuickSortWithInsertionSort);
		AppView.outputLine("");
	}

	private void validateWithRandomList(){																				//무작위 리스트를 만들어 각 소팅방식을 검증한다.
		this.setListTypeName("무작위 리스트");
		this.setList(DataGenerator.randomList(AppController.TEST_SIZE));
		this.validateSorts();
	}

	private void validateWithAscendingList(){
		this.setListTypeName("오름차순 리스트");
		this.setList(DataGenerator.ascendingList(AppController.TEST_SIZE));												//오름차순 리스트를 만들어 각 소팅방식을 검증한다.
		this.validateSorts();
	}

	private void validateWithDescendingList(){
		this.setListTypeName("내림차순 리스트");
		this.setList(DataGenerator.descendingList(AppController.TEST_SIZE));											//내림차순 리스트를 만들어 각 소팅방식을 검증한다.
		this.validateSorts();
	}

	private void setSortingOrder(boolean aNonDecreasing){																//인자로 넣어준 정렬 방식에 맞게 각 소팅들을 설정해줌.
		AppController.QuickSortByPivotLeft.setNonDecreasingOrder(aNonDecreasing);
		AppController.QuickSortByPivotMid.setNonDecreasingOrder(aNonDecreasing);
		AppController.QuickSortByPivotMedian.setNonDecreasingOrder(aNonDecreasing);
		AppController.QuickSortByPivotRandom.setNonDecreasingOrder(aNonDecreasing);
		AppController.QuickSortWithInsertionSort.setNonDecreasingOrder(aNonDecreasing);
	}

	public void run(){
		AppView.outputLine("<<< 정렬 알고리즘을 검증하는 프로그램을 시작합니다 >>>");
		AppView.outputLine("");

		AppView.outputLine("> 오름차순 정렬 프로그램의 검증");															//오름차
		this.setSortingOrder(true);
		this.validateWithRandomList();
		this.validateWithAscendingList();
		this.validateWithDescendingList();

		AppView.outputLine("> 내림차순 정렬 프로그램의 검증");
		this.setSortingOrder(false);
		this.validateWithRandomList();
		this.validateWithAscendingList();
		this.validateWithDescendingList();

		AppView.outputLine("<<< 정렬 알고리즘을 검증하는 프로그램을 종료합니다 >>>");
	}
}
