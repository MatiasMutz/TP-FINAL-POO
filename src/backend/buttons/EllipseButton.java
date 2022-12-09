package backend.buttons;

import backend.model.*;

public class EllipseButton extends SpecialButton{
    public EllipseButton(String info){
        super(info);
    }

    @Override
    public Figure newFigure(Point startPoint, Point endPoint, Format format){
        Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        return new Ellipse(centerPoint, sMayorAxis, sMinorAxis, format);
    }
}
