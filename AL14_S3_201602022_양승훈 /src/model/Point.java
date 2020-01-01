package model;

public class Point {
    public int x;
    public int y;

    public Point(){
        this(0, 0);
    }

    public Point(int givenX, int givenY){
        this.x = givenX;
        this.y = givenY;
    }

    public long distanceTo(Point other){
        if(other == null){
            return Integer.MAX_VALUE;
        }
        else{
            long differenceOfX = other.x - this.x;
            long differenceOfY = other.y - this.y;
            return ((differenceOfX * differenceOfX) + (differenceOfY * differenceOfY));
        }
    }
}
