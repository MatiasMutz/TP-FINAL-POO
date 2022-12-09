package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.buttons.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Translate;

import java.awt.event.MouseEvent;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

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
	Slider slider = new Slider(1, 50, 1);
	final ColorPicker borderColorPicker = new ColorPicker(Color.GREEN);
	final ColorPicker fillColorPicker = new ColorPicker(Color.GREEN);
	final Label coloredText = new Label("Colors");

	private Format copyFormat=null, newFormat=null;

	Translate translate = new Translate();

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
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
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if(startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}

			Figure newFigure = null;
			for(SpecialButton button: toolsArr){
				if(button.isSelected()){
					newFigure=button.newFigure(startPoint,endPoint,new Format(fillColorPicker.getValue(),borderColorPicker.getValue(),slider.getValue()));
					if(newFigure!=null){
						canvasState.addFigure(newFigure);
						startPoint = null;
						redrawCanvas();
						return;
					}
				}
			}
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Figure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if(figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure = figure;
						if(copyFormat!=null){
							selectedFigure.setFormat(copyFormat);
							copyFormat=null;
						}
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});



		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if(selectedFigure!=null){
					selectedFigure.moveX(diffX);
					selectedFigure.moveY(diffY);
				}
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});


		copyFormatButton.setOnAction(event->{
			if (selectedFigure!=null){
				copyFormat=new Format(selectedFigure.getFormat());
			}

		});

		borderColorPicker.setOnAction(event->updateSelectedFormat(selectedFigure));
		fillColorPicker.setOnAction(event->updateSelectedFormat(selectedFigure));
		slider.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue){
				updateSelectedFormat(selectedFigure);
			}
		});



		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void updateSelectedFormat(Figure selectedFigure){
		if(selectedFigure!=null){
			selectedFigure.setFormat(new Format(fillColorPicker.getValue(), borderColorPicker.getValue(), slider.getValue()));
		}
		redrawCanvas();
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
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

	boolean figureBelongs(Figure figure, Point eventPoint) {
		boolean found = figure.figureBelongs(eventPoint);
		return found;

	}

}
