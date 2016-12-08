(ns advent.2015.day09
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def prob (->> "2015/prob9" (io/resource) (io/reader) line-seq))

(defn parse-line [line]
  (-> line
      (#(str \[ % \]))
      (read-string)
      (->>
       (remove #{'to '=})
       (apply #(vector #{%1 %2} %3)))))

(defn parsed-data [lines]
  (->> lines
       (map parse-line)
       (into {})))

(defn permutations [colls]
  (if (= 1 (count colls))
    (list colls)
    (for [head colls
          tail (permutations (disj (set colls) head))]
            (cons head tail))))

(defn calc [min-max-fn input]
  (let [data (parsed-data input)]
    (->> data
         (keys)
         (apply set/union)
         (permutations)
         (map (fn [perm]
                (->> perm
                     (partition 2 1)
                     (map #(get data (into #{} %)))
                     (reduce +)
                     (vector perm))))
         (sort-by last)
         (min-max-fn)
         (last))))

(calc first prob) ;;=> 117
(calc last prob) ;;=> 909

;;
