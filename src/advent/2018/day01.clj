(ns advent.2018.day01
  (:require [clojure.java.io :as io]))

(def prob (->> "2018/prob01"
               (io/resource)
               (io/reader)
               line-seq))

(defn parser [line]
  (let [[_ op operand] (re-matches #"([\-\+])(\d+)" line)]
   [(resolve (symbol op)) (read-string operand)]))

(->> prob
     (map parser)
     (reduce (fn [acc [op operand]] (op acc operand) ) 0))
