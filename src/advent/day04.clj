(ns advent.day04
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [digest :refer [md5]]))

(def prob (str/trim (slurp (io/resource "prob4"))))

;; work space

(defn part-n [seed pattern]
  (first (sequence  (comp (map #(vec [% (md5 (str seed %))]))
                          (filter #(re-matches pattern (last %)))
                          (take 1)
                          cat)
                    (range))))

(def part1 (part-n prob #"^0{5}.*"))
(def part2 (part-n prob #"^0{6}.*"))
