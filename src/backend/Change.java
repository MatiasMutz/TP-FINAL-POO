package backend;

import backend.model.Figure;

public class Change {
    //oldFigure es la figura antes del cambio, newFigure es la figura despues del cambio
    private Figure oldFigure,newFigure;
    private Action action;

    public Change(Figure oldFigure,Figure newFigure,Action action){
        this.oldFigure=oldFigure;
        this.newFigure=newFigure;
        this.action=action;
    }
    public void undo(CanvasState canvasState){
        action.undo(canvasState,oldFigure,newFigure);
    }

    public void redo(CanvasState canvasState){
        action.redo(canvasState,oldFigure,newFigure);
    }

    public String toString(){
        return action.getMessage((oldFigure!=null)?oldFigure:newFigure);
    }

}
