(ns advent.day07
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [digest :refer [md5]]))

(def prob (str/trim (slurp (io/resource "prob7"))))

;; work space

(defn parse-token [token]
  (cond
    (re-matches #"\d+" token) (read-string token)
    (re-matches #"[a-z]+" token) (keyword token)
    (re-matches #"->" token) (symbol token)
    (re-matches #"[A-Z]+" token) (str token)))

(def op-map {"NOT" bit-not
             "AND" bit-and
             "OR"  bit-or
             "LSHIFT" bit-shift-left
             "RSHIFT" bit-shift-right})

(def dispatch-table
  (->> prob
       (str/split-lines)
       (map str/trim)
       (map #(str/split % #"\s"))
       (map (partial map parse-token))
       (map (partial reduce (fn [acc, token]
                              (cond
                                (number? token) (conj acc token)
                                (keyword? token) (conj acc token)
                                (string? token) (vec (cons token acc))
                                (symbol? token) (conj [token] acc))) []))
       (reduce (fn [acc [_ v k]] (conj acc [k v]))
               {})))

(defn solve
  ([state dt k]
   (let [fallback [k]
         next-op (get dt k fallback)
         fnext-op (first next-op)]
     (cond
       (number? fnext-op) fnext-op
       (keyword? fnext-op) (or (get @state fnext-op)
                               (get (swap! state assoc fnext-op (solve state dt fnext-op)) fnext-op))
       (string? fnext-op) (or (get @state k)
                              (get (swap! state assoc k (apply solve state dt k next-op)) k))
       :else (throw (Exception. "unknown fnext-op " fnext-op)))))
  ([state dt k op & args]
   (apply (op-map op) (map (partial solve state dt) args))))

(defn part1 [] (solve (atom {}) dispatch-table :a))
(defn part2 [] (solve (atom {}) (assoc dispatch-table :b [(part1)]) :a))
