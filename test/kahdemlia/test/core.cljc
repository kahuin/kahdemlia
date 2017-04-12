(ns kahdemlia.test.core
  #?(:clj
     (:require
       [clojure.test :refer :all]
       [clojure.core.async :refer [<! go-loop]]
       [taoensso.timbre :as log]
       [kahdemlia.core :as k])
     :cljs
     (:require
       [cljs.test :refer-macros [deftest is testing async]]
       [cljs.core.async :refer [<!]]
       [taoensso.timbre :as log]
       [kahdemlia.core :as k]))
  #?(:cljs
     (:require-macros [cljs.core.async.macros :refer [go-loop]])))

(deftest buckets
  (binding [k/*k* 3]
    (is (= 0 (#'k/octet-bucket 0)))
    (is (= 1 (#'k/octet-bucket 1)))
    (is (thrown? #?(:clj AssertionError :cljs js/Error) (#'k/id-distances [0] [0])))
    (is (= (#'k/id-distances [1 2 3] [4 5 6]) (#'k/id-distances [4 5 6] [1 2 3])))
    (is (= '(0 0 1) (#'k/id-distances "AAAA" "AAAB")))
    (is (= nil (#'k/bucket-index "AAAA" "AAAA")))
    (is (= 0 (#'k/bucket-index "AAAA" "AAAB")))
    (is (= 23 (#'k/bucket-index "AAAA" "____")))))

(binding [k/*k* 3]
  (let [node-ids ["AAAA" "AAAB" "AAEA" "AQAA" "____"]
        nodes (into {} (map (juxt (partial str "node-") #(k/make-node! {:id %}))) node-ids)
        connect-node! (fn [[src-ip {src-chan :network-out-ch}]]
                        (log/info "Connecting" src-ip)
                        (go-loop []
                          (let [incoming (<! src-chan)
                                [dest-ip msg] incoming
                                dest-node (get nodes dest-ip)
                                network-fn (:network-fn dest-node)]
                            (when incoming
                              (log/info "Message from" src-ip "to" dest-ip ":" msg)
                              (if dest-node
                                (network-fn src-ip msg)
                                (log/warn "Destination" dest-ip "not found")))
                            (recur))))]
    (doall (map connect-node! nodes))))
