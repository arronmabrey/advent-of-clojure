(ns advent.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.walk :as w]))

(def prob (->> "2015/prob7" (io/resource) (io/reader) line-seq))
