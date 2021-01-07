(ns clojure-prjs.ex_one)

(defn expression []
  (/ (+ 5 4 (- 2 (- 3 (+ (/ 4 5) 6)))) (* 3 (- 6 2) (- 2 7))))
