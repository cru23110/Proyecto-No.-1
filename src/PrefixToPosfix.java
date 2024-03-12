/**
* Universidad del Valle de Guatemala
* @author Isabella Obando - 23074
* @author Mia Fuentes - 23775
* @author Roberto Barreda -  23354
* @author Juan Cruz - 23110
* @author Linda Chen - 23173
* @author Nadissa Vela - 23764
* @description Clase PrefixToPosfix. Case encargada de traducir una expresion prefix a una posfix
* @date creación 23/01/2024 última modificación 06/02/2024
*/
package src;

import java.util.Stack;

public class PrefixToPosfix
{

    /** 
     * @param expresion
     * @return String
     */
    public static String invertir(String expresion){
        StringBuilder strn = new StringBuilder(expresion);
        expresion = strn.reverse().toString();
        String temp = "";
        for(int i = 0; i<expresion.length(); i++){
            if(expresion.charAt(i) != '(' && expresion.charAt(i) != ')'){
                temp = temp+expresion.charAt(i);
            } else if(expresion.charAt(i) == '('){
                temp = temp+")";
            } else if (expresion.charAt(i) == ')'){
                temp = temp+"(";
            }
        }
        expresion = temp;
        return expresion;
    }
}