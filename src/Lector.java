/**
* Universidad del Valle de Guatemala
* @author Isabella Obando - 23074
* @author Mia Fuentes - 23775
* @author Roberto Barreda -  23354
* @author Juan Cruz - 23110
* @author Linda Chen - 23173
* @author Nadissa Vela - 23764
* @description Clase Lector. Clase encargada de la lectura de los distintos archivos de texto
* @date creación 23/01/2024 última modificación 06/02/2024
*/
package src;

import java.io.File;
import java.util.*;

public class Lector
{
    /** 
     * @param path
     * @return ArrayList<String>
     */
    public static ArrayList<String> leerArchivo(String path) {
        ArrayList<String> lista = new ArrayList<>();
        try {
            File doc = new File(path);
            Scanner obj = new Scanner(doc);
            while (obj.hasNextLine()) {
                lista.add(obj.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    /** 
     * @param instrucciones
     */
    public static void ejecutarInstruccion(String instrucciones){
        
    }
}
