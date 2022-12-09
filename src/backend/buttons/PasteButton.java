package backend.buttons;

import backend.CanvasState;
import backend.model.Figure;
import frontend.PaintPane;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class PasteButton extends CutCopyPasteButtons {

    public PasteButton(String info, ImageView imageView){
        super(info, imageView);
    }

    @Override
    public void apply(Figure figure, CanvasState canvasState){
        canvasState.addFigure(canvasState.getToCopyFigure());
    }
}
