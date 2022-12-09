package frontend;

import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;

public class CutCopyPastePane extends BorderPane {

    // Botones Barra Superior

    String cutIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("cutIcon");
    Image cutIcon = new Image(HTMLEditorSkin.class.getResource(cutIconPath).toString());
    Button cutButton = new Button("Cortar", new ImageView(cutIcon));

    String copyIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("copyIcon");
    Image copyIcon = new Image(HTMLEditorSkin.class.getResource(copyIconPath).toString());
    Button copyButton = new Button("Copiar", new ImageView(copyIcon));

    String pasteIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("pasteIcon");
    Image pasteIcon = new Image(HTMLEditorSkin.class.getResource(pasteIconPath).toString());
    Button pasteButton = new Button("Pegar", new ImageView(pasteIcon));

    private Button[] toolsArr = {cutButton, copyButton, pasteButton};


    public CutCopyPastePane() {
        ToggleGroup tools = new ToggleGroup();
        for (Button tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setCursor(Cursor.HAND);
        }
        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-background-color: #999");
        buttonsBox.setPrefWidth(100);



        setCenter(buttonsBox);
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
