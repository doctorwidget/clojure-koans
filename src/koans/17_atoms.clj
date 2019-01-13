(ns koans.17-atoms
  (:require [koan-engine.core :refer :all]))

(def atomic-clock (atom 0))

(meditations
  "Atoms are like refs"
  (= 0 @atomic-clock)

  "You can change at the swap meet"
  (= 1 (do
         (swap! atomic-clock inc)
         ; I would have called this opertion alter!, becuase swap! *strongly*
         ; implies to me that you are going to provide a new _value_, not a fn!
         @atomic-clock))

  "Keep taxes out of this: swapping requires no transaction"
  (= 5 (do
         (swap! atomic-clock #(+ % 4))
         ; nb: the arg order is: [atom fn]
         ; but the new value for the atom is (fn atom)
         @atomic-clock))

  "Any number of arguments might happen during a swap"
  (= 20 (do
          (swap! atomic-clock + 1 2 3 4 5)
          ; when the args are [atom fn a b c]
          ; then the new value for the atom is (fn atom a b c)
          @atomic-clock))

  "Atomic atoms are atomic"
  (= 20 (do
          (compare-and-set! atomic-clock 100 :fin)
          ; this is sugar around an __if__ statement
          ; given (compare-and-set! atom x y)
          ; *if* the current value of the atom equals x,
          ; *then* the new value of the atom will be changed to y
          ; but otherwise the atom is unchanged
          @atomic-clock))

  "When your expectations are aligned with reality, things proceed that way"
  (= :fin (do
            (compare-and-set! atomic-clock 20 :fin)
            ; here we make our x match the incoming/current value, 
            ; so y becomes the outgoing/altered value
            @atomic-clock)))
