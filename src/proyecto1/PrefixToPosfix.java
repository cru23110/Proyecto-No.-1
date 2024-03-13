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
package proyecto1;

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
    
    /** 
     * @param expresion
     * @return String
     */
    public static String convPrefixToPosfix(String expresion){
        expresion = invertir(expresion);
        String temp = "";
        StackArrayList<String> stack = new StackArrayList<>();
        for(int i = 0; i<expresion.length(); i++){
            if(expresion.charAt(i) >= 'a' && expresion.charAt(i) <= 'z'){
                stack.push(""+expresion.charAt(i));
            } else if(expresion.charAt(i) == ' '){
                stack.push(" ");
            }else if (expresion.charAt(i) == '*' || expresion.charAt(i) == '+' || expresion.charAt(i) == '-' || expresion.charAt(i) == '/'){
                for(int a = 0; a<= 1; a++){
                    if(stack.peek() == " "){
                        temp = temp+stack.pull();
                    }
                    if(!stack.isEmpty()){
                    temp = temp+stack.pull();}
                }
                temp = temp+" "+expresion.charAt(i);
                temp = temp.trim();
            } else if (expresion.charAt(i) >= '0' && expresion.charAt(i) <= '9'){
                String temp2 = "";
                for(int a = i; a<expresion.length(); a++){
                    if(expresion.charAt(a) >= '0' && expresion.charAt(a) <= '9'){
                        temp2 = temp2+expresion.charAt(a);
                        i = a;
                    } else{
                        stack.push(invertir(temp2));
                        break;   
                    }
                }
            } else if(expresion.charAt(i) == '(' || expresion.charAt(i) == ')'){
                temp = temp+""+expresion.charAt(i);
            }
        }
        temp = temp.replaceAll("  ", " ");
        return temp;
    }
}
