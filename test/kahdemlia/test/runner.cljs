(ns kahdemlia.test.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [kahdemlia.test.async]
            [kahdemlia.test.encoding]
            [kahdemlia.test.core]))

(doo-tests 'kahdemlia.test.async
           'kahdemlia.test.encoding
           'kahdemlia.test.core)
