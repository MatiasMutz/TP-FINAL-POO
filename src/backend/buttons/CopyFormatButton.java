package backend.buttons;

import backend.CanvasState;
import backend.model.Figure;

public class CopyFormatButton extends ClickableButton {

    public CopyFormatButton(String info){
        super(info);
    }

    public void actOnClick(CanvasState canvasState, Figure figure){
        canvasState.pasteFormat(figure);
        setSelected(false);
    }
}
