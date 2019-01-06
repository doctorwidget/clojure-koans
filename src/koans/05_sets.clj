(ns koans.05-sets
  (:require [koan-engine.core :refer :all]
            [clojure.set :as set]))

(meditations
 "You can create a set by converting another collection"
 (= #{3} (set [3 3 3])) ; use (set) to convert from any other collection type
 
 "Counting them is like counting other collections"
 (= 3 (count #{1 2 3})) ; (count) is still universal
 
 "Remember that a set is a *mathematical* set"
 (= #{1 2 3 4 5} (set '(1 1 2 2 3 3 4 4 5 5))) ; note the sweet auto-collapsing of the extras
 
 "But a set literal does *NOT* handle duplicates for you"
 (= "Use the (set) function next time" (try    
                (= #(1 1 1) (set 1 1 1))  ; DOH - error on #{1 1 1 }
                (catch IllegalArgumentException e
                  "Use the (set) function next time")))
 
 "You can ask clojure for the union of two sets"
 (= #{1 2 3 4 5} (set/union #{1 2 3 4} #{2 3 5})) ; fusion of both

 "And also the intersection"
 (= #{2 3} (set/intersection #{1 2 3 4} #{2 3 5})) ; just the overlaps

 "But don't forget about the difference"
 (= #{1 4} (set/difference #{1 2 3 4 5} #{2 3 5}))) ; stuff in first but NOT in second
