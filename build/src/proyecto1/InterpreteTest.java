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

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InterpreteTest {

    @Test
    public void testOperacionesAritmeticas() {
        Interprete interprete = new Interprete();
        assertEquals("7", interprete.Evaluar("(+ 3 4)", 8), "La suma de 3 y 4 debe ser 7.");
        assertEquals("1", interprete.Evaluar("(- 4 3)", 9), "La resta de 4 y 3 debe ser 1.");
        assertEquals("12", interprete.Evaluar("(* 3 4)", 10), "La multiplicación de 3 por 4 debe ser 12.");
        assertEquals("2", interprete.Evaluar("(/ 4 2)", 11), "La división de 4 entre 2 debe ser 2.");
    }

    @Test
    public void testQuote() {
        Interprete interprete = new Interprete();
        assertEquals("(a b c)", interprete.Evaluar("(quote (a b c))", 12),
                "QUOTE debe retornar la expresión sin evaluar.");
    }

    @Test
    public void testPredicados() {
        assertTrue(Interprete.evaluar("(atom 'a)"), "ATOM debe retornar verdadero para 'a'.");
        assertFalse(Interprete.evaluar("(listp 'a)"), "LISTP debe retornar falso para 'a'.");
        assertTrue(Interprete.evaluar("(equal 5 5)"), "EQUAL debe retornar verdadero para dos números iguales.");
        assertTrue(Interprete.evaluar("(< 3 5)"), "El predicado '<' debe retornar verdadero para 3 < 5.");
        assertFalse(Interprete.evaluar("(> 3 5)"), "El predicado '>' debe retornar falso para 3 > 5.");
    }


}
