(ns advent.day07
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [clojure.walk :as w]))

(def prob (->> "prob7" (io/resource) (io/reader) line-seq))

(def opfn {'OR 'bit-or 'AND 'bit-and 'NOT 'bit-not 'LSHIFT 'bit-shift-left 'RSHIFT 'bit-shift-right})

(defn infix->prefix
  ([b _ a] [a b])
  ([c b _ a] [a (list (opfn c) b)])
  ([d c b _ a] [a (list (opfn c) d b)]))

(def parse-line
  (comp (partial apply infix->prefix) read-string #(str \[ % \])))

(def bindings
  (into {} (map parse-line) prob))

(declare solve)

(defn walker [cache binds]
  (fn [expr]
    (or (@cache expr)
        (if-let [bound-expr (binds expr)]
          (let [deps (filter binds (flatten (conj [] bound-expr)))]
            (if (every? @cache deps)
              (->> deps
                   (map (fn [dep] [dep (@cache dep)]))
                   (into {})
                   (solve bound-expr)
                   (swap! cache assoc expr)
                   (expr))
              bound-expr))
          expr))))

(defn solve [form binds]
  (eval (w/prewalk (walker (atom {}) binds) form)))

(defn part1 [] (solve (bindings 'a) bindings))
(defn part2 [] (solve (bindings 'a) (assoc bindings 'b (part1))))
