/**
* Universidad del Valle de Guatemala
* @author Isabella Obando - 23074
* @author Mia Fuentes - 23775
* @author Roberto Barreda -  23354
* @author Juan Cruz - 23110
* @author Linda Chen - 23173
* @author Nadissa Vela - 23764
* @description Clase StackArrayList. Sera la encargada de poder implementar un Stack generico durante el programa. Se utiliza en la calculadora
* @date creación 26/01/2024 última modificación 05/02/2024
*/

package proyecto1;
import java.util.ArrayList;
public class StackArrayList<T> implements ICustomStack<T>
{

    private ArrayList<T> data; //ArrayList de valores
    
    //Constructor
    public StackArrayList() {
        data = new ArrayList<>();
    }
    
    /** 
    * Introduce un valor al incio de la pila 
    * @param value
     */
    @Override
    public void push(T value) {
        data.add(0, value);
    }
    
    /** 
     * Obtiene el valor del inicio de la pila y lo elimina
     * @return T
     */
    @Override
    public T pull() {
        if (this.isEmpty()) {
            return null;
        } else {
            return data.remove(0);
        }
    }
    
    /** 
     * Obtiene el valor del inicio de la pila y no lo elimina
     * @return T
     */
    @Override
    public T peek() {
        if (this.isEmpty()) {
            return null;
        } else {
            return data.get(0);
        }
    }
    
    /**
     * Obtiene la cantidad de elementos dentro de la pila 
     * @return int
     */
    @Override
    public int count() {
        return data.size();
    }
    
    /**
     * Verifica si la pila esta vacia 
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}
