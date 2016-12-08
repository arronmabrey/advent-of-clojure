(ns advent.2015.day08
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (->> "2015/prob8" (io/resource) (io/reader) line-seq))

(defn unescape-count [line]
  (->> line
       (re-seq #"[a-zA-Z0-9]|\\\"|\\x[0-9a-f]{2}|\\\\")
       (count)))

(defn escape-count [line]
  (-> line
      (str/replace "\\" "\\\\")
      (str/replace "\"" "\\\"")
      (count)
      (+ 2)))

(defn map-sum [f1 f2 c]
  (reduce + (map #(- (f1 %) (f2 %)) c)))

(map-sum count unescape-count prob) ;;=> 1342
(map-sum escape-count count prob) ;;=> 2074

;;
