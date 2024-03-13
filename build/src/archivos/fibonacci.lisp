; Función para calcular el término n de la serie de Fibonacci
(defun fibonacci (n)
  (if (<= n 1)
      n
      (+ (fibonacci (- n 1)) (fibonacci (- n 2)))))
(print (fibonacci 5))
