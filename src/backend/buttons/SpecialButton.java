package backend.buttons;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Format;
import backend.model.Point;
import javafx.scene.control.ToggleButton;

public abstract class SpecialButton extends ToggleButton {

    public SpecialButton(String info){
        super(info);
    }
    public abstract Figure newFigure(Point startPoint, Point endPoint, Format format);

}
