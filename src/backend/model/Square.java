package backend.model;

public class Square extends Rectangle {

    private Double size;
    public Square(Point topLeft, double size,Format format) {
        super(topLeft,new Point(topLeft.getX() + size, topLeft.getY() + size),format);
        this.size=size;
    }

    public Square(Point topLeft, double size,Format format,int figureID) {
        super(topLeft,new Point(topLeft.getX() + size, topLeft.getY() + size),format,figureID);
        this.size=size;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }


    public Figure centerFigure(){
        Point auxTopLeft=new Point(400-(getBottomRight().getX()-getTopLeft().getX())/2,300+(getTopLeft().getY()- getBottomRight().getY())/2);
        return new Square(auxTopLeft,size,getFormat());
    }

    @Override
    public String getName(){
        return "Cuadrado";
    }
    @Override
    public Figure getCopy(){
        return new Square(getTopLeft(),size,new Format(getFormat()),getFigureID());
    }

}
