package frontend;

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

    private String redoMessage = "u";
    private String undoMessage = "r";

    private Integer redoTimes=0;
    private Integer undoTimes=0;

    Label undoLabel = new Label(undoTimes.toString());
    Label redoLabel = new Label(redoTimes.toString());
    Label redoMessageLabel = new Label(redoMessage);
    Label undoMessageLabel = new Label(undoMessage);
    String undoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("undoIcon");
    Image undoIcon = new Image(Objects.requireNonNull(HTMLEditorSkin.class.getResource(undoIconPath)).toString());
    Button undoButton = new Button("Deshacer", new ImageView(undoIcon));

    String redoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("redoIcon");
    Image redoIcon = new Image(Objects.requireNonNull(HTMLEditorSkin.class.getResource(redoIconPath)).toString());
    Button redoButton = new Button("Rehacer", new ImageView(redoIcon));

    Button[] toolsArr = {undoButton, redoButton};



    public UndoPane(CanvasState canvasState){
        HBox buttonsBox = new HBox(10);

        buttonsBox.getChildren().addAll(undoMessageLabel, undoLabel);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.getChildren().addAll(redoLabel, redoMessageLabel);
        buttonsBox.setStyle("-fx-background-color: #999");

        undoLabel.setAlignment(Pos.CENTER);
        undoLabel.setStyle("-fx-font-size: 16");

        redoLabel.setAlignment(Pos.CENTER);
        redoLabel.setStyle("-fx-font-size: 16");
        redoMessageLabel.setStyle("-fx-font-size: 16");
        undoMessageLabel.setStyle("-fx-font-size: 16");

        setCenter(buttonsBox);
        buttonsBox.setAlignment(Pos.CENTER);


    }

    public void updateUndoPane(String redoText, Integer redoTimes,String undoText,Integer undoTimes){
        redoMessageLabel.setText(redoText);
        undoMessageLabel.setText(undoText);
        undoLabel.setText(undoTimes.toString());
        redoLabel.setText(redoTimes.toString());
    }

    public Button getUndoButton() {
        return undoButton;
    }

    public Button getRedoButton() {
        return redoButton;
    }
}
