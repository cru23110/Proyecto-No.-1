/**
* Universidad del Valle de Guatemala
* @author Isabella Obando - 23074
* @author Mia Fuentes - 23775
* @author Roberto Barreda -  23354
* @author Juan Cruz - 23110
* @author Linda Chen - 23173
* @author Nadissa Vela - 23764
* @description Clase CalculadoraInterpreter. Encargada de realizar las operaciones aritmeticas
* @date creación 23/01/2024 última modificación 06/02/2024
*/
package proyecto1;
import java.util.HashMap;
import java.util.Set;

public class InterpreterCalculator {

    public InterpreterCalculator() {
    }

    public int Evaluate(String expresion, HashMap<String, Integer> variables) {
        String[] dataArray = expresion.split(" ");
        StackArrayList<Integer> stack = new StackArrayList<>();
        int resultado = 0;

        for (String data : dataArray) {
            if (!data.trim().isEmpty()) {
                if (isOperator(data)) {
                    if (stack.count() < 2) {
                        throw new IllegalArgumentException("La operación es inválida, deben de haber más de un operando");
                    }
                    int dato2 = stack.pull();
                    int dato1 = stack.pull();
                    switch (data) {
                        case "+":
                            resultado = dato1 + dato2;
                            break;
                        case "-":
                            resultado = dato1 - dato2;
                            break;
                        case "/":
                            if (dato2 == 0) {
                                throw new IllegalArgumentException("No se puede dividir entre 0");
                            }
                            resultado = dato1 / dato2;
                            break;
                        case "*":
                            resultado = dato1 * dato2;
                            break;
                    }
                    stack.push(resultado);
                } else {
                    int numero;
                    if (variables.containsKey(data)) {
                        numero = variables.get(data);
                    } else {
                        try {
                            numero = Integer.parseInt(data);
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("El operando '" + data + "' no es un número válido");
                        }
                    }
                    stack.push(numero);
                }
            }
        }

        if (stack.count() != 1) {
            throw new IllegalArgumentException("Expresión inválida");
        }

        return stack.pull();
    }

    private boolean isOperator(String data) {
        String operandos = "+-/*";
        return operandos.contains(data);
    }
}
