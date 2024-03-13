/**
* Universidad del Valle de Guatemala
* @author Isabella Obando - 23074
* @author Mia Fuentes - 23775
* @author Roberto Barreda -  23354
* @author Juan Cruz - 23110
* @author Linda Chen - 23173
* @author Nadissa Vela - 23764
* @description Clase Controlador. Sera la encargada de interactuar con el usuario
* @date creación 23/01/2024 última modificación 06/02/2024
*/
package proyecto1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ejecutarProgramaPreestablecido();
            interactuarConUsuario(scanner);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static void ejecutarProgramaPreestablecido() throws IOException {
        ArrayList<String> instrucciones = Lector.leerArchivo("src/archivos/linea.txt");
        System.out.println("---------- EJECUCION DE PROGRAMA PREESTABLECIDO ----------");
        Interprete inter = new Interprete();
        for (String instruccion : instrucciones) {
            System.out.print("Evaluando: " + instruccion + " ... ");
            inter.Evaluar(instruccion, SintaxScanner.getState(instruccion));
            System.out.println("Listo.");
        }
        System.out.println("----------------------------------------------------------");
    }

    private static void interactuarConUsuario(Scanner scanner) {
        System.out.println("Bienvenido al intérprete Lisp.");
        System.out.println("Ingrese una expresión o escriba 'exit' para salir.");

        Interprete inter = new Interprete();
        String entradaUsuario = "";

        do {
            System.out.print("↓ ");
            entradaUsuario = scanner.nextLine();

            if (!entradaUsuario.equals("exit")) {
                System.out.print("Evaluando expresión ... ");
                inter.Evaluar(entradaUsuario, SintaxScanner.getState(entradaUsuario));
                System.out.println("Listo.");
            }

        } while (!entradaUsuario.equals("exit"));

        System.out.println("¡Hasta luego!");
    }
}
