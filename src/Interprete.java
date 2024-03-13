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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interprete {
    private HashMap<String, Integer> variables; // Variables numéricas
    private HashMap<String, String> listas; // Variables tipo lista
    private HashMap<String, String> funciones; // Funciones definidas por el usuario
    public InterpreterCalculator calculadora; // Instancia de la clase CalculadoraInterpreter

    public Interprete() {
        this.variables = new HashMap<>();
        this.listas = new HashMap<>();
        this.funciones = new HashMap<>();
        this.calculadora = new InterpreterCalculator();
    }

    // Método principal para evaluar una expresión
    public void Evaluar(String expresion, int funcion) {
        if (funcion == 0) {
            System.out.println("Expresion invalida");
        } else if (funcion == 1) {
            String nameVar = setq(expresion);
            System.out.println("Valor de " +nameVar + ": "+variables.get(nameVar));
        } else if (funcion == 2) {
            System.out.println(atom(expresion));
        } else if (funcion == 3) {
            System.out.println(list(expresion));
        } else if (funcion == 4) {
            System.out.println(listp(expresion));
        } else if (funcion == 5) {
            System.out.println(equal(expresion));
        } else if (funcion == 6) {
            System.out.println(descendente(expresion));
        } else if (funcion == 7) {
            System.out.println(ascendente(expresion));
        } else if (funcion >= 8 && funcion <= 11) {
            expresion = expresion.trim().replaceAll("\\s+", " ");
            StringBuilder editado = new StringBuilder(expresion);
            editado.deleteCharAt(0);
            editado.deleteCharAt(editado.length() - 1);
            expresion = new String(editado);
            System.out.println(calculadora.Evaluate(PrefixToPosfix.convPrefixToPosfix(expresion), variables));
        } else if (funcion == 12) {
            System.out.println(Quote(limpiar(expresion, "q", "e", 4)));
        } else if (funcion == 13) {
            expresion = limpiar(expresion, "c", "d", 3);
            expresion = expresion.replaceAll("\\)\\) \\(\\(", "))\n((");
            System.out.println("que eres "+expresion);
            System.out.println(Cond(expresion));
        } else if (funcion == 14) {
            defun(expresion, this.funciones);
        }
    }
    
    // Método para evaluar la expresión 'Quote'
    public String Quote(String expresion) {
        // Devuelve la expresión sin modificar
        return expresion;
    }


    // Método para evaluar la expresión 'Cond'
    /**
     * @param expresion
     * @return
     */
    public String Cond(String expresion) {
        // Separa las cláusulas de condición
        String[] clausulas = expresion.split("\\)\\) \\(\\(");
        // Itera sobre las cláusulas
        for (String clausula : clausulas) {
            // Separa la condición y la expresión
            String[] partes = clausula.split("\\) \\(");
            String condicion = partes[0].replace("(", "").trim();
            // Cambia el nombre de la variable para evitar conflictos
            String expresionAEvaluar = partes[1].replace(")", "").trim();
            // Si la condición es verdadera, devuelve la expresión evaluada
            if (EvaluarCondicion(condicion)) {
                return expresionAEvaluar;
            }
        }
        // Si ninguna condición es verdadera, devuelve una cadena vacía
        return "";
    }

   
    
    
    // Método auxiliar para evaluar una condición
private boolean EvaluarCondicion(String condicion) {
    // Elimina espacios en blanco extra y divide la condición en partes
    String[] partes = condicion.trim().split(" ");

    // Verifica que la condición tenga el formato esperado (por ejemplo, dos operandos y un operador)
    if (partes.length != 3) {
        // Si la condición no tiene el formato esperado, devuelve false
        return false;
    }

    // Obtiene los operandos y el operador de la condición
    String operando1 = partes[0];
    String operador = partes[1];
    String operando2 = partes[2];

    // Realiza la evaluación de la condición según el operador
    switch (operador) {
        case "==":
            // Si los operandos son iguales, devuelve true
            return operando1.equals(operando2);
        case "!=":
            // Si los operandos son diferentes, devuelve true
            return !operando1.equals(operando2);
        case ">":
            // Si el primer operando es mayor que el segundo, devuelve true
            try {
                double op1 = Double.parseDouble(operando1);
                double op2 = Double.parseDouble(operando2);
                return op1 > op2;
            } catch (NumberFormatException e) {
                // Manejo de error si los operandos no son números
                return false;
            }
        case "<":
            // Si el primer operando es menor que el segundo, devuelve true
            try {
                double op1 = Double.parseDouble(operando1);
                double op2 = Double.parseDouble(operando2);
                return op1 < op2;
            } catch (NumberFormatException e) {
                // Manejo de error si los operandos no son números
                return false;
            }
        default:
            // Si el operador no es válido, devuelve false
            return false;
    }
}


    // Método para dividir una expresión en jerarquías
    public ArrayList<String> Jerarqui(String exp) {
        ArrayList<String> jerarquias = new ArrayList<>();
        boolean bandera = true;
        int pos = 0;
        String func = "";
        while (bandera) {
            func = "";
            pos = exp.lastIndexOf("(");
            int cont = 0;
            boolean bandera2 = true;
            while (bandera2) {
                if (String.valueOf(exp.charAt(pos + cont)).equals(")")) {
                    bandera2 = false;
                } else {
                    func = func + String.valueOf(exp.charAt(pos + cont));
                    cont++;
                }
            }
            func = func + ")";
            jerarquias.add(func);
            exp = exp.replace(func, "");
            if (pos == 0) {
                bandera = false;
            }
        }
        return jerarquias;
    }
    

    // Métodos para definir variables y listas
    public String setq(String instruccion) {
        instruccion = this.limpiar(instruccion, "s", "q", 3);
        String[] partes = instruccion.split("\\s+");
        String nombreVar = "";
        if (partes.length >= 2) { // Asegurándonos de que hay tanto nombre como valor.
            nombreVar = partes[0];
            try {
                Integer valorVar = Integer.parseInt(partes[1]);
                variables.put(nombreVar, valorVar); // Usando "variables" como el HashMap para almacenar la asignación.
            } catch (NumberFormatException e) {
                System.out.println("Error: El valor proporcionado para SETQ no es un número entero válido.");
            }
        } else {
            System.out.println("Error: Instrucción SETQ mal formada.");
        }
        return nombreVar;
    }
    
    
    

    public static void defun(String expresion, HashMap<String, String> funciones) {
        int parentesis = 0;
        String temp = "";
        for (int i = 2; i < expresion.length(); i++) {
            temp = temp + expresion.charAt(i);
            if (expresion.charAt(i) == '(') {
                parentesis++;
            } else if (expresion.charAt(i) == ')') {
                parentesis--;
            }
            funciones.put("" + expresion.charAt(0), temp);
        }
    }
    

    // Métodos para operaciones básicas y listas
    public String operaciones() {
        String info = "";
        info += "\nPrimera operacion: " + (this.variables.get("x") + this.variables.get("y") + this.variables.get("z"));
        info += "\nSegunda operacion: " + (this.variables.get("z") + this.variables.get("y") - this.variables.get("x"));
        info += "\nTercera operacion: " + (this.variables.get("x") / this.variables.get("y") + this.variables.get("z"));
        return info;
    }
    

    public String list(String instruccion) {
        // Limpiar la instrucción de espacios en blanco
        instruccion = instruccion.trim();
    
        // Verificar si la instrucción es una lista válida
        if (instruccion.startsWith("(") && instruccion.endsWith(")")) {
            // Obtener el contenido de la lista
            String contenido = instruccion.substring(1, instruccion.length() - 1).trim();
            // Verificar si el contenido está vacío
            if (contenido.isEmpty()) {
                return "NIL";
            }
            // Verificar si el contenido está bien formado
            String[] elementos = contenido.split("\\s+");
            for (String elemento : elementos) {
                if (!elemento.matches("[a-zA-Z0-9]+")) {
                    return "NIL";
                }
            }
            return "True";
        } else {
            return "NIL";
        }
    }
    

    // Métodos para operaciones booleanas
    public String atom(String instruccion) {
        instruccion = this.limpiar(instruccion, "a", "m", 3);
        // Verifica si la instrucción es un número
        Pattern pattern = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(instruccion);
        boolean esAtom = matcher.find();
        if (esAtom) {
            return "True";
        } else {
            // Si no es un número, verifica si es una variable o una lista
            Pattern variable = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = variable.matcher(instruccion);
            if (matcher2.find()) {
                try {
                    // Si es una variable, verifica si tiene un valor asignado
                    int valor = this.variables.get(instruccion);
                    return "True";
                } catch (Exception e) {
                    // Si no tiene un valor asignado, verifica si es una lista
                    if (this.listas.containsKey(instruccion)) {
                        return "NIL";
                    } else {
                        return instruccion + " no tiene valor. Por lo que no se puede determinar si es atom o no.";
                    }
                }
            }
            return "NIL";
        }
    }
    

    public String equal(String instruccion) {
        instruccion = this.limpiar(instruccion, "e", "l", 4);
    
        // Verifica si la instrucción contiene dos números separados por un espacio
        Pattern pattern = Pattern.compile("^[0-9]+[ ]+[0-9]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(instruccion);
        boolean sonNumeros = matcher.find();
        String[] split = instruccion.split(" ");
    
        if (sonNumeros) {
            // Si contiene dos números, los compara y devuelve el resultado
            if (Integer.valueOf(split[0]) == Integer.valueOf(split[1])) {
                return "True";
            } else {
                return "NIL";
            }
        } else {
            // Si no contiene dos números, verifica si contiene variables
            Pattern letras = Pattern.compile("^[a-zA-Z]+[ ]+[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = letras.matcher(instruccion);
            boolean sonLetras = matcher2.find();
            if (sonLetras) {
                // Si contiene solo letras, verifica si las variables están definidas y compara sus valores
                try {
                    if (this.variables.containsKey(split[0]) && this.variables.containsKey(split[1])) {
                        int primero = this.variables.get(split[0]);
                        int segundo = this.variables.get(split[1]);
                        if (primero == segundo) {
                            return "True";
                        } else {
                            return "NIL";
                        }
                    } else {
                        return "Uno de los argumentos no existe (variable no definida)";
                    }
                } catch (Exception e) {
                    return "NIL";
                }
            } else {
                // Si contiene letras y números, compara sus valores después de convertirlos si es necesario
                try {
                    Pattern letra = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher3 = letra.matcher(split[0]);
                    boolean esLetra = matcher3.find();
                    if (esLetra) {
                        int primero = this.variables.get(split[0]);
                        int segundo = Integer.valueOf(split[1]);
                        if (primero == segundo) {
                            return "True";
                        } else {
                            return "NIL";
                        }
                    } else {
                        int primero = Integer.valueOf(split[0]);
                        int segundo = this.variables.get(split[1]);
                        if (primero == segundo) {
                            return "True";
                        } else {
                            return "NIL";
                        }
                    }
                } catch (Exception e) {
                    return "Uno de los argumentos no existe (variable no definida)";
                }
            }
        }
    }
    

    public String ascendente(String instruccion) {
        // Elimina los símbolos < y > de la instrucción
        instruccion = this.limpiarSimbolo(instruccion);
    
        // Verifica si la instrucción contiene solo números
        Pattern pattern = Pattern.compile("^[0-9 ]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(instruccion);
        boolean sonNumeros = matcher.find();
        String[] split = instruccion.split(" ");
        Vector<Integer> numeros = new Vector<>();
    
        // Procesa los números o variables presentes en la instrucción
        if (sonNumeros) {
            for (int i = 0; i < split.length; i++) {
                numeros.add(Integer.valueOf(split[i]));
            }
        } else {
            // Si hay letras o variables, obtiene su valor numérico
            Pattern letras = Pattern.compile("^[a-zA-Z ]+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = letras.matcher(instruccion);
            boolean sonLetras = matcher2.find();
            if (sonLetras) {
                try {
                    for (int i = 0; i < split.length; i++) {
                        numeros.add(this.variables.get(split[i]));
                    }
                } catch (Exception e) {
                    // Maneja la excepción si una variable no está definida
                    return "\n" + e;
                }
            } else {
                // Si hay letras y números, procesa cada uno
                for (int i = 0; i < split.length; i++) {
                    Pattern letra = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher3 = letra.matcher(split[i]);
                    boolean esLetra = matcher3.find();
                    if (esLetra) {
                        try {
                            numeros.add(this.variables.get(split[i]));
                        } catch (Exception e) {
                            // Maneja la excepción si una variable no está definida
                            return "\n" + e;
                        }
                    } else {
                        numeros.add(Integer.valueOf(split[i]));
                    }
                }
            }
        }
    
        // Verifica si los números están en orden ascendente
        for (int i = 0; i < numeros.size() - 1; i++) {
            if ((numeros.get(i)) > numeros.get(i + 1)) {
                return "NIL";
            }
        }
        return "True";
    }
    

    public String descendente(String instruccion) {
        // Elimina los símbolos < y > de la instrucción
        instruccion = this.limpiarSimbolo(instruccion);
    
        // Verifica si la instrucción contiene solo números
        Pattern pattern = Pattern.compile("^[0-9 ]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(instruccion);
        boolean sonNumeros = matcher.find();
        String[] split = instruccion.split(" ");
        Vector<Integer> numeros = new Vector<>();
    
        // Procesa los números o variables presentes en la instrucción
        if (sonNumeros) {
            for (int i = 0; i < split.length; i++) {
                numeros.add(Integer.valueOf(split[i]));
            }
        } else {
            // Si hay letras o variables, obtiene su valor numérico
            Pattern letras = Pattern.compile("^[a-zA-Z ]+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = letras.matcher(instruccion);
            boolean sonLetras = matcher2.find();
            if (sonLetras) {
                try {
                    for (int i = 0; i < split.length; i++) {
                        numeros.add(this.variables.get(split[i]));
                    }
                } catch (Exception e) {
                    // Maneja la excepción si una variable no está definida
                    return "\n" + e;
                }
            } else {
                // Si hay letras y números, procesa cada uno
                for (int i = 0; i < split.length; i++) {
                    Pattern letra = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher3 = letra.matcher(split[i]);
                    boolean esLetra = matcher3.find();
                    if (esLetra) {
                        try {
                            numeros.add(this.variables.get(split[i]));
                        } catch (Exception e) {
                            // Maneja la excepción si una variable no está definida
                            return "\n" + e;
                        }
                    } else {
                        numeros.add(Integer.valueOf(split[i]));
                    }
                }
            }
        }
    
        // Verifica si los números están en orden descendente
        for (int i = 0; i < numeros.size() - 1; i++) {
            if ((numeros.get(i)) < numeros.get(i + 1)) {
                return "NIL";
            }
        }
        return "True";
    }
    

    public String listp(String instruccion) {
        // Limpiar la instrucción de espacios en blanco
        instruccion = instruccion.trim();
    
        // Verificar si la instrucción es una lista válida
        if (instruccion.startsWith("(") && instruccion.endsWith(")")) {
            // Obtener el contenido de la lista
            String contenido = instruccion.substring(1, instruccion.length() - 1).trim();
            // Verificar si el contenido está vacío
            if (contenido.isEmpty()) {
                return "True";
            }
            // Verificar si el contenido está bien formado
            String[] elementos = contenido.split("\\s+");
            for (String elemento : elementos) {
                if (!elemento.matches("[a-zA-Z0-9]+")) {
                    return "NIL";
                }
            }
            return "True";
        } else {
            return "NIL";
        }
    }
    

    // Métodos para limpiar expresiones
    public String limpiar(String instruccion, String letraInicio, String letraFinal, int largo) {
        instruccion = instruccion.trim().replaceAll("\\s+", " ");
        StringBuilder editado = new StringBuilder(instruccion);
        editado.deleteCharAt(0);
        editado.deleteCharAt(editado.length() - 1);
        int indexInicio = editado.indexOf(letraInicio);
        int indexFinal = editado.indexOf(letraFinal);
        if ((indexFinal - indexInicio) == largo) {
            editado.delete(indexInicio, indexFinal + 1);
        }
        instruccion = new String(editado).trim();
        instruccion = instruccion.replaceAll("\\s+", " ");
        return instruccion;
    }
    

    public String limpiarSimbolo(String instruccion) {
        instruccion = instruccion.trim().replaceAll("\\s+", " ");
        StringBuilder editado = new StringBuilder(instruccion);
        editado.deleteCharAt(0);
        editado.deleteCharAt(editado.length() - 1);
        for (int i = 0; i < editado.length(); i++) {
            String caracter = String.valueOf(editado.charAt(i));
            if (caracter.equals("<") || caracter.equals(">")) {
                editado.deleteCharAt(i);
            }
        }
        instruccion = new String(editado).trim().replaceAll("\\s+", " ");
        return instruccion;
    }
    
    public String imprimir() {
        return "\n" + String.valueOf(this.variables) + "\n" + String.valueOf(this.listas);
    }
    

    // Otros métodos auxiliares
    public boolean validarFunciones(String expresion) {
        // Verifica si la expresión contiene el patrón de una función (nombre(arg))
        Pattern pattern = Pattern.compile("^[a-zA-Z]+\\(\\w+\\)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expresion);
        return matcher.find();
    }
    

    public void leerFunciones(String expresion) {
        // Extrae el nombre de la función y su argumento
        Pattern pattern = Pattern.compile("^([a-zA-Z]+)\\((\\w+)\\)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expresion);
        if (matcher.find()) {
            String nombreFuncion = matcher.group(1);
            String argumento = matcher.group(2);
            // Almacena la función y su argumento en el mapa de funciones
            this.funciones.put(nombreFuncion, argumento);
        }
    }
    

    public static String ejecutarFuncion(String expresion, HashMap<String, String> funciones, int parametro) {
        // Extrae el nombre de la función y su argumento de la expresión
        Pattern pattern = Pattern.compile("^([a-zA-Z]+)\\((\\w+)\\)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(expresion);
        if (matcher.find()) {
            String nombreFuncion = matcher.group(1);
            String argumento = matcher.group(2);
            // Verifica si la función está definida en el mapa de funciones
            if (funciones.containsKey(nombreFuncion)) {
                // Obtiene el valor del argumento de la función
                int valorArgumento = Integer.parseInt(funciones.get(nombreFuncion));
                // Realiza una comparación con el parámetro dado y devuelve el resultado
                if (valorArgumento == parametro) {
                    return "True";
                } else {
                    return "NIL";
                }
            } else {
                return "La función no está definida";
            }
        } else {
            return "Expresión de función no válida";
        }
    }
}
