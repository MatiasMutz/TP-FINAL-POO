package backend.buttons;

import backend.CanvasState;
import backend.model.Figure;
import javafx.scene.control.ToggleButton;

public abstract class ClickableButton extends ToggleButton {

    public ClickableButton(String info){
        super(info);
    }

    public abstract void actOnClick(CanvasState canvasState, Figure figure);

}
