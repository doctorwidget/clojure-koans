(ns koans.03-lists
  (:require [koan-engine.core :refer :all]))

(meditations
 "Lists can be expressed by function or a quoted form"
 (= '(1 2 3 4 5) (list 1 2 3 4 5)) ; quoted form is more commone when applicable
 
 "They are Clojure seqs (sequences), so they allow access to the first"
 (= 1 (first '(1 2 3 4 5))) ; extract first as an element, not a list of 1
 
 "As well as the rest"
 (= '(2 3 4 5) (rest '(1 2 3 4 5))) ; get remainder as a list
 
 "Count your blessings"
 (= 3 (count '(dracula dooku chocula))) ; (count) is universal for all collections
 
 "Before they are gone"
 (= 0 (count '()))

 "The rest, when nothing is left, is empty"
 (= '() (rest '(100))) ; reST always returns a liST
 
 "Whereas next, when nothing is left, is nothing"
 (= nil (next '(100))); but __N__ext returns __N__il in the same scenario

 "Construction by adding an element to the front is easy"
 (= '(:a :b :c :d :e) (cons :a '(:b :c :d :e))) ;  classic lispy linked lists 

 "Conjoining an element to a list isn't hard either"
 (= '(:e :a :b :c :d) (conj '(:a :b :c :d) :e)) ; conj does the best thing for the collection type

 "You can use a list like a stack to get the first element"
 (= :a (peek '(:a :b :c :d :e))) ; peek is like first, but you save a char 

 "Or the others"
 (= '(:b :c :d :e) (pop '(:a :b :c :d :e))) ; pop is like rest, but you save a char

 "But watch out if you try to pop nothing"
 (= "No dice!" (try    ; pop has no sane default for empty, unlike next (nil) and rest (an empty list)
         (pop '())
         (catch IllegalStateException e
           "No dice!")))

 "The rest of nothing isn't so strict"
 (= '() (try  ; reST always returns a liST
         (rest '())
         (catch IllegalStateException e
           "No dice!"))))
