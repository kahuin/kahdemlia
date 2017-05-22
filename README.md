# ![K](docs/images/icon-56.png) kahdemlia

An asynchronous, network-agnostic Clojure & Clojurescript implementation of the Kademlia distributed
hash table. 

[![CircleCI](https://circleci.com/gh/kahuin/kahdemlia.svg?style=svg)](https://circleci.com/gh/kahuin/kahdemlia)

## Usage

Add the dependency to your leinigen or boot project configuration:

[![Clojars Project](https://img.shields.io/clojars/v/kahdemlia.svg)](https://clojars.org/kahdemlia)

Then in your project create a node:

```clojure
(ns kad-dht
  (:require [clojure.core.async :refer [<! go-loop]]
             ; ^^ or cljs.core.async and cljs.core.async.macros if you are using CLJS
            [kahdemlia.core :refer [make-node!]]))

(let [{:keys [network-fn network-out-ch store-fn find-fn found-ch]}
      (make-node!)]
  (go-loop []
    (let [[dest msg] (<! network-out-ch)]
      ; SEND MESSAGE TO DESTINATION
      (recur)))
  (go-loop []
    (let [[key val] (<! found-ch)]
      ; HANDLE KEY LOOKUP
      (recur)))
  (def on-network-message network-fn)
  (def find find-fn)
  (def store! store-fn))
```

## Development

  * [Original Kademlia paper](docs/reference/maymounkov-kademlia-short.pdf)
  * [Unofficial specification](docs/reference/xlattice-kademlia-specs.pdf)

### Tests

Tests uses clojure.test for clojure and rhino via doo for clojurescript:

`lein test` 

## License

Copyright © 2017 Camilo Polymeris

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
