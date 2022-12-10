package frontend;

import backend.Action;
import backend.CanvasState;
import backend.model.*;
import backend.buttons.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;
	CutCopyPastePane cutCopyPastePane;
	UndoPane undoPane;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;

	// Botones Barra Izquierda
	SpecialButton selectionButton = new SpecialButton("Seleccionar");
	SpecialButton rectangleButton = new RectangleButton("Rectángulo");
	SpecialButton circleButton = new CircleButton("Círculo");
	SpecialButton squareButton = new SquareButton("Cuadrado");
	SpecialButton ellipseButton = new EllipseButton("Elipse");
	SpecialButton deleteButton = new SpecialButton("Borrar");

	SpecialButton copyFormatButton=new SpecialButton("Cop. Form.");

	Label sliderLabel = new Label("Borde");
	Label fillLabel = new Label("Relleno");
	Slider slider = new Slider(1, 50, 10);
	final ColorPicker borderColorPicker = new ColorPicker(Color.BLACK);
	final ColorPicker fillColorPicker = new ColorPicker(Color.BLUE);
	final Label coloredText = new Label("Colors");

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane,CutCopyPastePane cutCopyPastePane,UndoPane undoPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		this.cutCopyPastePane=cutCopyPastePane;
		this.undoPane=undoPane;

		Button cutButton=cutCopyPastePane.getCutButton();
		Button copyButton=cutCopyPastePane.getCopyButton();
		Button pasteButton=cutCopyPastePane.getPasteButton();

		Button undoButton=undoPane.getUndoButton();
		Button redoButton=undoPane.getRedoButton();

		SpecialButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton,copyFormatButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		coloredText.setFont(new Font(53));
		buttonsBox.getChildren().addAll(sliderLabel, slider, borderColorPicker, fillLabel, fillColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(50);

		canvas.setOnMousePressed(event -> {
			canvasState.setStartPoint(new Point(event.getX(), event.getY()));
			//startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			//addNewFigure(event,toolsArr);
			canvasState.addNewFigure(event, toolsArr, fillColorPicker.getValue(), borderColorPicker.getValue(), slider.getValue());
			redrawCanvas();

		});

		canvas.setOnMouseMoved(event -> {
			mouseMovedonCanvas(event);
		});

		canvas.setOnMouseClicked(event -> {
			mouseClickonCanvas(event);
		});

		canvas.setOnMouseDragged(event -> {
			//mouseDraggedonCanvas(event);
			if(selectionButton.isSelected()){
				canvasState.mouseDraggedonCanvas(event);
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			canvasState.deleteFigure();
			redrawCanvas();
		});

		copyFormatButton.setOnAction(event->{
			canvasState.copyFormatFigure();
		});

		//Cambia el formato si
		borderColorPicker.setOnAction(event-> {
			canvasState.updateSelectedFormat(Action.CHANGEBORDERCOLOR, fillColorPicker.getValue(), borderColorPicker.getValue(), slider.getValue());
			redrawCanvas();
		});
		fillColorPicker.setOnAction(event->{
			canvasState.updateSelectedFormat(Action.CHANGEFILL, fillColorPicker.getValue(), borderColorPicker.getValue(), slider.getValue());
			redrawCanvas();
		});
		slider.setOnMouseReleased(event -> {
			canvasState.updateSelectedFormat(Action.CHANGEBORDER, fillColorPicker.getValue(), borderColorPicker.getValue(), slider.getValue());
			redrawCanvas();
		});

		copyButton.setOnAction(event->{
			canvasState.copyFigure();
			redrawCanvas();
		});

		cutButton.setOnAction(event -> {
			canvasState.cutFigure();
			redrawCanvas();
		});

		pasteButton.setOnAction(event -> {
			canvasState.pasteFigure();
			redrawCanvas();
		});

		setOnKeyPressed(event -> {
			if(event.isControlDown()){
				if(event.getCode() == KeyCode.X)
					cutButton.fire();
				else if(event.getCode() == KeyCode.C)
					copyButton.fire();
				else if(event.getCode() == KeyCode.V)
					pasteButton.fire();
			}
		});

		undoButton.setOnAction(event -> {
			canvasState.undo();
			redrawCanvas();
		});

		redoButton.setOnAction(event -> {
			canvasState.redo();
			redrawCanvas();
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	void redrawCanvas() {
		canvasState.updateUndoPane(undoPane);
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == canvasState.getSelectedFigure()) {
				gc.setStroke(Color.RED);
			}
			else {
				gc.setStroke(figure.getFormat().getBorderColor());
			}
			gc.setFill(figure.getFormat().getFillColor());
			gc.setLineWidth(figure.getFormat().getBorderWidth());
			figure.redrawCanvas(gc);
		}
	}

	private void mouseMovedonCanvas(MouseEvent event){
		Point eventPoint = new Point(event.getX(), event.getY());
		boolean found = false;
		StringBuilder label = new StringBuilder();
		for(Figure figure : canvasState.figures()) {
			if(canvasState.figureBelongs(figure, eventPoint)) {
				found = true;
				label.append(figure.toString());
			}
		}
		if(found) {
			statusPane.updateStatus(label.toString());
		} else {
			statusPane.updateStatus(eventPoint.toString());
		}
	}
	private void mouseClickonCanvas(MouseEvent event){
		if(selectionButton.isSelected()) {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder("Se seleccionó: ");
			for (Figure figure : canvasState.figures()) {
				if(canvasState.figureBelongs(figure, eventPoint)) {
					found = true;
					canvasState.selectFigure(figure);
					label.append(figure.toString());
				}
			}
			statusLabelFigureInfo(found,label);
			redrawCanvas();
		}
	}
	private void statusLabelFigureInfo(boolean found,StringBuilder label){
		if (found) {
			statusPane.updateStatus(label.toString());
		} else {
			canvasState.restartSelectedFigure();
			statusPane.updateStatus("Ninguna figura encontrada");
		}
	}
}
