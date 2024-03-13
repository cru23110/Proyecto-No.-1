import java.util.Scanner;
import java.util.Vector;

public class Principal
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Controlador controlador = new Controlador();
        String instruccion = "";

        System.out.println("\nIngrese la linea: ");
        instruccion = scanner.nextLine();
        controlador.setq(instruccion);
        System.out.println(controlador.imprimir());

        System.out.println("\nIngrese la linea: ");
        instruccion = scanner.nextLine();
        System.out.println(controlador.atom(instruccion));

    }
}