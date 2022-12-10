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

    private String redoMessage = "";
    private String undoMessage = "";

    private Integer redoTimes=0;
    private Integer undoTimes=0;

    private final Label undoLabel = new Label(undoTimes.toString());
    private final Label redoLabel = new Label(redoTimes.toString());
    private final Label redoMessageLabel = new Label(redoMessage);
    private final Label undoMessageLabel = new Label(undoMessage);
    private final String undoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("undoIcon");
    private final Image undoIcon = new Image(Objects.requireNonNull(HTMLEditorSkin.class.getResource(undoIconPath)).toString());
    private final Button undoButton = new Button("Deshacer", new ImageView(undoIcon));

    private final String redoIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("redoIcon");
    private final Image redoIcon = new Image(Objects.requireNonNull(HTMLEditorSkin.class.getResource(redoIconPath)).toString());
    private final Button redoButton = new Button("Rehacer", new ImageView(redoIcon));

    private final Button[] toolsArr = {undoButton, redoButton};



    public UndoPane(){
        HBox buttonsBox = new HBox(10);

        buttonsBox.getChildren().addAll(undoMessageLabel, undoLabel);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.getChildren().addAll(redoLabel, redoMessageLabel);
        buttonsBox.setStyle("-fx-background-color: #999");

        undoLabel.setAlignment(Pos.CENTER);
        undoLabel.setStyle("-fx-font-size: 16");
        undoLabel.setPrefWidth(30);

        redoLabel.setAlignment(Pos.CENTER);
        redoLabel.setStyle("-fx-font-size: 16");
        redoLabel.setPrefWidth(30);

        undoMessageLabel.setPrefWidth(300);
        undoMessageLabel.setAlignment(Pos.CENTER_RIGHT);
        undoMessageLabel.setStyle("-fx-font-size: 12");

        redoMessageLabel.setPrefWidth(300);
        redoMessageLabel.setAlignment(Pos.CENTER_LEFT);
        redoMessageLabel.setStyle("-fx-font-size: 12");

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
