(ns clojure-prjs.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn expression []
  (/ (+ 5 4 (- 2 (- 3 (+ (/ 4 5) 6)))) (* 3 (- 6 2) (- 2 7))))

(defn sum-of-squares
  "sum of squares of the two arguments"
  [first, second]
  (+ (* first first) (* second second))
  )

(defn bigger-of-two [one, two]
  (if (>= one two) one two))

(defn smaller-of-two [one, two]
  (if (<= one two) one two))


(defn sum-of-square-of-two-large-nos [one, two, three]
  (let [
        firstNum (bigger-of-two one two)
        secondNum (bigger-of-two (smaller-of-two one two) three)]
    (sum-of-squares firstNum secondNum)))

(defn concise-sum-of-square-of-two-large-nos [one, two, three]
  (let [
        firstNum (max one two)
        secondNum (max (min one two) three)]
    (sum-of-squares firstNum secondNum)))

;(defn sum-of-square-of-two-large-nos-big [a, b, c]
;  (cond
;    (>= a b) (cond
;               (>= b c) (sum-of-squares a b)
;               :else (sum-of-squares a c))
;    :else (cond
;            (>= a c) (sum-of-squares b a)
;            :else (sum-of-squares b c))))

(defn a-plus-abs-b [a, b]
  ((if (> b 0) + -) a b))

; Ben Bitdiddle's way of identifying applicative vs normal order evaluation
(defn p []
  (p))

(defn test [x y]
  (if (= x 0)
    0
    y))

; Stack overflow error since this is an applicative evaluation
;(test 0 (p))

(defn average [first, second]
  (/ (+ first second) 2))

(defn square [x]
  (* x x))

(defn abs [x]
  (if (< x 0) (- x) x))

(defn good-enough? [guess x]
  (println "good-enough")
  (< (abs (- (square guess) x)) 0.001))

(defn improve [guess x]
  (println "improve")
  (average guess (/ x guess)))

(defn sqrt-iter [guess, x]
  (if (good-enough? guess x)
    guess
    (sqrt-iter (improve guess x) x)))

(defn sqrt [number]
  (sqrt-iter 1.0 number))

(defn new-if [predicate, then-clause, else-clause]
  (cond
    (predicate) then-clause
    :else else-clause))

(defn new-sqrt [guess x]
  (new-if (good-enough? guess x)
          guess
          (new-sqrt (improve guess x) x)))