(ns koans.04-vectors
  (:require [koan-engine.core :refer :all]))

(meditations
 "You can use vectors in clojure as array-like structures"
 (= 1 (count [42])) ; (count) is universal
 
 "You can create a vector from a list"
 (= [1] (vec '(1))) ; (vec) converts a single collection to a single vector
 
 "Or from some elements"
 (= [nil nil] (vector nil nil)) ; (vector) converts many things to a single vector 
 
 "But you can populate it with any number of elements at once"
 (= [1 2] (vec '(1 2))) ; the one collection for (vec) can be any length
 
 "Conjoining to a vector is different than to a list"
 (= [111 222 333] (conj [111 222] 333)) ; (conj) adds to the most-efficient side for the collection type
 ; that's one of the primary value-added features of (conj) over (cons)
 ; for vectors, the most-efficient side is the end, so that's where the new element goes
 ; (remember that it was the front for lists, because that's the most-efficent side for lists)
 ; (the other value-added feature of conj is that it preserves the collection type)
 
 "You can get the first element of a vector like so"
 (= :peanut (first [:peanut :butter :and :jelly])) ; just like (first) on a list
 
 "And the last in a similar fashion"
 (= :jelly (last [:peanut :butter :and :jelly])) ; just like (last) on a list

 "Or any index if you wish"
 (= :and (nth [:peanut :butter :and :jelly] 2)) ; NB: nth throws an IndexOutOfBoundsError if you go too far

 "You can also slice a vector"
 (= [:butter :and] (subvec [:peanut :butter :and :jelly] 1 3)); first-included + first-excluded indices 

 "Equality with collections is in terms of values"
 (= (list 1 2 3) (vector 1 2 3))) ; cheap-and-easy value equality testing is one of Clojure's big wins
