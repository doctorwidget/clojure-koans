(ns koans.08-conditionals
  (:require [koan-engine.core :refer :all]))

(defn explain-exercise-velocity [exercise-term]
  (case exercise-term
        :bicycling        "pretty fast"
        :jogging          "not super fast"
        :walking          "not fast at all"
        "is that even exercise?"))

(meditations
  "You will face many decisions"
  (= :a (if (false? (= 4 5))  ; if is another fundamental special form
          :a
          :b))

  "Some of them leave you no alternative"
  (= [] (if (> 4 3) ; you don't have to provide the `else` clause
          []))

  "And in such a situation you may have nothing"
  (= nil (if (nil? 0)  ; if you do not provide `else`, you get nil by default
          [:a :b :c]))

  "In others your alternative may be interesting"
  (= :glory (if (not (empty? ())) ; so provide an else or else!
              :doom
              :glory))

  "You may have a multitude of possible paths"
  (let [x 5]  ; (cond) elegantly prevent the need for nested if-else expressions
    (= :your-road (cond (= x 1) :road-not-taken
                        (= x -1) :another-road-not-taken
                        :else :your-road)))

  "Or your fate may be sealed"
  (= 'doom (if-not (zero? 1) ; (if-not) is just (if) with the clause order reversed
          'doom
          'more-doom))

  "In case of emergency, go fast"
  (= "pretty fast"  ; (case) is like cond, but the test is always (= %1 %2)
     (explain-exercise-velocity :bicycling))

  "But admit it when you don't know what to do"
  (= "is that even exercise?"   ; which frankly seems pretty similar to using (get) on a map, with a third (default) arg
     (explain-exercise-velocity :watching-tv)))
