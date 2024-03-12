import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LeerArchivo leer = new LeerArchivo();
        PalindromeSi si = new PalindromeSi("reemplaza_con_palabra_real");
        ProcesadorCSV procesar = new ProcesadorCSV(leer, si);
        WritetoFile writeToFile = new WritetoFile(new File("output.txt"));

        while (true) {
            System.out.println("Menú Principal");
            System.out.println("1. Leer y procesar archivo CSV");
            System.out.println("2. Mostrar palíndromos");
            System.out.println("3. Escribir palíndromos en archivo de salida");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    List<String> palindromeList = procesar.procesar();
                    System.out.println("Archivo CSV leído y procesado.");
                    break;
                case 2:
                    System.out.println("Palíndromos:");
                    for (String palabra : palindromeList) {
                        System.out.println(palabra);
                    }
                    break;
                case 3:
                    writeToFile.writeToLogFile(palindromeList);
                    System.out.println("Palíndromos escritos en el archivo de salida.");
                    break;
                case 4:
                    System.out.println("Saliendo del programa.");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }
    
    // Resto de las clases y lógica aquí...
}
