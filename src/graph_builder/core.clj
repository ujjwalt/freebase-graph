(ns graph-builder.core
  (:gen-class)
  (:require [cayley-clj.core :as cayley]
            [clj-http.client :as http]))

(def seeds ["m.01zh29"])
(def freebase-ns "http://rdf.freebase.com/ns")

(defn get-lines
  "Fetches the rdf file for a machine id on freebase and returns a vector of lines"
  [id]
  (->> id
       (str freebase-ns)
       http/get
       :body
       clojure.string/split-lines
       (map clojure.string/trim)
       (filter #(not (clojure.string/blank? %)))
       vec))

(def prefix-re #"@prefix (.*): <(.*)>\.")

(defn filter-triples
  [id prefix-hash ntriples]
  (map (fn
         [line]
         (= (freebase-ns prefix-hash) (second (re-find #"^(.*):.*" (first line))))
         )
   (drop-while #(= id %) ntriples)))

(defn full-id
  [id]
  (str "/" (clojure.string/replace id "." "/")))

(defn get-freebase
  "Fetches a vector of vectors for the given machine id. Each element is a vector consisting of the subject, predicate and object"
  [id]
  (let [actual-id (full-id id)
        lines (get-lines actual-id)
        [prefixes ntriples] (split-with #(.startsWith % "@prefix") lines)
        prefix-hash (reduce (fn
                           [hash line]
                           (let [[_ val key] (re-find prefix-re line)]
                             (assoc hash key val))) {} prefixes)]
    (filter-triples id prefix-hash ntriples)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (map get-freebase seeds))
