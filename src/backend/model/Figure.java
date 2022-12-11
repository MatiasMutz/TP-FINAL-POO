package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Figure {

     private Format format;
     private Integer figureID;
     private static int ID=0;
     public Figure(Format format){
          setFormat(format);
          updateID();
          figureID=ID;
     }
     public Figure(Format format,int figureID){
          this.format=format;
          this.figureID=figureID;
     }
     private void updateID(){
           ID++;
     }
     public void setFormat(Format format) {
          this.format = format;
     }

     public void setFillColor(Color fillColor){
          format.setFillColor(fillColor);
     }
     public void setBorderColor(Color borderColor){
          format.setBorderColor(borderColor);
     }
     public void setBorderWidth(double borderWidth){
          format.setBorderWidth(borderWidth);
     }

     public Format getFormat(){
          return format;
     }

     public abstract void moveX(double distance);
     public abstract void moveY(double distance);

     public abstract void redrawCanvas(GraphicsContext gc);

     public abstract boolean figureBelongs(Point eventPoint);

     public abstract Figure centerFigure();

     public abstract String getName();

     public abstract Figure getCopy();

     public Integer getFigureID() {
          return figureID;
     }
}
