package backend.model;

public class Point {

    private Double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point){
        x=point.getX();
        y=point.getY();
    }
    public void moveX(double distance){
        x+=distance;
    }
    public void moveY(double distance){
        y+=distance;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }
    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Point)){
        return false;
        }
        if(other==this){
        return true;
        }
        Point aux=(Point) other;
        return x.equals(aux.getX()) && y.equals(aux.getY());
}

}
