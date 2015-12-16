(ns advent.day02
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (str/trim (slurp (io/resource "prob2"))))

(def part1
  (->>  prob
        (str/split-lines)
        (map (fn [line]
              (map read-string (str/split line #"x"))))
        (map (fn [[l w h]]
              (let [xyz (map (partial apply *) [[l w] [w h] [h l]])]
               (->> xyz
                    (map (partial * 2))
                    (reduce + (apply min xyz))))))
        (reduce +)))

(def part2
  (->>  prob
        (str/split-lines)
        (map (fn [line]
              (map read-string (str/split line #"x"))))
        (map (fn [lwh]
              (->>  lwh
                    (sort)
                    (take 2)
                    (map (partial * 2))
                    (reduce + (reduce * lwh)))))
        (reduce +)))
