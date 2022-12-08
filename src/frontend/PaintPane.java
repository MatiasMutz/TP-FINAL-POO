package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.buttons.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.event.MouseEvent;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	SpecialButton selectionButton = new SpecialButton("Seleccionar");
	SpecialButton rectangleButton = new RectangleButton("Rectángulo");
	SpecialButton circleButton = new CircleButton("Círculo");
	SpecialButton squareButton = new SquareButton("Cuadrado");
	SpecialButton ellipseButton = new EllipseButton("Elipse");
	SpecialButton deleteButton = new SpecialButton("Borrar");

	Label sliderLabel = new Label("Borde");
	Label fillLabel = new Label("Relleno");
	Slider slider = new Slider(1, 50, 1);
	final ColorPicker borderColorPicker = new ColorPicker(Color.GREEN);
	final ColorPicker fillColorPicker = new ColorPicker(Color.GREEN);
	final Label coloredText = new Label("Colors");



	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		SpecialButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
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
		gc.setLineWidth(1);

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
					newFigure=button.newFigure(startPoint,endPoint);
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

		setLeft(buttonsBox);
		setRight(canvas);
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(fillColor);
			figure.redrawCanvas(gc);

		}
	}

	boolean figureBelongs(Figure figure, Point eventPoint) {
		boolean found = figure.figureBelongs(eventPoint);
		return found;

	}

	private String createRGBString(Color c) {
		return String.format("-fx-base: rgb(%f,%f,%f);", (c.getRed() * 255),
				(c.getGreen() * 255), (c.getBlue() * 255));
	}

}
