(ns koans.01-equalities
  (:require [koan-engine.core :refer :all]))

(meditations
 "We shall contemplate truth by testing reality, via equality"
 (= true true) ; sanity test for reality

 "To understand reality, we must compare our expectations against reality"
 (= 2 (+ 1 1)) ; expressions are evalated in inside-out order

 "You can test equality of many things"
 (= (+ 3 4) 7 (+ 2 5)) ; '=' can take any number of arguments. (NB: many koans rely on this)

 "Some things may appear different, but be the same"
 (= true (= 2 2/1)) ; integers and ratios can be compared with =
 
 "You cannot generally float to heavens of integers"
 (= false (= 2 2.0)) ; integers and floats CANNOT be compared with =
 
 "But a looser equality is also possible"
 (= true (== 2.0 2)) ; use '==' to get loose numeric equality check
 
 "Something is not equal to nothing"
 (= true (not (= 1 nil))) ; 1 is not nothing
 
 "Zero is something"
 (= true (not (= 0 nil))) ; more interestingly, zero is not nothing either
 
 "Strings, and keywords, and symbols: oh my!"
 (= false (= "hello" :hello 'hello)) ; Clojure almost never autocasts 

 "Make a keyword with your keyboard"
 (= :hello (keyword "hello")) ; but you can easily do your own casting

 "Symbolism is all around us"
 (= 'hello (symbol "hello")) ;; make a symbol from a string

 "What could be equivalent to nothing?"
 (= nil nil) ; nil is only equal to itself

 "When things cannot be equal, they must be different"
 (not= :fill-in-the-blank 1)) ; almost anything works here
