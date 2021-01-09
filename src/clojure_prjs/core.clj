(ns clojure-prjs.core
  (:import (java.net URL)))

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

;(defn sqrt-iter [guess, x]
;  (if (good-enough? guess x)
;    guess
;    (sqrt-iter (improve guess x) x)))
;
;(defn sqrt [number]
;  (sqrt-iter 1.0 number))

(defn new-if [predicate, then-clause, else-clause]
  (cond
    (predicate) then-clause
    :else else-clause))

(defn new-sqrt [guess x]
  (new-if (good-enough? guess x)
          guess
          (new-sqrt (improve guess x) x)))

(defn new-good-enough? [guess x]
  (println "New thing")
  (< (abs (- (improve guess x) guess)) 0.000001))

(defn new-sqrt-iter [guess x]
  (if (new-good-enough? guess x)
    guess
    (new-sqrt-iter (improve guess x) x)))

(defn lexical-sqrt [x]
  (let [
        good-enough? (fn [guess, x] (< (abs (- (square guess) x)) 0.001))
        improve (fn [guess, x] (average guess (/ x guess)))
        sqrt-iter (fn [guess, x]
                    (println "Here again")
                    (if (good-enough? guess x)
                      guess
                      (recur (improve guess x) x)))
        ]
    (sqrt-iter 1.0 x))
  )

(def greet (fn [] (println "Hello world!")))
(def macro-greet #(println "Another world!" %))
(defn greeting
  ([] (greeting "World"))
  ([x] (greeting "Hello" x))
  ([x, y] (str x ", " y "!"))
  )

(defn do-nothing [x] x)
(defn always-thing
  "takes any number of arguments, ignores all of them, and returns the number 100"
  [& x] 100)

(defn make-thingy
  "takes a single argument x. It should return another function, which takes any number of arguments and always returns x"
  [x] (fn [& any] x))

(defn triplicate
  "takes another function and calls it three times, without any arguments"
  [f]
  (f)
  (f)
  (f))

(defn opposite
  "Get single argument f and return another function which takes any no of args,
  applies f on them, and then calls not on the result. The not function in Clojure does logical negation."
  [f]
  (fn ([& x] (not (apply f x)))))

(defn triplicate2 [f & args]
  (triplicate (fn [] (apply f args))))

(assert (= -1.0 (Math/cos (Math/PI))))
(assert (= 1.0 (+ (Math/pow (Math/cos 0.2) 2) (Math/pow (Math/sin 0.2) 2))))

(defn http-get [url]
  "Fetches that URL from the web, and returns the content as a string"
  (slurp (.openStream (URL. url))))

(defn simple-http-get [url]
  (slurp url))

(defn one-less-arg [f, x]
  (fn [& args] (apply f x args)))

(defn two-fns [f, g]
  (fn [x] (f (g x))))
