(ns advent.2015.day07
  (:require [clojure.java.io :as io]
            [clojure.walk :refer [prewalk]]))

(def prob (->> "2015/prob7" io/resource io/reader line-seq))
(def opfn {'OR 'bit-or 'AND 'bit-and 'NOT 'bit-not 'LSHIFT 'bit-shift-left 'RSHIFT 'bit-shift-right})

(defn infix->prefix
  ([xs] (apply infix->prefix xs))
  ([b _ a] [a b])
  ([c b _ a] [a (list (opfn c) b)])
  ([d c b _ a] [a (list (opfn c) d b)]))

(def parse-line (comp infix->prefix read-string #(str \[ % \])))
(def bindings (into {} (map parse-line) prob))

(declare solve)
(defn walker [cache binding-ctx]
  (fn [sym]
    (or (@cache sym)
        (if-let [expression (binding-ctx sym)]
          (let [expression-deps (filter binding-ctx (flatten (conj [] expression)))]
            (if (every? @cache expression-deps)
              (->> expression-deps
                   (select-keys @cache)
                   (solve cache expression)
                   (swap! cache assoc sym)
                   sym)
              expression))
          sym))))

(defn solve [cache sym binding-ctx] (eval (prewalk (walker cache binding-ctx) sym)))

(defn part-1 [] (solve (atom {}) (bindings 'a) bindings)) ;; => 956
(defn part-2 [] (solve (atom {}) (bindings 'a) (assoc bindings 'b (part-1)))) ;; => 40149

[(part-1) (part-2)]



;;
