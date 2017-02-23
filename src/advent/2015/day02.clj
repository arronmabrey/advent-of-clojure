(ns advent.2015.day02
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (->> "2015/prob2" io/resource io/reader line-seq))

(defn parse-dims [line]
  (map read-string (str/split line #"x")))

(defn dim->faces [[length width height]] [[length width] [width height] [height length]])

(def product (partial reduce *))
(def squared (partial map #(* 2 %)))
(def smallest-face (comp (partial take 2) sort))
(def face-areas (comp (partial map product) dim->faces))

(defn sum
  ([xs] (reduce + xs))
  ([initial xs] (reduce + initial xs)))

;; part-1 => 1598415
(->>  prob
      (map parse-dims)
      (map #(sum (product (smallest-face %))
                 (squared (face-areas %))))
      sum)

;; part-2 => 3812909
(->>  prob
      (map parse-dims)
      (map #(sum (product %)
                 (squared (smallest-face %))))
      sum)
