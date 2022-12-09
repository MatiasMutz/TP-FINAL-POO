package backend;

import backend.model.Figure;

import java.util.List;

public enum Action {
    MOVE("Mover un"){
        @Override
        public void redo(CanvasState canvasState, Figure oldFigure, Figure newFigure){
            Action.removeF1addF2(canvasState,oldFigure,newFigure);
        }
        @Override //Como agregue(ADD) el undo tiene que borrar (el oldFigure es null)
        public void undo(CanvasState canvasState, Figure oldFigure, Figure newFigure) {
            Action.removeF1addF2(canvasState,newFigure,oldFigure);
        }
    },
    ADD("Dibujar un "){

        @Override
        public void redo(CanvasState canvasState, Figure oldFigure, Figure newFigure){
            canvasState.addVisual(newFigure);
        }
        @Override //Como agregue(ADD) el undo tiene que borrar (el oldFigure es null)
        public void undo(CanvasState canvasState, Figure oldFigure, Figure newFigure) {
            canvasState.removeVisual(newFigure);
        }
    },
    DELETE("Borrar "){

        @Override //Yo habia borrado, lee hice un undo entonces volvio a estar lo que sria oldFigure,
        // entonce ahora quiero que se vuelva a borrar oldFigure
        public void redo(CanvasState canvasState, Figure oldFigure, Figure newFigure){
            canvasState.removeVisual(oldFigure);
        }
        @Override //Como borre(DELETE) el undo tiene que agregar (el newFigure es null)
        public void undo(CanvasState canvasState, Figure oldFigure, Figure newFigure) {
            canvasState.addVisual(oldFigure);
        }
    },
    CHANGEBORDERCOLOR("Cambiar el color de borde de un"){
        @Override
        public void redo(CanvasState canvasState, Figure oldFigure, Figure newFigure){
            removeF1addF2(canvasState,oldFigure,newFigure);
        }
        @Override
        public void undo(CanvasState canvasState, Figure oldFigure, Figure newFigure) {
            Action.removeF1addF2(canvasState,newFigure,oldFigure);
        }
    },
    CHANGEFILL("Cambiar el color de relleno de un"){
        @Override
        public void redo(CanvasState canvasState, Figure oldFigure, Figure newFigure){
            removeF1addF2(canvasState,oldFigure,newFigure);
        }
        @Override
        public void undo(CanvasState canvasState, Figure oldFigure, Figure newFigure) {
            Action.removeF1addF2(canvasState,newFigure,oldFigure);
        }
    },
    CHANGEBORDER("Cambiar el ancho del borde de un"){
        @Override
        public void redo(CanvasState canvasState, Figure oldFigure, Figure newFigure){
            removeF1addF2(canvasState,oldFigure,newFigure);
        }
        @Override
        public void undo(CanvasState canvasState, Figure oldFigure, Figure newFigure) {
            Action.removeF1addF2(canvasState,newFigure,oldFigure);
        }
    },
    COPYFORMAT("Copiar el formato de un"){
        @Override
        public void redo(CanvasState canvasState, Figure oldFigure, Figure newFigure){
            removeF1addF2(canvasState,oldFigure,newFigure);
        }
        @Override
        public void undo(CanvasState canvasState, Figure oldFigure, Figure newFigure) {
            Action.removeF1addF2(canvasState,newFigure,oldFigure);
        }
    },
    CUTFIGURE("Cortar "){
        @Override
        public void redo(CanvasState canvasState, Figure oldFigure, Figure newFigure){
            canvasState.removeVisual(oldFigure);
            canvasState.setToCopyFigure(oldFigure);
        }
        @Override
        public void undo(CanvasState canvasState, Figure oldFigure, Figure newFigure) {
            canvasState.addVisual(oldFigure);
            canvasState.restartToCopyFigure();
        }
    },
    COPYFIGURE("Copiar " ){
        @Override
        public void redo(CanvasState canvasState, Figure oldFigure, Figure newFigure){
            canvasState.setToCopyFigure(oldFigure);
        }
        @Override
        public void undo(CanvasState canvasState, Figure oldFigure, Figure newFigure) {
            canvasState.restartToCopyFigure();
        }
    },
    PASTEFIGURE("Pegar "){
        @Override
        public void redo(CanvasState canvasState, Figure oldFigure, Figure newFigure){
            canvasState.addVisual(newFigure);
        }
        @Override
        public void undo(CanvasState canvasState, Figure oldFigure, Figure newFigure) {
            canvasState.removeVisual(newFigure);
        }
    };

    private String message;
    Action(String message){
        this.message = message;
    }

    public String getMessage(Figure figure){
        return message + figure.getName();
    }
    public abstract void redo(CanvasState canvasState,Figure oldFigure,Figure newFigure);
    public abstract void undo(CanvasState canvasState, Figure oldFigure,Figure newFigure);

    static private void removeF1addF2(CanvasState canvasState,Figure figure1,Figure figure2){

        canvasState.removeVisual(figure1);
        canvasState.addVisual(figure2);
    }
}
