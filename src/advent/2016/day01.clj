(ns advent.2016.day01
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (->> "2016/prob01" (io/resource) (io/reader) line-seq))
(def samp1 '("R2, L3"))
(def samp2 '("R2, R2, R2"))
(def samp3 '("R5, L5, R5, R3"))
(def samp4 '("R5, L525, R5, R3"))

(def parse-line
  (comp read-string #(str \[ % \])))

(def rk {"R" {"N" "E" "E" "S" "S" "W" "W" "N"} "L" {"N" "W" "W" "S" "S" "E" "E" "N"}})
(def pk {"N" [0 1] "E" [1 0] "S" [0 -1] "W" [-1 0]})

(->> prob
     (mapcat parse-line)
     (map (comp (partial drop 1) #(re-find #"([RL])(\d+)" %) str))
     (reduce (fn [acc [r n]]
               (let [d (get-in rk [r (:d acc)])
                     p (:p acc)
                     ps (repeat (read-string n) (get pk d))]
                 (-> acc
                     (assoc :d d)
                     (assoc :p (reduce (fn [[a1 a2] [d1 d2]] [(+ a1 d1) (+ a2 d2)])
                                       p
                                       ps)))))
             {:d "N" :p [0 0]})
     (:p)
     (apply +)
     (Math/abs))

;;
