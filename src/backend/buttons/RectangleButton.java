package backend.buttons;

import backend.model.Figure;
import backend.model.Format;
import backend.model.Point;
import backend.model.Rectangle;

public class RectangleButton extends SpecialButton{
    public RectangleButton(String info){
        super(info);
    }

    @Override
    public Figure newFigure(Point startPoint, Point endPoint, Format format){
        return new Rectangle(startPoint, endPoint,format);
    }
}
