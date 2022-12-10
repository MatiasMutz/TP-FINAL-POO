package backend;

import backend.model.Figure;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class CanvasState {


    private Figure toCopyFigure=null;
    private final List<Figure> list = new ArrayList<>(); //Lo visual tal como aparece

    private ArrayDeque<Change> done= new ArrayDeque<>(); //Todos los pasos

    private ArrayDeque<Change> undone=new ArrayDeque<>(); //Todo lo deshecho

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
        addDone(null,figure,Action.ADD);
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
        addDone(figure,null,Action.DELETE);
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
        return done.pollLast();
    }
    //Quita el ultimo elemento de la lista de desechos si es que hay
    private Change getLastUndone(){
        if (undone.isEmpty()){
            return null;
        }
        return undone.pollLast();
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
        undone=new ArrayDeque<>();
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


}
