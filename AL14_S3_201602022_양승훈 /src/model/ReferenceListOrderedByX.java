package model;

public class ReferenceListOrderedByX extends ReferenceListOrderedByCoordinate{
    public ReferenceListOrderedByX(PointSet givenPointSet) {
        super(givenPointSet);
    }

    protected int coordinateReferencedByIndex(int i){
        return this.pointSet().pointReferencedByIndex(this, i).x;
    }
}
