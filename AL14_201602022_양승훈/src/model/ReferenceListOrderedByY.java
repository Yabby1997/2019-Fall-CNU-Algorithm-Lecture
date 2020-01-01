package model;

public class ReferenceListOrderedByY extends ReferenceListOrderedByCoordinate{
    public ReferenceListOrderedByY(PointSet givenPointSet) {
        super(givenPointSet);
    }

    protected int coordinateReferencedByIndex(int i){
        return this.pointSet().pointReferencedByIndex(this, i).y;
    }
}
