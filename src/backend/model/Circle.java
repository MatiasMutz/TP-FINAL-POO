package backend.model;

public class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius,Format format) {
        super(centerPoint, radius*2, radius*2,format);
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
        return new Circle(new Point(getCenterPoint()),getRadius(),new Format(getFormat()));
    }

    @Override
    public String getName(){
        return "Circulo";
    }
/*
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Circle)){
            return false;
        }
        if(other==this){
            return true;
        }
        Circle aux=(Circle) other;
        return getCenterPoint().equals(aux.getCenterPoint()) && getRadius().equals(aux.getRadius());
    }

*/
}
