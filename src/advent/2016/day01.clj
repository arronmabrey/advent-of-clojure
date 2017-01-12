(ns advent.2016.day01
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (->> "2016/prob01" (io/resource) (io/reader) line-seq))
(def samp1 '("R2, L3"))
(def samp2 '("R2, R2, R2"))
(def samp3 '("R5, L5, R5, R3"))
(def samp4 '("R5, L525, R5, R3"))
(def samp5 '("R8, R4, R4, R8"))

(def rotation-map {"R" {"N" "E" "E" "S" "S" "W" "W" "N"} "L" {"N" "W" "W" "S" "S" "E" "E" "N"}})
(def coordinate-map {"N" [0 1] "E" [1 0] "S" [0 -1] "W" [-1 0]})
(def init-state {:heading "N" :coordinate [0 0]})
(def parse-line (comp read-string #(str \[ % \])))
(def split-rotation-qty (comp (partial drop 1) #(re-find #"([RL])(\d+)" %) str))

(defn next-coordinate [next-heading current-coordinate qty]
  (reduce (fn [[current-x current-y] [next-x next-y]]
            [(+ current-x next-x) (+ current-y next-y)])
          current-coordinate
          (repeat (read-string qty) (get coordinate-map next-heading))))

(->> prob
     (mapcat parse-line)
     (map split-rotation-qty)
     (reduce (fn [state [rotation qty]]
               (let [current-heading (:heading state)
                     current-coordinate (:coordinate state)
                     next-heading (get-in rotation-map [rotation current-heading])
                     next-coordinate (next-coordinate next-heading current-coordinate qty)]
                 {:heading next-heading :coordinate next-coordinate}))
             init-state)
     (:coordinate)
     (map #(Math/abs %))
     (apply +))



;;
