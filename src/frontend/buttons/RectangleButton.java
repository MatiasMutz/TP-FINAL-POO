package frontend.buttons;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.control.ToggleButton;

public class RectangleButton extends SpecialButton{
    public RectangleButton(String info){
        super(info);
    }

    @Override
    public Figure newFigure(Point startPoint, Point endPoint){
        return new Rectangle(startPoint, endPoint);
    }
}
