package frontend;

import backend.CanvasState;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {

    public MainFrame(CanvasState canvasState) {
        getChildren().add(new AppMenuBar());
        StatusPane statusPane = new StatusPane();
        CutCopyPastePane cutCopyPastePane=new CutCopyPastePane();
        getChildren().add(cutCopyPastePane);
        UndoPane undoPane=new UndoPane(canvasState);
        getChildren().add(undoPane);
        getChildren().add(new PaintPane(canvasState, statusPane,cutCopyPastePane,undoPane));
        getChildren().add(statusPane);
    }

}
