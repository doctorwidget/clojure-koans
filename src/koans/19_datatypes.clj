(ns koans.19-datatypes
  (:require [koan-engine.core :refer :all]))

(defrecord Nobel [prize]) ; records are the clj version of structs
(deftype Pulitzer [prize]) ; types are lower-level and lack hashmap functionality

(defprotocol Award         ; protocols are the Clj version of interfaces
  (present [this recipient]))  ; this is the declaration and defintion, not the implementation

(defrecord Oscar [category]  ; Oscar is now a Clj struct that *implements* the Award protocol
  Award
  (present [this recipient]
    (print (str "Congratulations on your "
                (:category this) " Oscar, "
                recipient
                "!")))) ; aka a Clj "class" that implements the Award interface

(deftype Razzie [category]  ; Razzie is a different clj struct, with the same implementations
  Award
  (present [this recipient]
    (print (str "You're really the "
                (.category this) ", " ; THIS is the difference - must access via dot, not kw!
                recipient
               "... sorry."))))

(meditations
  "Holding records is meaningful only when the record is worthy of you"
  (= "peace" (.prize (Nobel. "peace"))) ; args to defrecord are implicitly map entries

  "Types are quite similar"
  (= "literature" (.prize (Pulitzer. "literature"))) ; same holds true for deftypes

  "Records may be treated like maps"
  (= "physics" (:prize (Nobel. "physics"))) ; but defrecords have all the convenience of maps

  "While types may not"
  (= nil (:prize (Pulitzer. "poetry"))) ; whereas deftypes do not

  "Further study reveals why"
  (= [true false]    ; under the hood, a defrecord implements the map protocol/interface
     (map map? [(Nobel. "chemistry")  
                (Pulitzer. "music")])) ; but a deftype does not

  "Either sort of datatype can define methods in a protocol"
  (= "Congratulations on your Best Picture Oscar, Evil Alien Conquerors!"
     (with-out-str (present (Oscar. "Best Picture") "Evil Alien Conquerors")))

  "Surely we can implement our own by now"
  (= "You're really the Worst Picture, Final Destination 5... sorry."
     (with-out-str (present (Razzie. "Worst Picture") "Final Destination 5"))))
