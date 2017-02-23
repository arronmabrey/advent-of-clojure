(ns advent.2015.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (-> "2015/prob1" io/resource slurp str/trim))

(def lookup {\( 1 \) -1})

(defn part1 []
  (->>  prob
        (map lookup)
        (reduce +)))

(defn part2 []
  (->>  prob
        (map lookup)
        (reductions +)
        (take-while #(not= -1 %))
        count
        inc))
