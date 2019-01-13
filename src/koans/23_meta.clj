(ns koans.23-meta
  (:require [koan-engine.core :refer :all]))

(def giants ; metadata lets you attach info to symbols that does __not__ mess with equality
  (with-meta 'Giants ; so we get back the symbol 'Giants, but with the map of metadata
    {:league "National League"}))

(meditations
  "Some objects can be tagged using the with-meta function"
  (= {:league "National League"} (meta giants))  ; then you can use (meta) to inspect that metadata later on

  "Or more succinctly with a reader macro" ; this is an alternative to (def giants ...) above
  (= {:division "West"} (meta '^{:division "West"} Giants)) ; not an alt for the first meditation!

  "While others can't"  ; you cannot attach metadata to primitives!
  (= "This doesn't implement the IObj interface" (try
          (with-meta
            2           ; 2 is a primitive, so this throws an error
            {:prime true})
          (catch ClassCastException e
            "This doesn't implement the IObj interface")))

  "Notice when metadata carries over"  ; first argument to merge keeps its metadata
  (= {:foo :bar} (meta (merge '^{:foo :bar} {:a 1 :b 2}
                     {:b 3 :c 4})))

  "And when it doesn't"  ; second argument to merge loses any metadata
  (= nil (meta (merge {:a 1 :b 2}
                     '^{:foo :bar} {:b 3 :c 4})))

  "Metadata can be used as a type hint to avoid reflection during runtime"
  (= \C (#(.charAt ^String % 0) "Cast me")) ; optimization

  "You can directly update an object's metadata"
  (= 8 (let [giants
             (with-meta
               'Giants
               {:world-series-titles (atom 7)})]
         (swap! (:world-series-titles (meta giants)) inc)
         @(:world-series-titles (meta giants))))

  "You can also create a new object from another object with metadata"
  (= {:league "National League" :park "AT&T Park"}
     (meta (vary-meta giants  ; there are no atoms here, so we get a new object back 
                      assoc :park "AT&T Park")))

  "But it won't affect behavior like equality"
  (= 'Giants (vary-meta giants dissoc :league))

  "Or the object's printed representation"
  (= "Giants" (pr-str (vary-meta giants dissoc :league))))
