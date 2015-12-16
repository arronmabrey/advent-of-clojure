(ns advent.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (str/trim (slurp (io/resource "prob1"))))

(def part1
  (->>  prob
        (map #(if (= % \() 1 -1))
        (reduce +)))

(def part2
  (->>  prob
        (map-indexed #(vec [%1 (if (= %2 \() 1 -1)]))
        (reduce (fn [acc [idx step]]
                  (if (= (+ acc step) -1)
                      (reduced (inc idx))
                      (+ acc step)))
                0)))
