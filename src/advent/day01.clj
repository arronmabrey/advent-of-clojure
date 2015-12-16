(ns advent.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (str/trim (slurp (io/resource "prob1"))))

(def part1 (reduce + (map #(if (= % \() 1 -1) prob)))

(def part2 (reduce (fn [acc, [idx, step]] (let [r (+ acc step)] (if (= r -1) (reduced (inc idx)) r))) 0 (map-indexed #(vec [%1 (if (= %2 \() 1 -1)]) prob)))
