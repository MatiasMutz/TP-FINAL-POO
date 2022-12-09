package backend.buttons;


import backend.CanvasState;
import backend.model.Figure;
import frontend.PaintPane;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CutButton extends CutCopyPasteButtons{


    public CutButton(String info, ImageView imageView){
        super(info, imageView);
    }

    @Override
    public void apply(Figure figure, CanvasState canvasState){
        canvasState.setToCopyFigure(figure);
        canvasState.deleteFigure(figure);
    }
}
