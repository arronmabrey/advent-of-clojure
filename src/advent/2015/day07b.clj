(ns advent.2015.day07b
  (:require [clojure.java.io :as io]))

(def prob (->> "2015/prob7" io/resource io/reader line-seq))
(def opfn {'OR 'bit-or 'AND 'bit-and 'NOT 'bit-not 'LSHIFT 'bit-shift-left 'RSHIFT 'bit-shift-right})

(defn invoke [f] (f))
(defn prefix [sym] (symbol (str "bound-" sym)))
(defn with-cache [x] (if (symbol? x) (list 'cache (prefix x)) x))

(defn infix->prefix
  ([xs] (apply infix->prefix xs))
  ([b _ a] [(prefix a) (with-cache b)])
  ([c b _ a] [(prefix a) (list (opfn c) (with-cache b))])
  ([d c b _ a] [(prefix a) (list (opfn c) (with-cache d) (with-cache b))]))

(def parse-line (comp infix->prefix read-string #(str \[ % \])))
(def bindings (into {} (map parse-line) prob))

(defn solve [form ctx]
  (eval
   `(do
      (def cache ~(memoize invoke))
      ~@(for [[k v] ctx] (list 'declare k))
      ~@(for [[k v] ctx] (list 'defn k [] v))
      (~(prefix form)))))

(defn part-1 [] (solve 'a bindings))
(defn part-2 [] (solve 'a (assoc bindings (prefix 'b) (part-1))))

[(part-1) (part-2)] ;; => [956 40149]
