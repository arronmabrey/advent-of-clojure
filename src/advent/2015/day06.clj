(ns advent.2015.day06
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [digest :refer [md5]]))

(def prob (str/trim (slurp (io/resource "2015/prob6"))))

;; work space

(def rows 1000)
(def cols 1000)

(defn cord->idx [[row col]]
  (+ col (* cols row)))

(defn rect-cords->idxs [[[row1 col1 :as cord1] [row2 col2 :as cord2]]]
  (->> (range (cord->idx cord1) (inc (cord->idx cord2)))
       (partition (inc (- col2 col1)) cols)
       (flatten)))

(def line->op-idxs
  (comp (fn [[op & rect-cords]]
          [op (rect-cords->idxs rect-cords)])
        (partial map (comp (fn [[cord1 cord2 :as rect-cords-or-op]]
                             (if cord2
                               (map read-string rect-cords-or-op)
                               (keyword (first rect-cords-or-op))))
                           #(str/split % #",")))
        rest
        (partial re-find #"(\w+)\s([\d,]+)\s\w+\s([\d,]+)")))

(defn part-n [update-map]
  (->> prob
       (str/split-lines)
       (map line->op-idxs)
       (reduce (fn [st1 [op idxs]]
                 (reduce (fn [s2 idx]
                           (update s2
                                   idx
                                   (op update-map)))
                         st1
                         idxs))
               {})
       (vals)))

(defn part1 []
  (->> (part-n {:on (fn [v] true)
                :off (fn [v] false)
                :toggle (fn [v] (not v))})
       (keep #{true})
       (count)))

(defn part2 []
  (->> (part-n {:on (fn [v] (inc (or v 0)))
                :off (fn [v] (max 0 (dec (or v 0))))
                :toggle (fn [v] (+ 2 (or v 0)))})
       (reduce + 0)))
