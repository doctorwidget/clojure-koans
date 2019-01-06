(ns koans.02-strings
  (:require [koan-engine.core :refer :all]
            [clojure.string :as string]))

(meditations
 "A string is nothing more than text surrounded by double quotes"
 (= "hello" "hello") ; double quotes only - single is for suppressing evaluation!
 
 "But double quotes are just magic on top of something deeper"
 (= "world" (str 'world)) ; (str) is like toString in many other languages 
 
 "You can do more than create strings, you can put them together"
 (= "Cool right?" (str "Cool " "right?")) ; (str) also concatenates 
 
 "You can even get certain characters"
 (= \C (get "Characters" 0)) ; Treat a string as a vector of characters
 
 "Or even count the characters"
 (= 11 (count "Hello World")) ; more vector-like string behavior
 
 "But strings and characters are not the same"
 (= false (= \c "c")) ; as in Java, which lurks below
 
 "What if you only wanted to get part of a string?"
 (= "World" (subs "Hello World" 6 11)) ; standard first-included first-excluded indices
 
 "How about joining together elements in a list?"
 (= "123" (string/join '(1 2 3))) ; concatenate and stringify any collection in one call
 
 "What if you wanted to separate them out?"
 (= "1, 2, 3" (string/join ", " '(1 2 3))) ; arity 2 means first arg is delimter
 
 "Maybe you want to separate out all your lines"
 (= ["1" "2" "3"] (string/split-lines "1\n2\n3")) ; splits on newlines
 
 "You may want to make sure your words are backwards"
 (= "olleh" (string/reverse "hello")) ; an evergreen interview question
 
 "Maybe you want to find the index of the first occurrence of a substring"
 (= 0 (string/index-of "hello world" "h")) ; first occurance
 
 "Or maybe the last index of the same"
 (= 13 (string/last-index-of "hello world, hello" "hello")) ; clojure standard library is full-featured
 
 "But when something doesn't exist, nothing is found"
 (= nil (string/index-of "hello world" "bob")) ; nil is the customary not-found indicator
 
 "Sometimes you don't want whitespace cluttering the front and back"
 (= "hello world" (string/trim "  \nhello world \t \n")) ; more standard string operations
 
 "You can check if something is a char"
 (= true (char? \c)) ; Clojure chars are Java chars... 2-byte UTF-16
 
 "But it may not be"
 (= false (char? "a")) ; string != char!
 
 "But chars aren't strings"
 (= false (string? \b)) ; and char != string
 
 "Strings are strings"
 (= true (string? "abc")) ; strings surely keep a collection of chars under the hood, but that's not equality
 
 "Some strings may be blank"
 (= true (string/blank? "")) ; "blank" means "nothing but whitespace"
 
 "Even if at first glance they aren't"
 (= true (string/blank? " \n \t  ")) ; so "blank" strings can be long
 
 "Blankness is not the same as emptiness"
 (= false (empty? "\n")) ; that string is blank but not empty
 
 "Emptiness goes beyond the domain of strings"
 (= true (empty? "") (empty? []) (empty? '())) ; you can use empty? on any collection

 "However, most strings aren't blank"
 (= false (string/blank? "hello?\nare you out there?")))
