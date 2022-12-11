package backend.buttons;

import backend.CanvasState;
import backend.model.Figure;

public class SelectionButton extends ClickableButton {

    public SelectionButton(String info){
        super(info);
    }

    public void actOnClick(CanvasState canvasState, Figure figure){
        canvasState.selectFigure(figure);
        canvasState.restartCopyFormat();
    }

}
