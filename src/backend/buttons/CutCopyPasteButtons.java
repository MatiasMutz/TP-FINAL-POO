package backend.buttons;

import backend.CanvasState;
import backend.model.Figure;
import frontend.PaintPane;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;


public abstract class CutCopyPasteButtons extends Button {

    public CutCopyPasteButtons(String info, ImageView imageView){
        super(info, imageView);
    }

    public abstract void apply(Figure figure, CanvasState canvasState);
}
