package backend.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.Objects;

public class Ellipse extends Figure {

    private  Point centerPoint;
    private Double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis,Format format) {
        super(format);
        defineEllipse(centerPoint,sMayorAxis,sMinorAxis);
    }

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Format format, int figureID){
        super(format,figureID);
        defineEllipse(centerPoint,sMayorAxis,sMinorAxis);
    }

    private void defineEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis){
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
        return new Ellipse(centerPoint,sMayorAxis,sMinorAxis,new Format(getFormat()),getFigureID());
    }
    @Override
    public String getName(){
        return "Elipse";
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Ellipse)){
            return false;
        }
        if(other==this){
            return true;
        }
        Ellipse aux=(Ellipse) other;
        return getFigureID().equals(aux.getFigureID());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getFigureID());
    }
}
