(ns koans.16-refs
  (:require [koan-engine.core :refer :all]))

(def the-world (ref "hello")) ; refs are like atoms, but thread-safe
; In addition to be thread-safe, refs allow you to *coordinate* changes
; between more than one ref, when they are both involved in a transaction.
; E.g. the classic two-bank-accounts scenario, where you need both changes or neither.
; So refs are not solely about thread-safety; they are also about __transactions__
(def bizarro-world (ref {})) 

; Note that this file has an ever-changing value for 'the-world
; This is totally different from the behavior we've seen in all koans until now
; This is the *entire point* of refs (and atoms in the next chapter)(aka reference types)
; They are the sandboxes into which you safely quarantine all your mutable state
; In other languages, there is no such quarantining available to you out of the box. 
; And that results in omnipresent spooky action at a distance :frown
; But refs & atoms allow you keep most of your code in the unspooky light of day :hooray

(meditations
  "In the beginning, there was a word"
  (= "hello" (deref the-world)) ; you deref a ref just like you would an atom

  "You can get the word more succinctly, but it's the same"
  (= "hello" @the-world) ; in practice, everyone always uses '@', not (deref)

  "You can be the change you wish to see in the world."
  (= "better" (do   ; the price of thread safety is the need to wrap all changes in (dosync)
          (dosync (ref-set the-world "better")) 
          @the-world))

  "Alter where you need not replace"
  (= "better!!!" (let [exclamator (fn [x] (str x "!"))]
          (dosync ; within this (dosync), you can run multiple changes with the guarantee
           (alter the-world exclamator)  ; that they will all run to completion
           (alter the-world exclamator)  ; without interference from other threads
           (alter the-world exclamator))
          @the-world)) ; and then at the end, we're back to atom-like access

  "Don't forget to do your work in a transaction!"
  (= 0 (do (dosync (ref-set the-world 0)) ; reminder: all changes get wrapped in (dosync)
           @the-world))

  "Functions passed to alter may depend on the data in the ref"
  (= 20 (do
          (dosync (alter the-world #(+ 20 %)))))

  "Two worlds are better than one" ; As noted above, refs are required for __transactions__
  (= ["Real Jerry" "Bizarro Jerry"] ; You need refs to get a "all or none" guarantee for the changes
       (do  
         (dosync   ; otherwise, in the absence of the "all or none" transaction guarantee
          (ref-set the-world {})  ; you might unset 'the-world
          (alter the-world assoc :jerry "Real Jerry")  ; and then fail to set new values
          (alter bizarro-world assoc :jerry "Bizarro Jerry") ; and THEN where would you be?
          (map :jerry [@the-world @bizarro-world])))))
