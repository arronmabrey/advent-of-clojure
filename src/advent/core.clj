(ns advent.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def prob (str/trim (slurp (io/resource "prob2"))))

;; work space
