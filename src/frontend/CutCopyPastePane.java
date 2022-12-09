package frontend;

import backend.buttons.CopyButton;
import backend.buttons.CutButton;
import backend.buttons.CutCopyPasteButtons;
import backend.buttons.PasteButton;
import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
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
    CutCopyPasteButtons cutButton = new CutButton("Cortar", new ImageView(cutIcon));

    String copyIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("copyIcon");
    Image copyIcon = new Image(HTMLEditorSkin.class.getResource(copyIconPath).toString());
    CutCopyPasteButtons copyButton = new CopyButton("Copiar", new ImageView(copyIcon));

    String pasteIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString("pasteIcon");
    Image pasteIcon = new Image(HTMLEditorSkin.class.getResource(pasteIconPath).toString());
    CutCopyPasteButtons pasteButton = new PasteButton("Pegar", new ImageView(pasteIcon));

    private CutCopyPasteButtons[] toolsArr = {cutButton, copyButton, pasteButton};

    public CutCopyPastePane() {
        ToggleGroup tools = new ToggleGroup();
        for (CutCopyPasteButtons tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setCursor(Cursor.HAND);
        }
        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-background-color: #999");
        buttonsBox.setPrefWidth(100);

        cutButton.setOnMouseClicked(event->{

        });

        setCenter(buttonsBox);
    }

    public CutCopyPasteButtons[] getButtons(){
        return toolsArr;
    }
}
