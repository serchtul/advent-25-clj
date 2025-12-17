(ns advent-25-clj.core
  (:gen-class))
(require '[clojure.java.io :as io])

(defn parse-reading [reading]
  (let [sign (if (= (first reading) \L) -1 1)]
    (* sign (parse-long (subs reading 1)))))

(defn rotate-dial [{curr-count :count, zeros :zeros} value]
  (let [next-count (mod (+ curr-count value) 100)]
    {:count (if (neg? next-count) (+ next-count 100) next-count)
     :zeros (if (zero? next-count) (inc zeros) zeros)}))

(defn -main
  []
  (with-open [reader (io/reader "input_day1.txt")]
    (println "Result:"
             (reduce rotate-dial {:count 50 :zeros 0} (map parse-reading (line-seq reader))))))

