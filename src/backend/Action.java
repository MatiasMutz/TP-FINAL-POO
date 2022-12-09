package backend;

import backend.model.Figure;

public enum Action {
    ADD("Dibujar un "),
    DELETE("Borrar "),
    CHANGECOLOR("Cambiar el color de borde de "),
    CHANGEFILL("Cambiar el color de relleno de "),
    CHANGEBORDERCOLOR("Cambiar el ancho del borde de "),
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
}
