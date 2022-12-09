package backend.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class Figure {

     private Format format;

     public Figure(Format format){
          setFormat(format);
     }
     public void setFormat(Format format) {
          this.format = format;
     }

     public Format getFormat(){
          return format;
     }

     public abstract void moveX(double distance);
     public abstract void moveY(double distance);

     public abstract void redrawCanvas(GraphicsContext gc);

     public abstract boolean figureBelongs(Point eventPoint);

     public abstract Figure centerFigure();




}
