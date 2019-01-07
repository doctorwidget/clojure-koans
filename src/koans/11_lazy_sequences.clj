(ns koans.11-lazy-sequences
  (:require [koan-engine.core :refer :all]))

(meditations
 "There are many ways to generate a sequence"
 (= '(1 2 3 4) (range 1 5)) ; arity-2: first-included and first-excluded indices
 
 "The range starts at the beginning by default"
 (= '(0 1 2 3 4) (range 5)) ; arity-1: first-included index is 0 by default; %1 is first-excluded
 
 "Only take what you need when the sequence is large"
 (= [0 1 2 3 4 5 6 7 8 9]
    (take 10 (range 100))) ; think of (range 100) as instantiating a __generator__ instance
 
 "Or limit results by dropping what you don't need"
 (= [95 96 97 98 99]
    (drop 95 (range 100))) ; all clojure lazy sequences are surely generators under the hood
 
 "Iteration provides an infinite lazy sequence"
 (= [1 2 4 8 16 32 64 128] (take 8 (iterate (fn [x] (* x 2)) 1))) 
 ; (iterate) is another way to instantiate a generator; this time with your own fn 
 
 "Repetition is key"
 (= [:a :a :a :a :a :a :a :a :a :a]
    (repeat 10 :a)) ; (repeat) is another way to instantiate a generator

 "Iteration can be used for repetition"
 (= (repeat 100 "hello")  ; a generator instance, i.e. a lazy sequence
    (take 100 (iterate (fn [x] x) "hello")))) 
; (iterate) creates a lazy sequence i.e. a generator
; but (take) __realizes__ it
; and then the first lazy sequence (based on (repeat)) is compared to the results of (take)

