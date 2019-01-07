(ns koans.13-creating-functions
  (:require [koan-engine.core :refer :all]))

(defn square [x] (* x x))

(meditations
 "One may know what they seek by knowing what they do not seek"
 (= [true false true] (let [not-a-symbol? (complement symbol?)]
                        (map not-a-symbol? [:a 'b "c"])))
 ; (complement) is a HOF that doubles the value of the standard library comparators
 
 "Praise and 'complement' may help you separate the wheat from the chaff"
 (= [:wheat "wheat" 'wheat]
    (let [not-nil? (complement nil?)]
      (filter not-nil? [nil :wheat nil "wheat" nil 'wheat nil])))
; it can make things _much_ clearer to define a local helper with (complement)
 
 "Partial functions allow procrastination"
 (= 20 (let [multiply-by-5 (partial * 5)]
         (multiply-by-5 4)))
 ; pin any number arguments to the front of a fn with (partial)
 
 "Don't forget: first things first"
 (= [:a :b :c :d]
    (let [ab-adder (partial concat [:a :b])]
      (ab-adder [:c :d])))
 ; a local (partial) is often the more idiomatic choice in Clojure,  vs a top-level named helper
 
 "Functions can join forces as one 'composed' function"
 (= 25 (let [inc-and-square (comp square inc)]
         (inc-and-square 4)))
 ; (comp) lets you chain functions; they will be applied from RIGHT to LEFT (opposite of reading order)
 
 "Have a go on a double dec-er"
 (= 8 (let [double-dec (comp dec dec)]
         (double-dec 10)))
 ; (though the order of application doesn't matter if you're just repeating the same fn)

 "Be careful about the order in which you mix your functions"
 (= 99 (let [square-and-dec (comp dec square)]
         (square-and-dec 10))))
; again, fn application is RIGHT to LEFT in a (comp) HOF 
