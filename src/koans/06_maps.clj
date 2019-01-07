(ns koans.06-maps
  (:require [koan-engine.core :refer :all]))

(meditations
 "Don't get lost when creating a map"
 (= {:a 1 :b 2} (hash-map :a 1 :b 2)) ; (hash-map) accepts loose lists
 
 "A value must be supplied for each key"
 (= {:a 1} (hash-map :a 1)) ; but the loose list must have an even length
 
 "The size is the number of entries"
 (= 2 (count {:a 1 :b 2})) ; count is still universal, but here it is counting KV pairs
 
 "You can look up the value for a given key"
 (= 2 (get {:a 1 :b 2} :b)) ; (get) is the most-general lookup function
 
 "Maps can be used as functions to do lookups"
 (= 1 ({:a 1 :b 2} :a)) ; the map can sit in the function position
 
 "And so can keywords"
 (= 1 (:a {:a 1 :b 2})) ; keywords can also sit in the function position
 
 "But map keys need not be keywords"
 (= "Sochi" ({2010 "Vancouver" 2014 "Sochi" 2018 "PyeongChang"} 2014))
 ; anything at all can be a key, which is one of the superpowers of clojure maps
 
 "You may not be able to find an entry for a key"
 (= nil (get {:a 1 :b 2} :c)) ; nil is the standard not-found response
 
 "But you can provide your own default"
 (= :key-not-found (get {:a 1 :b 2} :c :key-not-found)) ; only (get) allows you to provide a default 
 ; keywords and maps sitting in the function position cannot do that

 "You can find out if a key is present"
 (= true (contains? {:a nil :b nil} :b)) ; (contains?) is a *key* check, not a *value* check

 "Or if it is missing"
 (= false (contains? {:a nil :b nil} :c)) ; which is helpful when nil is an acceptable value

 "Maps are immutable, but you can create a new and improved version"
 (= {1 "January" 2 "February"} (assoc {1 "January"} 2 "February")) ; cheap augmented clones are another Clojure win

 "You can also create a new version with an entry removed"
 (= {1 "January"} (dissoc {1 "January" 2 "February"} 2)) ; cheap augmented clones are EVERYWHERE

 "Create a new map by merging"
 (= {:a 1 :b 2 :c 3} (merge {:a 1 :b 2} {:c 3})) ; priority goes left (lowest) to right (highest)

 "Specify how to handle entries with same keys when merging"
 (= {:a 1 :b 8 :c 3} (merge-with + {:a 1 :b 5} {:b 3 :c 3})); you are not limited to simple overwrites

 "Often you will need to get the keys, but the order is undependable"
 (= (list 2010 2014 2018)
    (sort (keys {2014 "Sochi" 2018 "PyeongChang" 2010 "Vancouver"}))) ; so here we explicitly sort

 "You can get the values in a similar way"
 (= (list "PyeongChang" "Sochi" "Vancouver")
    (sort (vals {2010 "Vancouver" 2014 "Sochi" 2018 "PyeongChang"}))) ; NB: you get 'natural' sort order by default

 "You can even iterate over the map entries as a seq"
 (= {:a 2 :b 3}
    (into {}    ; {} is the sink collection type; arg #2 will go into it
          (map  ; the results of this mapping operation are the collection used as arg #2
           (fn [[k v]] [k (inc v)]) ; this map function returns a [k v] 2-vector, where each v has been incremented
           {:a 1 :b 2})))) ; iterating over a map means dealing with one KV pair at a time, each as a 2-vector
