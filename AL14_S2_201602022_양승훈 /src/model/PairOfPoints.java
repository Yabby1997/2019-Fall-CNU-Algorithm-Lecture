package model;

public class PairOfPoints {
    private Point _firstPoint;
    private Point _secondPoint;

    public PairOfPoints(){
        this(null, null);
    }

    public PairOfPoints(Point givenFirstPoint, Point givenSecondPoint){
        this.setFirstPoint(givenFirstPoint);
        this.setSecondPoint(givenSecondPoint);
    }

    public Point firstPoint(){
        return this._firstPoint;
    }

    public void setFirstPoint(Point newFirstPoint){
        this._firstPoint = newFirstPoint;
    }

    public Point secondPoint(){
        return this._secondPoint;
    }

    public void setSecondPoint(Point newSecondPoint){
        this._secondPoint = newSecondPoint;
    }

    public long distance(){
        if(this.firstPoint() == null || this.secondPoint() == null){
            return Integer.MAX_VALUE;
        }
        else{
            return this.firstPoint().distanceTo(this.secondPoint());
        }
    }
}
