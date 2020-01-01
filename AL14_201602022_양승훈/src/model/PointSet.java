package model;

import list.ArrayList;

public class PointSet extends ArrayList<Point>{
    public PointSet(int givenCapacity){
        super(givenCapacity);
    }

    public Point pointReferencedByIndex(ReferenceList referenceList, int index){
        if(referenceList.orderIsValid(index)){
            return this.elementAt(referenceList.elementAt(index));                                                      //reference의 index번째가 pointset의 index, 그 index가 가리키는게 찾는 point임
        }
        else{
            return null;
        }
    }
}
