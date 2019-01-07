(ns koans.09-higher-order-functions
  (:require [koan-engine.core :refer :all]))

(meditations
 "The map function relates a sequence to another"
 (= [4 8 12] (map (fn [x] (* 4 x)) [1 2 3]))
 ; NB: map actually returns a list, so we're relying on value equality here
 
 "You may create that mapping"
 (= [1 4 9 16 25] (map (fn [x] (* x x)) [1 2 3 4 5])); order is (map function collection)
 
 "Or use the names of existing functions"
 (= '(false false true false false) (map nil? [:a :b nil :c :d])) ; any function can be used at %1
 
 "A filter can be strong"
 (= '() (filter (fn [x] false) '(:anything :goes :here)))
; filter keeps IFF function returns true, so this filter rejects everything automatically
 
 "Or very weak"
 (= [:anything :goes :here] (filter (fn [x] true) '(:anything :goes :here)))
 ; whereas this filter keeps everything automatically
 
 "Or somewhere in between"
 (= [10 20 30] (filter (fn [x] (< x 40)) [10 20 30 40 50 60 70 80]))
 ; and this filter actually does something conditional... finally, a non-silly filter!
 
 "Maps and filters may be combined"
 (= [10 20 30] (map (fn [x] (* x 10)) (filter (fn [x] (< x 4)) [1 2 3 4 5 6 7 8])))
 ; standard inside-out order means the filter precedes the map
 
 "Reducing can increase the result"
 (= 24 (reduce (fn [a b] (* a b)) [1 2 3 4]))
 ; IFF arity-2: (get %2 0) and (get %2 1) are used for the first pair
 
 "You can start somewhere else"
 (= 2400 (reduce (fn [a b] (* a b)) 100 [1 2 3 4]))
 ; IFF arity-3: %2 is `a` and (get %3 0) is `b` in the first cycle

 "Numbers are not the only things one can reduce"
 (= "longest" (reduce (fn [a b]   
                        (if (< (count a) (count b)) b a))
                      ["which" "word" "is" "longest"])))
; (reduce) is best understood as a universal tool for converting a collection to a single element
; it is easy to use (reduce) to re-implement both (map) and (filter) from scratch,
; whereas going the other way around is not easy at all

