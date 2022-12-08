package backend.model;

import javafx.scene.canvas.GraphicsContext;

public interface Figure {
     void moveX(double distance);
     void moveY(double distance);

     void redrawCanvas(GraphicsContext gc);

     boolean figureBelongs(Point eventPoint);


}
