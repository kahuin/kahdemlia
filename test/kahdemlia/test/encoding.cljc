(ns kahdemlia.test.encoding
  #?(:clj
     (:require
       [clojure.test :refer :all]
       [kahdemlia.encoding :as enc])
     :cljs
     (:require
       [cljs.test :refer-macros [deftest is testing]]
       [kahdemlia.encoding :as enc])))

(deftest base64
  (is (= nil (enc/base64->octets "")))
  (is (= "" (enc/octets->base64 '())))
  (is (= '(0 0 0) (enc/base64->octets "AAAA")))
  (is (= '(105 183 29 123 255) (enc/base64->octets "abcde_-")))
  (is (= "AAECAwQFBgcICQoLDA0ODxAREhM" (enc/octets->base64 (range 20))))
  (is (= "AAAA" (enc/octets->base64 '(0 0 0))))
  (is (= "AAAB" (enc/octets->base64 '(0 0 1))))
  (is (= "AAEA" (enc/octets->base64 '(0 1 0))))
  (is (= "AQAA" (enc/octets->base64 '(1 0 0))))
  (is (= "_wAA" (enc/octets->base64 '(255 0 0))))
  (is (= "____" (enc/octets->base64 '(255 255 255))))
  (is (= "kademlia" (enc/octets->base64 (enc/base64->octets "kademlia")))))
