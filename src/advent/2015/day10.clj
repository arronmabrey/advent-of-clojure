(ns advent.2015.day10
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def prob (->> "2015/prob10"
               (io/resource)
               (io/reader)
               line-seq))

(def samp (->> "2015/samp10"
               (io/resource)
               (io/reader)
               line-seq))

(defn look-and-say [input]
  (->> input
       (apply str)
       (partition-by identity)
       (map #(str (count %) (first %)))))

(defn calc [input iterations]
  (->> input
       (iterate look-and-say)
       (take (inc iterations))
       (last)
       (apply str)
       (count)))

(calc samp 5) ;;=> 6
(calc prob 40) ;;=> 252594
(calc prob 50) ;;=> 3579328

;;
