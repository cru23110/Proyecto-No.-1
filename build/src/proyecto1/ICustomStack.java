/**
* Universidad del Valle de Guatemala
* @author Isabella Obando - 23074
* @author Mia Fuentes - 23775
* @author Roberto Barreda -  23354
* @author Juan Cruz - 23110
* @author Linda Chen - 23173
* @author Nadissa Vela - 23764
* @description Interfaz IStack. Se implementara en la clase StackArrayList
* @date creación 23/01/2024 última modificación 06/02/2024
*/
package proyecto1;

public interface ICustomStack<T> {

    void push(T value);

    T pull();

    T peek();

    int count();

    boolean isEmpty();
}
