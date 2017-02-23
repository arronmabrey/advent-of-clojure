(ns advent.2015.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (->> "2015/prob1" io/resource slurp str/trim))

(def part-n
  (->> prob
       (map {\( 1 \) -1})
       (reductions +)))

;; part-1 => 232
(->> part-n
     last)

;; part-2 => 1783
(->> part-n
     (take-while #(not= -1 %))
     count
     inc)
