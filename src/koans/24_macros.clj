(ns koans.24-macros
  (:require [koan-engine.core :refer :all]))

(defmacro hello [x]
  (str "Hello, " x))

(defmacro infix [form]
  (list (second form) (first form) (nth form 2)))

(defmacro infix-concise [form]
  `(~(second form) ; Note the syntax-quote (`) and unquote (~) characters!
    ~(first form)
    ~(nth form 2)))

(defmacro recursive-infix [form] ; cond expects pairs, which makes this fn is hard to read
  (cond (not (seq? form)) ; test A - test for the base case... is this a non-empty sequence?
        form              ; result A - non-sequence or empty sequence, so return it as is
        (= 1 (count form)) ; test B - test for seq of length 1: there is only one element!
        `(recursive-infix ~(first form)) ; result B - recurse into that seq element
        :else              ; all other cases MUST BE sequences of length 2 or more
        (let [operator (second form)  ; this is the fn - it will go into lisp function position
              first-arg (first form)  ; this is arg#1 - will go into the second spot
              others (drop 2 form)]     ; all others need to be unraveled as args 2..N
          `(~operator
            (recursive-infix ~first-arg)
            (recursive-infix ~others)))))

(meditations
  "Macros are like functions created at compile time"
  (= "Hello, Macros!" (hello "Macros!"))

  "I can haz infix?"
  (= 10 (infix (9 + 1)))

  "Remember, these are nothing but code transformations"
  (= '(+ 9 1) (macroexpand '(infix (9 + 1))))

  "You can do better than that - hand crafting FTW!"
  (= '(* 10 2) (macroexpand '(infix-concise (10 * 2))))

  "Things don't always work as you would like them to... "
  (= '(+ 10 (2 * 3)) (macroexpand '(infix-concise (10 + (2 * 3)))))

  "Really, you don't understand recursion until you understand recursion"
  (= 36 (recursive-infix (10 + (2 * 3) + (4 * 5)))))
