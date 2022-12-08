package frontend.buttons;

import backend.model.Figure;
import backend.model.Format;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.control.ToggleButton;

public class SquareButton extends SpecialButton{
    public SquareButton(String info){
        super(info);
    }
    @Override
    public Figure newFigure(Point startPoint, Point endPoint, Format format){
        double size = Math.abs(endPoint.getX() - startPoint.getX());
        return new Square(startPoint, size,format);
    }
}
