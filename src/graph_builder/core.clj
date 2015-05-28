(ns graph-builder.core
  (:gen-class)
  (:require [cayley-clj.core :as cayley]
            [clj-http.client :as http]))

(def seeds ["m.01zh29"])
(def freebase-ns "http://rdf.freebase.com/ns")

(defn full-url
  [id]
  (str freebase-ns id))

(defn get-lines
  "Fetches the rdf file for a machine id on freebase and returns a vector of lines"
  [id]
  (->> id
       full-url
       http/get
       :body
       clojure.string/split-lines
       (map clojure.string/trim)
       (map clojure.string/blank?)))

(def prefix-re #"@prefix (.*): <(.*)>\.")

(defn prefixes-and-triples
  "Split lines into prefixes and non prefix directives"
  [lines]
  (let [[prefixes triples] (split-with #(.startsWith % "@prefix") lines)
        p {}]
    [(map)]))

(defn )

(defn get-freebase
  "Fetches a vector of vectors for the given machine id. Each element is a vector consisting of the subject, predicate and object"
  [id]
  (let [id (str "/" (clojure.string/replace) id "." "/")
        lines (get-lines id)
        [prefixes ns-triples] (prefixes-and-triples lines)
        triples (remove-prefixes prefixes ns-triples)]
    (build-triples id triples)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (map get-freebase seeds))
