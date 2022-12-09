package backend;

import backend.model.Figure;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class CanvasState {


    private Figure toCopyFigure=null;
    private final List<Figure> list = new ArrayList<>(); //Lo visual tal como aparece

    private LinkedList<Change> done= new LinkedList<>(); //Todos los pasos

    private LinkedList<Change> undone=new LinkedList<>(); //Todo lo desecho

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
    }

    public void addVisual(Figure figure){
        list.add(figure);
    }
    public void removeVisual(Figure figure){
        list.remove(figure);
    }

    //Agregar la figura al canvas y tambien la agrega a la lista de cambios por un undo o redo
    public void addFigure(Figure figure) {
        addVisual(figure);
        addDone(null,figure,Action.ADD);
    }

    //Agrega una figura completamente nueva, por lo tanto desaparecen los elementos a restaurar
    public void addNewFigure(Figure figure) {
        addFigure(figure);
        restartUndone();

    }

    //Borra el elemento de la pantalla pero por un undo o rdo
    public void removeFigure(Figure figure){
        removeVisual(figure);
        addDone(figure,null,Action.DELETE);
    }

    //Accion nueeva, de borrado se pierde la posibilidad de redo si habia
    public void deleteFigure(Figure figure) {
        removeFigure(figure);
        restartUndone();
    }

    //Agrega un cambio a la lista de cambios
    public void addDone(Figure oldFigure,Figure newFigure,Action action){
        done.add(new Change(oldFigure,newFigure,action));
    }
    public void addDone(Change change){
        done.add(change);
    }
    public void addUndone(Figure oldFigure,Figure newFigure,Action action){
        undone.add(new Change(oldFigure,newFigure,action));
    }
    public void addUndone(Change change){
        undone.add(change);;
    }

    private Change getLastDone(){
        if (done.isEmpty()){
            return null;
        }
        return done.removeLast();
    }
    private Change getLastUndone(){
        if (undone.isEmpty()){
            return null;
        }
        return undone.removeLast();
    }

    public void undo(){
        Change change=getLastDone();
        if(change!=null){
            change.undo(this);
            addUndone(change);
        }
    }
    public void redo(){
        Change change=getLastUndone();
        if (change!=null){
            change.redo(this);
            addDone(change);
        }
    }

    private void restartUndone(){
        undone=new LinkedList<>();
    }

    public Figure getToCopyFigure() {
        return toCopyFigure;
    }

    public void setToCopyFigure(Figure figure){
        toCopyFigure=figure;
    }

    public void restartToCopyFigure(){
        setToCopyFigure(null);
    }

    public int toUndoAvailable(){
        return done.size();
    }
    public int toRedoAvailable(){
        return undone.size();
    }

    public String getUndoMessage(){
        if (done.isEmpty()){
            return "nada";
        }
        return done.peekLast().toString();
    }
    public String getRedoMessage(){
        if (undone.isEmpty()){
            return "nada";
        }
        return undone.peekLast().toString();
    }


}
