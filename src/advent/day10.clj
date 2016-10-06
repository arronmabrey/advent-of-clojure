(ns advent.day10
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def prob (->> "prob10" (io/resource) (io/reader) line-seq))
(def samp (->> "samp10" (io/resource) (io/reader) line-seq))

(defn pp [x] (clojure.pprint/pprint x) x)

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
