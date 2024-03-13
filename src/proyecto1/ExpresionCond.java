/**
* Universidad del Valle de Guatemala
* @author Isabella Obando - 23074
* @author Mia Fuentes - 23775
* @author Roberto Barreda -  23354
* @author Juan Cruz - 23110
* @author Linda Chen - 23173
* @author Nadissa Vela - 23764
* @description Clase Interprete. Clase responsable que funcione el interprete lisp
* @date creación 23/01/2024 última modificación 06/02/2024
*/
package proyecto1;

public class ExpresionCond {
    String condicion;
    String expresion;

    public ExpresionCond(String condicion, String expresion) {
        this.condicion = condicion;
        this.expresion = expresion;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }
   
}
