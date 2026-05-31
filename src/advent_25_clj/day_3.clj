(ns advent-25-clj.day-3
  (:require [clojure.java.io :as io]))

(defn find-max-indexed [coll]
  (->> coll
       (mapv int)
       (reduce-kv (fn [acc i n]
                    (if (>= (:n acc) n) acc {:n n :i i}))
                  {:n -1 :i -1})
       (#(update % :n - (int \0)))))

(defn max-joltage [bank]
  (let [max-tens (-> bank drop-last find-max-indexed)
        max-units (->> bank
                       (drop (+ 1 (:i max-tens)))
                       find-max-indexed)]
    (parse-long (str (:n max-tens) (:n max-units)))))

(defn solve []
  (with-open [input (io/reader "input_day3.txt")]
    (->> input
         line-seq
         (map max-joltage)
         (reduce +))))

(defn -main []
  (println "Part 1:" (solve)))
