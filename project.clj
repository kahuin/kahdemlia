(defproject kahdemlia "0.1.0-SNAPSHOT"
  :description "An asynchronous Clojure & Clojurescript implementation of the Kademlia distributed hash table."
  :url "https://github.com/polymeris/kahdemlia"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha15"]
                 [org.clojure/core.async "0.3.442"]
                 [org.clojure/clojurescript "1.9.495"]
                 [com.taoensso/timbre "4.8.0"]]
  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-doo "0.1.7"]]
  :doo {:paths {:rhino "lein run -m org.mozilla.javascript.tools.shell.Main"}}
  :aliases {"test-cljs" ["with-profile" "test" "doo" "rhino" "test" "once"]
            "test-clj"  ["test"]
            "test"      ["do" ["test-clj"] ["test-cljs"]]}
  :profiles {:test {:dependencies [[org.mozilla/rhino "1.7.7.1"]]
                    :cljsbuild    {:builds
                                   {:test {:source-paths ["src" "test"]
                                           :compiler     {:output-to     "target/main.js"
                                                          :output-dir    "target"
                                                          :main          kahdemlia.test.runner
                                                          :optimizations :simple}}}}}})
