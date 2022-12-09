package backend;

import backend.model.Figure;

public enum Action {
    ADD("Dibujar un "),
    DELETE("Borrar "),
    CHANGEBORDERCOLOR("Cambiar el color de borde de "),
    CHANGEFILL("Cambiar el color de relleno de "),
    CHANGEBORDER("Cambiar el ancho del borde de "),
    COPYFORMAT("Copiar el formato de "),
    CUTFIGURE("Cortar "),
    COPYFIGURE("Copiar " ),
    PASTEFIGURE("Pegar ");

    private String message;
    Action(String message){
        this.message = message;
    }

    public String getMessage(Figure figure){
        return message + figure.getName();
    }
    public abstract void doo(Figure figure);
    public abstract void undo(Figure figure);
}
