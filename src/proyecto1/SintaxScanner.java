/**
* Universidad del Valle de Guatemala
* @author Isabella Obando - 23074
* @author Mia Fuentes - 23775
* @author Roberto Barreda -  23354
* @author Juan Cruz - 23110
* @author Linda Chen - 23173
* @author Nadissa Vela - 23764
* @description SintaxScanner. Encargada de evaluar y devolver un entero dependiendo de la expresion que se le envie
* @date creación 23/01/2024 última modificación 06/02/2024
*/
package proyecto1;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SintaxScanner {
    
    public static int getState(String expresion) {
        if (evaluateSetq(expresion)) {
            return 1;
        } else if (evaluateAtom(expresion)) {
            return 2;
        } else if (evaluateList(expresion)) {
            return 3;
        } else if (evaluateListp(expresion)) {
            return 4;
        } else if (evaluateEqual(expresion)) {
            return 5;
        } else if (evaluateDescendente(expresion)) {
            return 6;
        } else if (evaluateAscendente(expresion)) {
            return 7;
        } else if (evaluateSuma(expresion)) {
            return 8;
        } else if (evaluateResta(expresion)) {
            return 9;
        } else if (evaluateMultiplicacion(expresion)) {
            return 10;
        } else if (evaluateDivision(expresion)) {
            return 11;
        } else if (evaluateQuote(expresion)) {
            return 12;
        } else if (evaluateCond(expresion)) {
            return 13;
        } else if (evaluateDefun(expresion)) {
            return 14;
        } else {
            return 0;
        }
    }

    private static boolean evaluate(String regex, String expresion) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expresion);
        return matcher.find();
    }

    private static boolean evaluateSetq(String expresion) {
        String regex = "^[ ]*[(][ ]*setq[ ]+[\\w]+[ ]+([0-9]+[ ]*)+[)][ ]*$|^[ ]*[(][ ]*setq[ ]+[\\w]+[ ]+[(][ ]*([0-9]+[ ]*)+[ ]*[)][ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateAtom(String expresion) {
        String regex = "^[ ]*[(][ ]*atom[ ]+[A-Za-z0-9]+[ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateList(String expresion) {
        String regex = "^[ ]*[(][ ]*list[ ]+[A-Za-z0-9 ]+[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateListp(String expresion) {
        String regex = "^[ ]*[(][ ]*listp[ ]+[A-Za-z0-9 ]+[)][ ]*$|^[ ]*[(][ ]*listp[ ]+[(][A-Za-z0-9 ]+[)][ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateEqual(String expresion) {
        String regex = "^[ ]*[(][ ]*equal[ ]+[A-Za-z0-9]+[ ]+[A-Za-z0-9]+[ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateDescendente(String expresion) {
        String regex = "^[ ]*[(][ ]*>[ ]+[A-Za-z0-9 ]+[ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateAscendente(String expresion) {
        String regex = "^[ ]*[(][ ]*<[ ]+[A-Za-z0-9 ]+[ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateSuma(String expresion) {
        String regex = "^[ ]*[(][ ]*[+][ ]+[A-Za-z0-9 ]+[ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateResta(String expresion) {
        String regex = "^[ ]*[(][ ]*[-][ ]+[A-Za-z0-9 ]+[ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateMultiplicacion(String expresion) {
        String regex = "^[ ]*[(][ ]*[*][ ]+[A-Za-z0-9 ]+[ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateDivision(String expresion) {
        String regex = "^[ ]*[(][ ]*[\\/][ ]+[A-Za-z0-9 ]+[ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateQuote(String expresion) {
        String regex = "^[ ]*[(][ ]*quote[ ]+[A-Za-z0-9][ ]*[)][ ]*$|^[ ]*[(][ ]*quote[ ]+[(][ ]*[A-Za-z0-9 ]+[ ]*[)][ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateCond(String expresion) {
        String regex = "^[ ]*[(]cond[ ]+([(][ ]*[(][ ]*[\\=\\<\\>][ ]+[A-Za-z0-9][ ]+[A-Za-z0-9][ ]*[)][ ]*[(][ ]*([A-Za-z0-9]+[ ]*)+[ ]*[)][ ]*[)] *)+[ ]*[)][ ]*$";
        return evaluate(regex, expresion);
    }

    private static boolean evaluateDefun(String expresion) {
        String regex = "^[ ]*[(][ ]*defun[ ]+[A-Za-z0-9]+[ ]*[(][ ]*([A-Za-z0-9]+[ ]*)+[ ]*[)][ ]*[(]*[^()\\\"']*.*[)][ ]*$";
        return evaluate(regex, expresion);
    }
}
