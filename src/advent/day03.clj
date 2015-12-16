(ns advent.day03
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (str/trim (slurp (io/resource "prob3"))))

(defn char->move [char]
  (case char
    \^ [dec identity]
    \> [identity inc]
    \< [identity dec]
    \v [inc identity]))

(defn rotate-cords [[cord1 cord2 :as noop]] (if (vector? cord2) [cord2 cord1] noop))
(defn update-cords [[x y] [xf yf]] [(xf x) (yf y)])
(defn update-house [v] (inc (or v 0)))

(defn part-fn [init-state]
  (->> prob
    (map char->move)
    (reduce (fn [state, move]
              (as-> state s
                    (update-in s [:cords] rotate-cords)
                    (update-in s [:cords 0] update-cords move)
                    (update-in s [:houses (get-in s [:cords 0])] update-house)))
            init-state)
    (:houses)
    (map val)
    (filter #(> % 0))
    (count)))

(defn part1 [] (part-fn {:cords [[0 0]      ] :houses {[0 0] 1}}))
(defn part2 [] (part-fn {:cords [[0 0] [0 0]] :houses {[0 0] 1}}))
