(ns advent.day05
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [digest :refer [md5]]))

(def prob (str/trim (slurp (io/resource "prob5"))))

;; work space

(defn part1 []
  (->> prob
       (str/split-lines)
       (filter (fn [line]
                 (and (->> line
                           (filter #{\a \e \i \o \u})
                           (count)
                           (<= 3))
                      (->> line
                           (partition 2 1)
                           (map (comp count set))
                           (some #(= 1 %)))
                      (->> line
                           (partition 2 1)
                           (map (partial apply str))
                           (some #{"ab" "cd" "pq" "xy"})
                           (not)))))
       (count)))

(defn part2 []
  (->> prob
       (str/split-lines)
       (filter (fn [line]
                 (and (->> line
                           (partition 2 1)
                           (dedupe)
                           (frequencies)
                           (map val)
                           (some #(>= % 2)))
                      (->> line
                           (partition 3 1)
                           (map (comp sort vals frequencies dedupe))
                           (some #{(list 1 2)})))))
       (count)))
