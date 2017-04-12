(ns kahdemlia.test.async
  #?(:clj
     (:require
       [clojure.test :refer :all]
       [clojure.core.async :refer [chan <! >! <!! alts! timeout go]])
     :cljs
     (:require
       [cljs.test :refer-macros [deftest is async]]
       [cljs.core.async :refer [chan <! >! take! alts! timeout]]))
  #?(:cljs
     (:require-macros [cljs.core.async.macros :refer [go]])))

; test-async and test-withing helper functions based on code by Leon Grapenthin

(defn test-async
  "Asynchronous test awaiting ch to produce a value or close.
  NOTE: You cannot have more than one async test per deftest form or only the first one will run."
  [ch]
  #?(:clj  (<!! ch)
     :cljs (async done (take! ch (fn [_] (done))))))

(defn test-within
  "Asserts that ch does not close or produce a value within ms. Returns a
  channel from which the value can be taken."
  [ms ch]
  (go (let [t (timeout ms)
            [v ch] (alts! [ch t])]
        (is (not= ch t)
            (str "Test should have finished within " ms "ms."))
        v)))

(deftest test-helpers
  (let [ch (chan)]
    (go (>! ch "Hello"))
    (test-async (test-within 1000 (go (is (= "Hello" (<! ch))))))))
