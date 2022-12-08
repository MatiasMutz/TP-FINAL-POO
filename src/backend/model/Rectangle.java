package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle implements Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
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

}
