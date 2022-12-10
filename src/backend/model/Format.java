package backend.model;

import javafx.scene.paint.Color;

public class Format {
    private Color fillColor,borderColor;
    private double borderWidth;

    public Format(Color fillColor,Color borderColor,double borderWidth){
        setBorderColor(borderColor);
        setBorderWidth(borderWidth);
        setFillColor(fillColor);
    }
    public Format(Format format){
        setBorderColor(format.getBorderColor());
        setBorderWidth(format.getBorderWidth());
        setFillColor(format.getFillColor());
    }

    public Color getFillColor(){
        return fillColor;
    }
    public Color getBorderColor(){
        return borderColor;
    }
    public double getBorderWidth(){
        return borderWidth;
    }

    public void setFillColor(Color fillColor){
        this.fillColor=fillColor;
    }
    public void setBorderColor(Color borderColor){
        this.borderColor=borderColor;
    }
    public void setBorderWidth(double borderWidth){
        this.borderWidth=borderWidth;
    }

    public String toString(){
        return String.format("[%s %s %f] ",fillColor.toString(),borderColor.toString(), borderWidth);
    }
}
