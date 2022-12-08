package backend.model;

public class Square extends Rectangle {


    public Square(Point topLeft, double size,Format format) {
        super(topLeft,new Point(topLeft.getX() + size, topLeft.getY() + size),format);
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

}
