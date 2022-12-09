package backend;

import backend.model.Figure;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class CanvasState {


    private final List<Figure> list = new ArrayList<>();

    private LinkedList<Change> done= new LinkedList<>();

    private LinkedList<Change> undone=new LinkedList<>();

    public void addFigure(Figure figure) {
        list.add(figure);
        addDone(figure,Action.ADD);
    }

    public void deleteFigure(Figure figure) {
        list.remove(figure);
        addDone(figure,Action.DELETE);
    }

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
    }

    public void addDone(Figure figure,Action action){
        done.add(new Change(figure,action));
    }

    public void removeDone(){
        Change change=done.pop();
        if(change!=null) {
            undone.add(change);
        }
    }

    public void removeUndone(){
        Change change=undone.pop();
        if(change!=null) {
            done.add(change);
        }
    }





}
