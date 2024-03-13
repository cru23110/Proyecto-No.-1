; Función para calcular el factorial de un número
(defun factorial (n)
  (if (= n 0)
      1
      (* n (factorial (- n 1)))))

; Ejemplos de uso

(print (factorial 5))
