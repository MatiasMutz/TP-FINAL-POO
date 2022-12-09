package frontend.buttons;

import backend.CanvasState;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



import java.util.Objects;
import java.util.ResourceBundle;


public class UndoPane extends BorderPane {

    Label undoLabel = new Label("0");
    Label redoLabel = new Label("0");
    String undoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("undoIcon");
    Image undoIcon = new Image(Objects.requireNonNull(HTMLEditorSkin.class.getResource(undoIconPath)).toString());
    Button undoButton = new Button("Deshacer", new ImageView(undoIcon));

    String redoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("redoIcon");
    Image redoIcon = new Image(Objects.requireNonNull(HTMLEditorSkin.class.getResource(redoIconPath)).toString());
    Button redoButton = new Button("Rehacer", new ImageView(redoIcon));

    Button[] toolsArr = {undoButton, redoButton};



    public UndoPane(){
        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(undoLabel);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.getChildren().addAll(redoLabel);
        buttonsBox.setStyle("-fx-background-color: #999");

        undoLabel.setAlignment(Pos.CENTER);
        undoLabel.setStyle("-fx-font-size: 16");

        redoLabel.setAlignment(Pos.CENTER);
        redoLabel.setStyle("-fx-font-size: 16");

        setCenter(buttonsBox);
        buttonsBox.setAlignment(Pos.CENTER);


    }

}
