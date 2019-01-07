(ns koans.12-sequence-comprehensions
  (:require [koan-engine.core :refer :all]))

(meditations
  "Sequence comprehensions can bind each element in turn to a symbol"
  (= '(0 1 2 3 4 5)   ; (for) comprehensions return sequences
     (for [x (range 6)] ; i.e. "for EACH x in the range of 0..6"
       x))

  "They can easily emulate mapping"
  (= '(0 1 4 9 16 25)
     (map (fn [x] (* x x))
          (range 6))
     (for [x (range 6)]
       (* x x))) ; emulate seems too weak of a term here... "duplicate" is more like it

  "And also filtering"
  (= '(1 3 5 7 9)
     (filter odd? (range 10))  ; technically, the (filter) is shorter here
     (for [x (range 10) :when (odd? x)] ;  but that :when clause is nice
       x))

  "Combinations of these transformations is trivial"
  (= '(1 9 25 49 81)
     (map (fn [x] (* x x))
          (filter odd? (range 10)))
     (for [x (range 10) :when (odd? x)] ; it's only when we get to the *combination* of filter and map
       (* x x)))  ; where I think a (for) comprehension starts to feel like the better choice

  "More complex transformations simply take multiple binding forms"
  (= [[:top :left] [:top :middle] [:top :right]
      [:middle :left] [:middle :middle] [:middle :right]
      [:bottom :left] [:bottom :middle] [:bottom :right]]
     (for [row [:top :middle :bottom]
           column [:left :middle :right]]
       [row column]))) 
; and once you start dealing with >1 collection, it's no contest: (for) wins every time
