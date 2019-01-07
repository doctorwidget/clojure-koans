(ns koans.07-functions
  (:require [koan-engine.core :refer :all]))

(defn multiply-by-ten [n]
  (* 10 n))

(defn square [n] (* n n))

(meditations
  "Calling a function is like giving it a hug with parentheses"
  (= 81 (square 9)) ; lisp order is best order

  "Functions are usually defined before they are used"
  (= 20 (multiply-by-ten 2)) ; dash-case is idiomatic clojure

  "But they can also be defined inline"
  (= 10 ((fn [n] (* 5 n)) 2)) ; fn is fundamental. One of the original special forms! 

  "Or using an even shorter syntax"
  (= 60 (#(* 15 %) 4)) ; whereas this #-based reader macro is a late-comer

  "Even anonymous functions may take multiple arguments"
  (= 15 (#(+ %1 %2 %3) 4 5 6)) ; NB: 1-based counting, not 0-based!

  "Arguments can also be skipped"
  (= "AACC" (#(str "AA" %2) "bb" "CC")) ; 'skipping' here means *ignoring*; it is an error not to *provide* all arguments

  "One function can beget another"
  (= 9 (((fn [] +)) 4 5)) ; you don't need to quote the '+' here... it's perfectly safe in the non-function position

  "Functions can also take other functions as input"
  (= 20 ((fn [f] (f 4 5))
           *)) ; same here - providing the '*' function as a first-class function is perfectly kosher

  "Higher-order functions take function arguments"
  (= 25 ((fn [f] (f 5))  ; so this fn expects another fn... it's functions all the way down!
          (fn [n] (* n n))))

  "But they are often better written using the names of functions"
  (= 25 ((fn [f] (f 5)) square))) ; it seems to me that too many anonymous functions is an antipattern
