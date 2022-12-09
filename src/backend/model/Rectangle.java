package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Figure {

    private Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight,Format format) {
        super(format);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }
    @Override
    public void moveX(double distance){
        topLeft.moveX(distance);
        bottomRight.moveX(distance);
    }
    @Override
    public void moveY(double distance){
        topLeft.moveY(distance);
        bottomRight.moveY(distance);
    }

    @Override
    public void redrawCanvas(GraphicsContext gc){
        gc.fillRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
        gc.strokeRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
    }

    @Override
    public boolean figureBelongs(Point eventPoint){
        return eventPoint.getX() > topLeft.getX() && eventPoint.getX() < bottomRight.getX() &&
                eventPoint.getY() > topLeft.getY() && eventPoint.getY() < bottomRight.getY();
    }
    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }
    @Override
    public Figure centerFigure(){
        Point auxTopLeft=new Point(400-(bottomRight.getX()-topLeft.getX())/2,300+(topLeft.getY()- bottomRight.getY())/2);
        Point auxBottomRight=new Point(400+(bottomRight.getX()-topLeft.getX())/2,300-(topLeft.getY()- bottomRight.getY())/2);
        return new Rectangle(auxTopLeft,auxBottomRight,getFormat());
    }

}
