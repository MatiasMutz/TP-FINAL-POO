package backend;

import backend.model.Figure;

public class Change {
    private Figure oldFigure,newFigure;
    private Action action;

    public Change(Figure oldFigure,Figure newFigure,Action action){
        this.oldFigure=oldFigure;
        this.newFigure=newFigure;
        this.action=action;
    }

    public Figure getOldFigure(){
        return oldFigure;
    }

    public Figure getNewFigure(){return newFigure};

    public Action getAction(){
        return action;
    }
}
