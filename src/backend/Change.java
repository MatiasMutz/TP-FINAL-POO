package backend;

import backend.model.Figure;

public class Change {
    private Figure figure;
    private Action action;

    public Change(Figure figure,Action action){
        this.figure=figure;
        this.action=action;
    }

    public Figure getFigure(){
        return figure;
    }

    public Action getAction(){
        return action;
    }
}
