package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Figure {

    private  Point centerPoint;
    private final double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis,Format format) {
        super(format);
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }
    @Override
    public void moveX(double distance){
        centerPoint.moveX(distance);
    }
    @Override
    public void moveY(double distance){
        centerPoint.moveY(distance);

    }

    @Override
    public void redrawCanvas(GraphicsContext gc){
        gc.strokeOval(centerPoint.getX() - (sMayorAxis / 2), centerPoint.getY() - (sMinorAxis / 2), sMayorAxis, sMinorAxis);
        gc.fillOval(centerPoint.getX() - (sMayorAxis / 2), centerPoint.getY() - (sMinorAxis / 2), sMayorAxis, sMinorAxis);
    }

    @Override
    public boolean figureBelongs(Point eventPoint){
        return ((Math.pow(eventPoint.getX() - centerPoint.getX(), 2) / Math.pow(sMayorAxis, 2)) +
                (Math.pow(eventPoint.getY() - centerPoint.getY(), 2) / Math.pow(sMinorAxis, 2))) <= 0.30;
    }
    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }
    @Override
    public Figure centerFigure(){
        return new Ellipse(new Point(400,300),sMayorAxis,sMinorAxis,getFormat());
    }

    @Override
    public Figure getCopy(){
        return new Ellipse(centerPoint,sMayorAxis,sMinorAxis,getFormat());
    }
}
