import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controlador {
   private HashMap<String, Integer> variables = new HashMap();
   private HashMap<String, String> listas = new HashMap();

   public Controlador() {
   }

   public void setq(String var1) {
      var1 = this.limpiar(var1, "s", "q", 3);
      String var2 = "";
      int var3 = 0;
      int var4 = 0;

      for(int var5 = 0; var5 < var1.length(); ++var5) {
         String var6 = String.valueOf(var1.charAt(var5));
         if (var6.equals("(")) {
            var3 = var5;
         }

         if (var6.equals(")")) {
            var4 = var5;
         }
      }

      boolean var9 = false;
      if (var3 != 0 && var4 != 0) {
         var9 = true;

         for(int var10 = var3; var10 <= var4; ++var10) {
            var2 = var2 + String.valueOf(var1.charAt(var10));
         }

         StringBuilder var11 = new StringBuilder(var2);

         for(int var7 = 0; var7 < var11.length(); ++var7) {
            String var8 = String.valueOf(var11.charAt(var7));
            if (var8.equals("(") || var8.equals(")")) {
               var11.deleteCharAt(var7);
            }
         }

         var2 = (new String(var11)).trim().replaceAll("\\s+", " ");
         var2 = "(" + var2 + ")";
      }

      var1 = var1.replaceAll("\\s+", " ");
      String[] var12 = var1.split(" ");
      if (var9) {
         this.listas.put(var12[0], var2);
      } else {
         this.variables.put(var12[0], Integer.parseInt(var12[1]));
      }

   }

   public String limpiar(String var1, String var2, String var3, int var4) {
      var1 = var1.trim().replaceAll("\\s+", " ");
      StringBuilder var5 = new StringBuilder(var1);
      var5.deleteCharAt(0);
      var5.deleteCharAt(var5.length() - 1);
      int var6 = var5.indexOf(var2);
      int var7 = var5.indexOf(var3);
      if (var7 - var6 == var4) {
         var5.delete(var6, var7 + 1);
      }

      var1 = (new String(var5)).trim();
      var1 = var1.replaceAll("\\s+", " ");
      return var1;
   }

   public String imprimir() {
      String var10000 = String.valueOf(this.variables);
      return "\n" + var10000 + "\n" + String.valueOf(this.listas);
   }

   public String operaciones() {
      String var1 = "";
      var1 = var1 + "\nPrimera operacion: " + ((Integer)this.variables.get("x") + (Integer)this.variables.get("y") + (Integer)this.variables.get("z"));
      var1 = var1 + "\nSegunda operacion: " + ((Integer)this.variables.get("z") + (Integer)this.variables.get("y") - (Integer)this.variables.get("x"));
      var1 = var1 + "\nTercera operacion: " + ((Integer)this.variables.get("x") / (Integer)this.variables.get("y") + (Integer)this.variables.get("z"));
      return var1;
   }

   public boolean atom(String var1) {
      var1 = this.limpiar(var1, "a", "m", 3);
      Pattern var2 = Pattern.compile("^[0-9]+$", 2);
      Matcher var3 = var2.matcher(var1);
      boolean var4 = var3.find();
      if (var4) {
         return var4;
      } else {
         Pattern var5 = Pattern.compile("[a-zA-Z]+", 2);
         Matcher var6 = var5.matcher(var1);
         if (var6.find()) {
            try {
               int var7 = (Integer)this.variables.get(var1);
               return true;
            } catch (Exception var8) {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public boolean equal(String var1) {
      var1 = this.limpiar(var1, "e", "l", 4);
      Pattern var2 = Pattern.compile("^[0-9]+[ ]+[0-9]+$", 2);
      Matcher var3 = var2.matcher(var1);
      boolean var4 = var3.find();
      String[] var5 = var1.split(" ");
      if (var4) {
         return Integer.valueOf(var5[0]) == Integer.valueOf(var5[1]);
      } else {
         Pattern var6 = Pattern.compile("^[a-zA-Z]+[ ]+[a-zA-Z]+$", 2);
         Matcher var7 = var6.matcher(var1);
         boolean var8 = var7.find();
         if (var8) {
            try {
               int var16 = (Integer)this.variables.get(var5[0]);
               int var17 = (Integer)this.variables.get(var5[1]);
               return var16 == var17;
            } catch (Exception var14) {
               System.out.println("\n" + String.valueOf(var14));
               return false;
            }
         } else {
            try {
               Pattern var9 = Pattern.compile("^[a-zA-Z]+$", 2);
               Matcher var10 = var9.matcher(var5[0]);
               boolean var11 = var10.find();
               int var12;
               int var13;
               if (var11) {
                  var12 = (Integer)this.variables.get(var5[0]);
                  var13 = Integer.valueOf(var5[1]);
                  return var12 == var13;
               } else {
                  var12 = Integer.valueOf(var5[0]);
                  var13 = (Integer)this.variables.get(var5[1]);
                  return var12 == var13;
               }
            } catch (Exception var15) {
               System.out.println("\n" + String.valueOf(var15));
               return false;
            }
         }
      }
   }

   public boolean ascendente(String var1) {
      var1 = this.limpiarSimbolo(var1);
      Pattern var2 = Pattern.compile("^[0-9 ]+$", 2);
      Matcher var3 = var2.matcher(var1);
      boolean var4 = var3.find();
      String[] var5 = var1.split(" ");
      Vector var6 = new Vector();
      int var7;
      if (var4) {
         for(var7 = 0; var7 < var5.length; ++var7) {
            var6.add(Integer.valueOf(var5[var7]));
         }
      } else {
         Pattern var17 = Pattern.compile("^[a-zA-Z ]+$", 2);
         Matcher var8 = var17.matcher(var1);
         boolean var9 = var8.find();
         int var10;
         if (var9) {
            try {
               for(var10 = 0; var10 < var5.length; ++var10) {
                  var6.add((Integer)this.variables.get(var5[var10]));
               }
            } catch (Exception var16) {
               System.out.println("\n" + String.valueOf(var16));
            }
         } else {
            for(var10 = 0; var10 < var5.length; ++var10) {
               Pattern var11 = Pattern.compile("^[a-zA-Z]+$", 2);
               Matcher var12 = var11.matcher(var5[var10]);
               boolean var13 = var12.find();
               if (var13) {
                  try {
                     var6.add((Integer)this.variables.get(var5[var10]));
                  } catch (Exception var15) {
                     System.out.println("\n" + String.valueOf(var15));
                  }
               } else {
                  var6.add(Integer.valueOf(var5[var10]));
               }
            }
         }
      }

      for(var7 = 0; var7 < var6.size() - 1; ++var7) {
         if ((Integer)var6.get(var7) > (Integer)var6.get(var7 + 1)) {
            return false;
         }
      }

      return true;
   }

   public boolean descendente(String var1) {
      var1 = this.limpiarSimbolo(var1);
      Pattern var2 = Pattern.compile("^[0-9 ]+$", 2);
      Matcher var3 = var2.matcher(var1);
      boolean var4 = var3.find();
      String[] var5 = var1.split(" ");
      Vector var6 = new Vector();
      int var7;
      if (var4) {
         for(var7 = 0; var7 < var5.length; ++var7) {
            var6.add(Integer.valueOf(var5[var7]));
         }
      } else {
         Pattern var17 = Pattern.compile("^[a-zA-Z ]+$", 2);
         Matcher var8 = var17.matcher(var1);
         boolean var9 = var8.find();
         int var10;
         if (var9) {
            try {
               for(var10 = 0; var10 < var5.length; ++var10) {
                  var6.add((Integer)this.variables.get(var5[var10]));
               }
            } catch (Exception var16) {
               System.out.println("\n" + String.valueOf(var16));
            }
         } else {
            for(var10 = 0; var10 < var5.length; ++var10) {
               Pattern var11 = Pattern.compile("^[a-zA-Z]+$", 2);
               Matcher var12 = var11.matcher(var5[var10]);
               boolean var13 = var12.find();
               if (var13) {
                  try {
                     var6.add((Integer)this.variables.get(var5[var10]));
                  } catch (Exception var15) {
                     System.out.println("\n" + String.valueOf(var15));
                  }
               } else {
                  var6.add(Integer.valueOf(var5[var10]));
               }
            }
         }
      }

      for(var7 = 0; var7 < var6.size() - 1; ++var7) {
         if ((Integer)var6.get(var7) < (Integer)var6.get(var7 + 1)) {
            return false;
         }
      }

      return true;
   }

   public String limpiarSimbolo(String var1) {
      var1 = var1.trim().replaceAll("\\s+", " ");
      StringBuilder var2 = new StringBuilder(var1);
      var2.deleteCharAt(0);
      var2.deleteCharAt(var2.length() - 1);

      for(int var3 = 0; var3 < var2.length(); ++var3) {
         String var4 = String.valueOf(var2.charAt(var3));
         if (var4.equals("<") || var4.equals(">")) {
            var2.deleteCharAt(var3);
         }
      }

      var1 = (new String(var2)).trim().replaceAll("\\s+", " ");
      return var1;
   }

   public String list(String var1) {
      var1 = this.limpiar(var1, "l", "t", 3);
      var1 = "(" + var1 + ")";
      return var1;
   }

   public boolean listp(String var1) {
      var1 = this.limpiar(var1, "l", "p", 4);
      Pattern var2 = Pattern.compile("^[0-9]+$", 2);
      Matcher var3 = var2.matcher(var1);
      boolean var4 = var3.find();
      if (var4) {
         return false;
      } else {
         Pattern var5 = Pattern.compile("[a-zA-Z]+", 2);
         Matcher var6 = var5.matcher(var1);
         if (var6.find()) {
            try {
               int var7 = (Integer)this.variables.get(var1);
               return false;
            } catch (Exception var8) {
               return true;
            }
         } else {
            return true;
         }
      }
   }
}
