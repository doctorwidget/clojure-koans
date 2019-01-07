(ns koans.10-runtime-polymorphism
  (:require [koan-engine.core :refer :all]))

(defn hello    ; arity-based polymorphism is cheap, easy and simple in Clojure
  ([] "Hello World!")
  ([a] (str "Hello, you silly " a "."))
  ([a & more] (str "Hello to this group: "
                   (apply str
                          (interpose ", " (cons a more)))
                   "!")))

(defmulti diet (fn [x] (:eater x))) ; multimethods require you to apparantly re-declare the same function
(defmethod diet :herbivore [a] (str (:name a) " eats veggies."))  ; over and over and
(defmethod diet :carnivore [a] (str (:name a) " eats animals.")) ; over and over...
(defmethod diet :default [a] (str "I don't know what " (:name a) " eats.")) ; which is unsettling until you get used to it
; think of the above declaration chain as building up a big (cond) statement somewhere hidden from view

(meditations
 "Some functions can be used in different ways - with no arguments"
 (= "Hello World!" (hello)) ; arity-based polymophism can fill the role of default arguments
 
 "With one argument"
 (= "Hello, you silly world." (hello "world")) ; but it can also allow different *logic* based on arity
 
 "Or with many arguments"
 (= "Hello to this group: Peter, Paul, Mary!"
    (hello "Peter" "Paul" "Mary"))
 ; so all things considered, arity-based polymorphism is a bigger deal than mere default arguments
 
 "Multimethods allow more complex dispatching"
 (= "Bambi eats veggies."
    (diet {:species "deer" :name "Bambi" :age 1 :eater :herbivore}))
 ; step 1: apply the (defmulti) fn to the original input; that yields :herbivore
 ; step 2: apply the one-and-only fn associated with :herbivore to the _same_ original input!
 
 "Animals have different names"
 (= "Thumper eats veggies."
    (diet {:species "rabbit" :name "Thumper" :age 1 :eater :herbivore}))
 ; so now any entity with both :eater and :name can be used with this multimethod
 
 "Different methods are used depending on the dispatch function result"
 (= "Simba eats animals."
    (diet {:species "lion" :name "Simba" :age 1 :eater :carnivore}))
 ; step 1: apply the (defmulti) fn to the original input; that yields :carnivore
 ; step 2: apply the one-and-only fn associated with :carnivore to the _same_ original input!

 "You may use a default method when no others match"
 (= "I don't know what Rich Hickey eats."
    (diet {:name "Rich Hickey"})))
; step 1: apply the (defmulti) fn to the original input; that yields nil
; step 2: no matching fn for nil, so apply the :default fn to the original input
