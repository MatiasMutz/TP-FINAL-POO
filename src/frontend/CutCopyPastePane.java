package frontend;

import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;

public class CutCopyPastePane extends BorderPane {

    // Botones Barra Superior

    private final String cutIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("cutIcon");
    private final Image cutIcon = new Image(HTMLEditorSkin.class.getResource(cutIconPath).toString());
    private final Button cutButton = new Button("Cortar", new ImageView(cutIcon));

    private final String copyIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("copyIcon");
    private final Image copyIcon = new Image(HTMLEditorSkin.class.getResource(copyIconPath).toString());
    private final Button copyButton = new Button("Copiar", new ImageView(copyIcon));

    private final String pasteIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("pasteIcon");
    private final Image pasteIcon = new Image(HTMLEditorSkin.class.getResource(pasteIconPath).toString());
    private final Button pasteButton = new Button("Pegar", new ImageView(pasteIcon));

    public CutCopyPastePane() {
        Button[] toolsArr = {cutButton, copyButton, pasteButton};

        for (Button tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setCursor(Cursor.HAND);
        }
        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-background-color: #999");
        buttonsBox.setPrefWidth(100);

        setOnKeyPressed(event -> {
            if(event.isControlDown()){
                controlShortcuts(event);
            }
        });

        setCenter(buttonsBox);
    }

    public void controlShortcuts(KeyEvent event){
        if(event.getCode() == KeyCode.X)
            cutButton.fire();
        else if(event.getCode() == KeyCode.C)
            copyButton.fire();
        else if(event.getCode() == KeyCode.V)
            pasteButton.fire();
    }

    public Button getCutButton(){
        return cutButton;
    }

    public Button getCopyButton() {
        return copyButton;
    }

    public Button getPasteButton() {
        return pasteButton;
    }
}
