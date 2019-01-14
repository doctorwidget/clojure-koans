(ns koans.17-atoms
  (:require [koan-engine.core :refer :all]))

(def atomic-clock (atom 0))
; Atoms are a more-limited version of refs; almost anything you can do with an atom you 
; could also do with a ref, but NOT vice versa.  The one operation that refs can perform
; that atoms cannot is the __transaction__. You cannot coordinate changes across multiple
; atoms and guarantee that the changes will be all or none.  You *can* guarantee that 
; any changes to one single atom will not be interrupted or abandoned partway through.
;
; The upside of atoms is a simpler syntax than refs, with less ceremony required for use
; (i.e. you don't have to wrap all your changes to an atom in a (dosync) block).
; Also, ClojureScript only gives you atoms, not refs, whether you like it or not
;
; So it's important to remember that atoms *are* thread safe -- you never have to worry 
; about another thread changing part of (say) an atom-wrapped map out from under you in
; the middle of operating on it. Atoms have this safety because the functions used to
; change them (swap! and reset! and etc) are guaranteed to be *synchronous*. Clojure 
; handles all of the JVM-specific lock details under the hood for you, and you are free
; to share a single atom across threads without worrying about it ending up in some kind
; of oxymoronic / nonsensical state.  
;


(meditations
  "Atoms are like refs"
  (= 0 @atomic-clock)

  "You can change at the swap meet"
  (= 1 (do
         (swap! atomic-clock inc)
         ; I would have called this opertion (alter!), becuase swap! *strongly*
         ; implies to me that you are going to provide a new _value_, not a fn!
         ; but atoms use (reset!) for that - another name I would change!
         ; because "reset!" strongly implies to me setting a value of nil or 0 or empty
         ; but the terms are what they are, so get over it!
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
