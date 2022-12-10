package backend.model;

public class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius,Format format) {
        super(centerPoint, radius*2, radius*2,format);
    }
    public Circle(Point centerPoint, double radius,Format format,int figureID){
        super(centerPoint, radius*2, radius*2,format,figureID);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }

    public Double getRadius() {
        return getsMayorAxis()/2;
    }
    @Override
    public Figure centerFigure(){
        return new Circle(new Point(400,300),getRadius(),getFormat());
    }

    @Override
    public Figure getCopy(){
        return new Circle(getCenterPoint(),getRadius(),new Format(getFormat()),getFigureID());
    }

    @Override
    public String getName(){
        return "Circulo";
    }

}
