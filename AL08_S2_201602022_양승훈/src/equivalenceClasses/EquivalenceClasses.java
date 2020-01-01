package equivalenceClasses;

import app.AppView;
import graph.AdjacencyGraph;
import graph.Edge;
import list.List;
import list.LinkedList;
import list.Stack;
import list.LinkedStack;
import list.ArrayStack;
import list.Iterator;

public class EquivalenceClasses<E extends Edge> {
    private static final boolean DEBUG_MODE = true;                                                                     //디버깅 메시지용
    private static void showDebugMessage(String aMessage){
        if(EquivalenceClasses.DEBUG_MODE){
            AppView.output(aMessage);
        }
    }

    private AdjacencyGraph<E> _graph;
    private boolean[] _found;
    private Stack<Integer> _sameClassMembers;
    private List<List<Integer>> _equivalenceClassList;

    //constructor
    public EquivalenceClasses(){
        this.setGraph(null);
        this.setFound(null);
        this.setSameClassMembers(null);
        this.setEquivalenceClassList(null);
    }

    //getter setter
    private AdjacencyGraph<E> graph(){
        return this._graph;
    }

    private void setGraph(AdjacencyGraph<E> newGraph){
        this._graph = newGraph;
    }

    private boolean[] found(){
        return this._found;
    }

    private void setFound(boolean[] newFound){
        this._found = newFound;
    }

    private Stack<Integer> sameClassMembers(){
        return this._sameClassMembers;
    }

    private void setSameClassMembers(Stack<Integer> newSameClassMembers){
        this._sameClassMembers = newSameClassMembers;
    }

    public List<List<Integer>> equivalenceClassList(){
        return this._equivalenceClassList;
    }

    private void setEquivalenceClassList(List<List<Integer>> newEquivalenceClassList){
        this._equivalenceClassList = newEquivalenceClassList;
    }

    public boolean solve(AdjacencyGraph<E> aGraph){                                                                     //AdjacencyGraph를 상속받는 모든 클래스를 넣을 수 있다.
        this.setGraph(aGraph);
        if(this.graph().numberOfVertices() < 1){                                                                        //vertex의 개수가 1개 미만이면 안된다. false반환
            return false;
        }
        this.setFound(new boolean[this.graph().numberOfVertices()]);
        this.setEquivalenceClassList(new LinkedList<List<Integer>>());
        this.setSameClassMembers(new ArrayStack<Integer>());

        EquivalenceClasses.showDebugMessage("\n");
        for(int vertex = 0; vertex < this.graph().numberOfVertices(); vertex++){                                        //모든 vertex를 돌면서
            if(!this.found()[vertex]){                                                                                  //아직 동등 클래스가 찾아지지 않은 vertex를 발견하면
                EquivalenceClasses.showDebugMessage("[Debug] 새로운 동등 클래스 : {");
                List<Integer> newEquivalenceClass = new LinkedList<Integer>();                                          //새로운 동등클래스의 집합을 만들고
                this.equivalenceClassList().add(newEquivalenceClass);
                this.found()[vertex] = true;                                                                            //동등클래스는 찾아진 것으로,
                newEquivalenceClass.add(vertex);                                                                        //해당 집합에 속하는것으로 해준다.
                EquivalenceClasses.showDebugMessage(" " + vertex);
                this.sameClassMembers().push(vertex);                                                                   //동등관계가 있는지를 알아보기위해 sameClassMembers에 넣어준다.

                while(!sameClassMembers().isEmpty()){                                                                   //동등클래스를 찾을 대상이 있다면
                    int tailVertex = sameClassMembers().pop();                                                          //꺼내서
                    Iterator<E> iterator = this.graph().neighborIteratorOf(tailVertex);                                 //이웃한 edge들을 순회하며
                    while(iterator.hasNext()){
                        E edge = iterator.next();                                                                       //해당 edge의 head를 확인하여 쌍을 이룬 vertex를 찾는다.
                        int headVertex = edge.headVertex();
                        if(!this.found()[headVertex]){                                                                  //head도 동등클래스가 찾아지지 않았다면
                            this.found()[headVertex] = true;                                                            //찾아진것으로 바꾸고
                            newEquivalenceClass.add(headVertex);                                                        //동등 클래스 리스트에 함께 추가해준다.
                            EquivalenceClasses.showDebugMessage(", " + headVertex);
                            this.sameClassMembers().push(headVertex);                                                   //head를 대상으로 동등클래스를 다시 찾는다.
                        }
                    }
                }
                EquivalenceClasses.showDebugMessage("}\n");
            }
        }
        return true;
    }
}
