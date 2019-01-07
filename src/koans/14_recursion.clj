(ns koans.14-recursion
  (:require [koan-engine.core :refer :all]))

(defn is-even? [n]
  (if (= n 0) ; base case?
    true ; if so, return true, and we're done here
    (not (is-even? (dec n))))) ; otherwise recur
; the is-even? result is always a boolean
; hence the (not) to reverse it IFF we had to recurse
; so this is going to build up a giant stack of nested bools before it eventually finishes
; ie (not (not (not (not (not true))))), just to check "5"!

; that tendency towards giant stacks is why (loop) and (recur) exist
; they let you implement tail recursion, so you don't have an ever-growing stack
; instead you have a sequence of iterations, and the stack stays constant
(defn is-even-bigint? [n]
  (loop [n   n      ; n is always n, both the first time through, and every time thereafter
         acc true]  ; acc is true the first time, but after that it depends on (recur)
    (if (= n 0)     ; base base?
      acc           ; if so, remember that acc is the most-recent bool... it is not a number!
      (recur (dec n) (not acc))))) ; here's the (recur) call; it finds the nearest (loop)

(defn recursive-reverse [coll]
  (loop [new '()    ; new is an empty list the first time through; after that it depends on (recur)
         old coll]  ; old is the input collection the first time through; after that it depends on (recur)
    (if (empty? old) ; base case?
      new            ; if so, return the _current_ value of new... probably not still empty!
      (recur (conj new (first old)) (rest old))))) ; here's the (recur), which finds the enclosing (loop)

(defn factorial [n]
  (if (<= n 1)  ; base case?
    1           ; if so, return 1
    (* n (factorial (dec n))))) ; if not, recurse until you find the answer

(meditations
  "Recursion ends with a base case"
  (= true (is-even? 0))

  "And starts by moving toward that base case"
  (= false (is-even? 1))

  "Having too many stack frames requires explicit tail calls with recur"
  (= false (is-even-bigint? 100003N))

  "Reversing directions is easy when you have not gone far"
  (= '(1) (recursive-reverse [1]))

  "Yet it becomes more difficult the more steps you take"
  (= '(6 5 4 3 2) (recursive-reverse [2 3 4 5 6]))

  "Simple things may appear simple."
  (= 1 (factorial 1)) ; back to simple recursion; no (loop)(recur)

  "They may require other simple steps."
  (= 2 (factorial 2))

  "Sometimes a slightly bigger step is necessary"
  (= 6 (factorial 3))

  "And eventually you must think harder"
  (= 24 (factorial 4))

  "You can even deal with very large numbers"
  (< 1000000000000000000000000N (factorial 1000N)) 

  "But what happens when the machine limits you?"
  (< 1000000000000000000000000N (factorial 3500N)))
  ; this matchine fails somewhere over 3500N and below 4000N
