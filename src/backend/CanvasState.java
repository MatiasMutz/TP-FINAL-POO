package backend;

import backend.buttons.SpecialButton;
import backend.model.Figure;
import backend.model.Format;
import backend.model.Point;
import frontend.UndoPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.*;

public class CanvasState {
    private Figure toCopyFigure = null, selectedFigure = null;
    private Point startPoint = null;
    private Format copyFormat = null;
    private final List<Figure> list = new ArrayList<>(); //Lo visual tal como aparece

    private LinkedList<Change> done= new LinkedList<>(); //Todos los pasos

    private LinkedList<Change> undone=new LinkedList<>(); //Todo lo deshecho

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
    }

    //-------------------------

    //Agrega la figura al canvas
    public void addVisual(Figure figure){
        list.add(figure);
    }

    //Agregar la figura al canvas y tambien la agrega a la lista de cambios por un undo o redo
    public void addFigure(Figure figure) {
        addVisual(figure);
        addDone(null,figure.getCopy(),Action.ADD);
    }

    //Agrega una figura completamente nueva, por lo tanto desaparecen los elementos a restaurar
    public void addNewFigure(Figure figure) {
        addFigure(figure);
        restartUndone();

    }
    //-----------------------------
    //Borra la figura del canvas
    public void removeVisual(Figure figure){
        list.remove(figure);
    }

    //Borra el elemento de la pantalla pero por un undo o rdo
    public void removeFigure(Figure figure){
        removeVisual(figure);
        addDone(figure.getCopy(),null,Action.DELETE);
    }

    //Accion nueeva, de borrado se pierde la posibilidad de redo si habia
    public void deleteFigure(Figure figure) {
        removeFigure(figure);
        restartUndone();
    }

    //------------------------------

    //Agrega un cambio a la lista de cambios
    public void addDone(Figure oldFigure,Figure newFigure,Action action){
        done.add(new Change(oldFigure,newFigure,action));
    }
    public void addDone(Change change){
        done.add(change);
    }
    //-------------------------------------
    //Agrega un cambio a la lista de deshechos
    public void addUndone(Figure oldFigure,Figure newFigure,Action action){
        undone.add(new Change(oldFigure,newFigure,action));
    }
    public void addUndone(Change change){
        undone.add(change);
    }
    //--------------------------------------
    //Quita el ultimo elemento de la lista de cambios si es que hay
    private Change getLastDone(){
        if (done.isEmpty()){
            return null;
        }
        return done.removeLast();
    }
    //Quita el ultimo elemento de la lista de desechos si es que hay
    private Change getLastUndone(){
        if (undone.isEmpty()){
            return null;
        }
        return undone.removeLast();
    }
    //-----------------------------------
    //Deshace el ultimo cambio
    public void undo(){
        Change change=getLastDone();
        if(change!=null){
            change.undo(this);
            addUndone(change);
        }
    }

    //Rehace el ultimo cambio que fue desecho
    public void redo(){
        Change change=getLastUndone();
        if (change!=null){
            change.redo(this);
            addDone(change);
        }
    }
    //----------------------------
    // Vacia la lista de desechos
    public void restartUndone(){
        undone=new LinkedList<>();
    }

    //Devuelve la figura que esta para ser copiada
    public Figure getToCopyFigure() {
        return toCopyFigure;
    }
    //Setea la figura que esta para ser copiada
    public void setToCopyFigure(Figure figure){
        toCopyFigure=figure;
    }

    //Deja vacia la figura que esta para ser copiada, osea no hay nada para copiar
    public void restartToCopyFigure(){
        setToCopyFigure(null);
    }

    //Devuelve la cantidad de elementos que se pueden deshacer
    public int toUndoAvailable(){
        return done.size();
    }
    //Devuelve la cantidad de elementos que se pueden rehacer
    public int toRedoAvailable(){
        return undone.size();
    }
    //-------------------------
    //Devuelve el mensaje que indica lo que se deshace
    public String getUndoMessage(){
        if (done.isEmpty()){
            return "";
        }
        return done.peekLast().toString();
    }
    //Devuelve el mensaje que indica lo que se rehace
    public String getRedoMessage(){
        if (undone.isEmpty()){
            return "";
        }
        return undone.peekLast().toString();
    }

    public void pasteFigure(){
        if(toCopyFigure!=null){
            Figure figure = toCopyFigure.centerFigure();
            addVisual(figure);
            addDone(null,figure,Action.PASTEFIGURE);
            restartToCopyFigure();
            restartUndone();
        }
    }


    public boolean figureBelongs(Figure figure, Point eventPoint) {
        boolean found = figure.figureBelongs(eventPoint);
        return found;
    }

    public void addNewFigure(MouseEvent event, SpecialButton[] toolsArr, Color fill, Color border, double sliderValue){
        Point endPoint = new Point(event.getX(), event.getY());
        if(startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
            return ;
        }
        Figure newFigure = null;
        for(SpecialButton button: toolsArr){
            if(button.isSelected()){
                newFigure=button.newFigure(startPoint,endPoint,new Format(fill, border, sliderValue));
                if(newFigure!=null){
                    addNewFigure(newFigure);
                    startPoint = null;
                    return;
                }
            }
        }
    }

    public void selectFigure(Figure figure){
        if(figure != null){
            selectedFigure = figure;
            if(copyFormat!=null){
                Figure oldFigure=selectedFigure.getCopy();
                selectedFigure.setFormat(copyFormat);
                addDone(oldFigure,selectedFigure.getCopy(),Action.COPYFORMAT);
                copyFormat=null;
            }
        }
    }

    public void mouseDraggedonCanvas(MouseEvent event){
        Point eventPoint = new Point(event.getX(), event.getY());
        double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
        double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
        if(selectedFigure!=null){
            moveFigure(diffX,diffY);
        }
    }
    public void moveFigure(double diffX,double diffY){
        selectedFigure.moveX(diffX);
        selectedFigure.moveY(diffY);
    }
    public void deleteFigure(){
        if (selectedFigure != null) {
            deleteFigure(selectedFigure);
            selectedFigure = null;
        }
    }

    public void copyFormatFigure(){
        if(selectedFigure != null){
            copyFormat = new Format(selectedFigure.getFormat());
        }
    }

    public void copyFigure(){
        CopyFunction(Action.COPYFIGURE);
    }

    public void cutFigure(){
        CopyFunction(Action.CUTFIGURE);
        removeVisual(selectedFigure);
    }

    private void CopyFunction(Action action){
        if(selectedFigure != null){
            setToCopyFigure(selectedFigure.getCopy());
            addDone(selectedFigure.getCopy(),null,action);
        }
    }

    public void updateUndoPane(UndoPane undoPane){
        undoPane.updateUndoPane(getRedoMessage(), toRedoAvailable(), getUndoMessage(), toUndoAvailable());
    }

    public void updateSelectedFormat(Action action, Color fill, Color border, double sliderValue){
        if(selectedFigure!=null){
            Figure oldFigure=selectedFigure.getCopy();
            action.updateSelectedFormat(selectedFigure,new Format(fill, border, sliderValue));
            //selectedFigure.setFormat(new Format(fillColorPicker.getValue(), borderColorPicker.getValue(), slider.getValue()));
            addDone(oldFigure,selectedFigure.getCopy(),action);
            restartUndone();
        }
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void restartStartPoint(){
        startPoint = null;
    }
    public void restartSelectedFigure(){
        selectedFigure = null;
    }
    public Figure getSelectedFigure() {
        return selectedFigure;
    }

    public void setSelectedFigure(Figure selectedFigure) {
        this.selectedFigure = selectedFigure;
    }
}
