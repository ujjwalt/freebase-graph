(defproject graph-builder "0.1.0-SNAPSHOT"
  :description "This program takes a set of freebase machine ids and builds a subgraph around it from reachability"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-http "1.1.2"]
                 [org.clojars.wgb/cayley-clj "0.2.1"]]
  :main ^:skip-aot graph-builder.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
