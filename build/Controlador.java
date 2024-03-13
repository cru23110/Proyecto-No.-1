
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controlador
{
    private HashMap<String, Integer> variables = new HashMap<>();
    private HashMap<String, String> listas = new HashMap<>();

    public void setq(String instruccion)
    {
        instruccion = this.limpiar(instruccion, "s", "q", 3);

        //Se mira si la cadena tiene mas parentesis dentro de ella
        String lista = "";
        int inicio = 0;
        int fin = 0;
        for(int i = 0; i < instruccion.length(); i++)
        {
            String caracter = String.valueOf(instruccion.charAt(i));
            if(caracter.equals("(")) inicio = i;
            if(caracter.equals(")")) fin = i;
        }

        boolean esLista = false;

        if(inicio != 0 && fin != 0) //Si son distinto a 0 ambos, significa que el for anterior identifico otros parentesis dentro de la cadena
        {
            esLista = true;
            for(int x = inicio; x <= fin; x++)
            {
                lista += String.valueOf(instruccion.charAt(x)); //Se le concatena a "lista" todos los caracteres que estan dentro de los parentesis de "editado", incluyendo los mismos parentesis
            }

            StringBuilder listaE = new StringBuilder(lista);
            for(int i = 0; i < listaE.length(); i++)
            {
                String caracter = String.valueOf(listaE.charAt(i));
                if(caracter.equals("(") || caracter.equals(")")) listaE.deleteCharAt(i); //Se eliminan los parentesis para poder hacer la limpieza de espacios en blanco innecesarios
            }
            lista = new String(listaE).trim().replaceAll("\\s+", " ");
            lista = "(" + lista + ")";  //se regresan los parentesis a la lista para poder manejarla luego como tal

        }

        instruccion = instruccion.replaceAll("\\s+", " ");

        String[] split = instruccion.split(" ");

        if(esLista)
        {
            this.listas.put(split[0], lista);
        }
        else
        {
            this.variables.put(split[0], Integer.parseInt(split[1]));
        }
    }

    public String limpiar(String instruccion, String letraInicio, String letraFinal, int largo)
    {
        instruccion = instruccion.trim().replaceAll("\\s+", " ");
        StringBuilder editado = new StringBuilder(instruccion);
        editado.deleteCharAt(0);
        editado.deleteCharAt(editado.length() - 1);
        int indexInicio = editado.indexOf(letraInicio);
        int indexFinal = editado.indexOf(letraFinal);
        if((indexFinal - indexInicio) == largo) editado.delete(indexInicio, indexFinal + 1);
        instruccion = new String(editado).trim();
        instruccion = instruccion.replaceAll("\\s+", " ");
        return instruccion;
    }

    public String imprimir()
    {
        return "\n" + String.valueOf(this.variables) + "\n" + String.valueOf(this.listas);
    }

    public String operaciones()
    {
        String info = "";
        info += "\nPrimera operacion: " + (this.variables.get("x") + this.variables.get("y") + this.variables.get("z"));
        info += "\nSegunda operacion: " + (this.variables.get("z") + this.variables.get("y") - this.variables.get("x"));
        info += "\nTercera operacion: " + (this.variables.get("x") / this.variables.get("y") + this.variables.get("z"));

        return info;
    }

    public boolean atom(String instruccion) //CAMBIAR A QUE DEVUELVA "T" SI ES TRUE O "NIL" SI ES FALSO
    {  
        instruccion = this.limpiar(instruccion, "a", "m", 3);
        //aqui debe ir un codigo para convertir la "instruccion" si es que esta viene con una funcion definida por el usuario
        Pattern pattern = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(instruccion);
        boolean esAtom = matcher.find();  //El matcher.find() cambia cada vez que se utiliza, por lo que se guarda el match en la varibale "esAtom"
        if(esAtom)
        {
            return esAtom;
        }
        else
        {
            Pattern variable = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = variable.matcher(instruccion);
            if(matcher2.find())
            {
                try {
                    int valor = this.variables.get(instruccion);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
            return false;
        }
        
        
        /*
        boolean esAtom = false;
        System.out.println("\nEntrada ATOM: '" + instruccion + "'");
        instruccion = limpiar(instruccion, "a", "m", 3);
        System.out.println("\nLimpiado ATOM: '" + instruccion + "'");
        StringBuilder editado = new StringBuilder(instruccion);
        System.out.println("\nEditado ATOM: '" + editado + "'");

        String lista = "";
        int inicio = 0;
        int fin = 0;
        for(int i = 0; i < editado.length(); i++)
        {
            String caracter = String.valueOf(editado.charAt(i));
            if(caracter.equals("(")) inicio = i;
            if(caracter.equals(")")) fin = i;
        }

        boolean parentesis = false;

        if(inicio != 0 || fin != 0) //Si son distinto a 0 uno de los dos, significa que el for anterior identifico otros parentesis dentro de la cadena
        {
            parentesis = true;
            for(int x = inicio; x <= fin; x++)
            {
                lista += String.valueOf(editado.charAt(x)); //Se le concatena a "lista" todos los caracteres que estan dentro de los parentesis de "editado", incluyendo los mismos parentesis
            }

            StringBuilder listaE = new StringBuilder(lista);
            for(int i = 0; i < listaE.length(); i++)
            {
                String caracter = String.valueOf(listaE.charAt(i));
                if(caracter.equals("(") || caracter.equals(")")) listaE.deleteCharAt(i); //Se eliminan los parentesis para poder hacer la limpieza de espacios en blanco innecesarios
            }
            lista = new String(listaE).trim().replaceAll("\\s+", " ");
            lista = "(" + lista + ")";

        }

        if(parentesis)
        {
            //codigo para ver si lo que esta dentro de los parentesis es una funcion o algo que podria ser un ATOM
            return false;
        }
        else
        {
            if(instruccion.length() > 0 ) return true;
        }

        System.out.println("\nLimpiado ATOM: '" + instruccion + "'");
        
        return esAtom;
        */
    }

    public boolean equal(String instruccion)
    {
        instruccion = this.limpiar(instruccion, "e", "l", 4);

        Pattern pattern = Pattern.compile("^[0-9]+[ ]+[0-9]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(instruccion);
        boolean sonNumeros = matcher.find();
        String[] split = instruccion.split(" ");

        if(sonNumeros)
        {
            return (Integer.valueOf(split[0])  == Integer.valueOf(split[1]));
        }
        else //Pueden ser numeros y letras (variables) juntos o solo variables
        {
            Pattern letras = Pattern.compile("^[a-zA-Z]+[ ]+[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = letras.matcher(instruccion);
            boolean sonLetras = matcher2.find();
            if(sonLetras) //Son solo letras, por lo tanto, son variables definidas por el usuario
            {
                try {
                    int primero = this.variables.get(split[0]);
                    int segundo = this.variables.get(split[1]);
                    return (primero == segundo);
                } catch (Exception e) {
                    //TODO: handle exception
                    System.out.println("\n" + e);
                    return false;
                }
            }
            else //Son letras (variables definidas por el usaurio) y numeros
            {   
                try {
                    Pattern letra = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher3 = letra.matcher(split[0]);
                    boolean esLetra = matcher3.find();

                    if(esLetra)
                    {
                        int primero = this.variables.get(split[0]);
                        int segundo = Integer.valueOf(split[1]);
                        return (primero == segundo);
                    }
                    else
                    {
                        int primero = Integer.valueOf(split[0]);
                        int segundo = this.variables.get(split[1]);
                        return (primero == segundo);
                    }
                } catch (Exception e) {
                    //TODO: handle exception
                    System.out.println("\n" + e);
                    return false;
                }
            }
        }
    }

    public boolean ascendente(String instruccion)
    {
        instruccion = this.limpiarSimbolo(instruccion);

        Pattern pattern = Pattern.compile("^[0-9 ]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(instruccion);
        boolean sonNumeros = matcher.find();
        String[] split = instruccion.split(" ");
        Vector<Integer> numeros = new Vector<>();

        if(sonNumeros)
        {   
            for(int i = 0; i < split.length; i++)
            {
                numeros.add(Integer.valueOf(split[i]));
            }
            
        }
        else //Pueden ser numeros y letras (variables) juntos o solo variables
        {
            Pattern letras = Pattern.compile("^[a-zA-Z ]+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = letras.matcher(instruccion);
            boolean sonLetras = matcher2.find();
            if(sonLetras) //Son solo letras, por lo tanto, son variables definidas por el usuario
            {
                try {
                    for(int i = 0; i < split.length; i++)
                    {
                        numeros.add(this.variables.get(split[i]));
                    }
                } catch (Exception e) {
                    //TODO: handle exception
                    System.out.println("\n" + e);
                }
            }
            else //Son letras (variables definidas por el usaurio) y numeros
            {
                for(int i = 0; i < split.length; i++)
                {
                    Pattern letra = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher3 = letra.matcher(split[i]);
                    boolean esLetra = matcher3.find();
                    if(esLetra)
                    {
                        try {
                            numeros.add(this.variables.get(split[i]));
                        } catch (Exception e) {
                            //TODO: handle exception
                            System.out.println("\n" + e);
                        }
                        
                    }
                    else numeros.add(Integer.valueOf(split[i]));
                }
            }
        }
        for (int i = 0; i < numeros.size() - 1; i++)
        {
            if((numeros.get(i)) > numeros.get(i + 1)) return false;
        }
        return true;
    }

    public boolean descendente(String instruccion)
    {
        instruccion = this.limpiarSimbolo(instruccion);

        Pattern pattern = Pattern.compile("^[0-9 ]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(instruccion);
        boolean sonNumeros = matcher.find();

        String[] split = instruccion.split(" ");
        Vector<Integer> numeros = new Vector<>();

        if(sonNumeros)
        {   
            for(int i = 0; i < split.length; i++)
            {
                numeros.add(Integer.valueOf(split[i]));
            }
            
        }
        else
        {
            Pattern letras = Pattern.compile("^[a-zA-Z ]+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = letras.matcher(instruccion);
            boolean sonLetras = matcher2.find();
            if(sonLetras) //Son solo letras, por lo tanto, son variables definidas por el usuario
            {
                try {
                    for(int i = 0; i < split.length; i++)
                    {
                        numeros.add(this.variables.get(split[i]));
                    }
                } catch (Exception e) {
                    //TODO: handle exception
                    System.out.println("\n" + e);
                }
            }
            else //Son letras (variables definidas por el usaurio) y numeros
            {
                for(int i = 0; i < split.length; i++)
                {
                    Pattern letra = Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher3 = letra.matcher(split[i]);
                    boolean esLetra = matcher3.find();
                    if(esLetra)
                    {
                        try {
                            numeros.add(this.variables.get(split[i]));
                        } catch (Exception e) {
                            //TODO: handle exception
                            System.out.println("\n" + e);
                        }
                        
                    }
                    else numeros.add(Integer.valueOf(split[i]));
                }
            }

        }

        for (int i = 0; i < numeros.size() - 1; i++)
        {
            if((numeros.get(i)) < numeros.get(i + 1)) return false;
        }
        return true;
    }

    public String limpiarSimbolo(String instruccion)
    {
        instruccion = instruccion.trim().replaceAll("\\s+", " ");
        StringBuilder editado = new StringBuilder(instruccion);
        editado.deleteCharAt(0);
        editado.deleteCharAt(editado.length() - 1);
        for(int i = 0; i < editado.length(); i++)
        {
            String caracter = String.valueOf(editado.charAt(i));
            if(caracter.equals("<") || caracter.equals(">")) editado.deleteCharAt(i);
        }
        instruccion = new String(editado).trim().replaceAll("\\s+", " ");
        return instruccion;
    }

    public String list(String instruccion)
    {
        instruccion = this.limpiar(instruccion, "l", "t", 3);
        instruccion = "(" + instruccion + ")";
        return instruccion;
    }

    public boolean listp(String instruccion)
    {
        instruccion = this.limpiar(instruccion, "l", "p", 4);
        //aqui debe ir un codigo para convertir la "instruccion" si es que esta viene con una funcion definida por el usuario
        Pattern pattern = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(instruccion);
        boolean esListp = matcher.find();  //El matcher.find() cambia cada vez que se utiliza, por lo que se guarda el match en la varibale "esAtom"
        if(esListp)
        {
            return false;
        }
        else
        {
            Pattern variable = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = variable.matcher(instruccion);
            if(matcher2.find())
            {
                try {
                    int valor = this.variables.get(instruccion);
                    return false;
                } catch (Exception e) {
                    return true;
                }
            }
            return true;
        }
    }
}
