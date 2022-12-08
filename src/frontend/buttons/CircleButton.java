package frontend.buttons;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Format;
import backend.model.Point;
import javafx.scene.control.ToggleButton;

public class CircleButton extends SpecialButton {
    public CircleButton(String info){
        super(info);
    }

    @Override
    public Figure newFigure(Point startPoint, Point endPoint, Format format){
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new Circle(startPoint, circleRadius,format);
    }
}
