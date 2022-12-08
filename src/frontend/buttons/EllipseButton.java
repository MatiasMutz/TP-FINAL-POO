package frontend.buttons;

import backend.model.Circle;
import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.control.ToggleButton;

public class EllipseButton extends SpecialButton{
    public EllipseButton(String info){
        super(info);
    }

    @Override
    public Figure newFigure(Point startPoint, Point endPoint){
        Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        return new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
    }
}
