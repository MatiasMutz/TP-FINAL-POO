package backend.model;

public class Square extends Rectangle {

    private Double size;
    public Square(Point topLeft, double size,Format format) {
        super(topLeft,new Point(topLeft.getX() + size, topLeft.getY() + size),format);
        this.size=size;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }


    public Figure centerFigure(){
        Point auxTopLeft=new Point(400-(getBottomRight().getX()-getTopLeft().getX())/2,300+(getTopLeft().getY()- getBottomRight().getY())/2);
        Point auxBottomRight=new Point(400+(getBottomRight().getX()-getTopLeft().getX())/2,300-(getTopLeft().getY()- getBottomRight().getY())/2);
        return new Square(auxTopLeft,size,getFormat());
    }

    @Override
    public String getName(){
        return "Cuadrado";
    }
    @Override
    public Figure getCopy(){
        return new Square(new Point(getTopLeft()),size,new Format(getFormat()));
    }

    public Double getSize(){
        return size;
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Square)){
            return false;
        }
        if(this == other){
            return true;
        }
        Square aux = (Square) other;
        return getTopLeft().equals(aux.getTopLeft()) && getBottomRight().equals(aux.getBottomRight()) && size.equals(aux.getSize());
    }
}
