(ns advent-25-clj.day-3
  (:require [clojure.java.io :as io]))

(defn find-max-indexed [coll]
  (->> coll
       (mapv int)
       (reduce-kv (fn [acc i n]
                    (if (>= (:n acc) n) acc {:n n :i i}))
                  {:n -1 :i -1})
       (#(update % :n - (int \0)))))

(defn max-joltage [num-batteries bank]
  (loop [curr-bank bank
         idx       1
         result    []]
    (let [{:keys [n i]} (->> curr-bank
                             (drop-last (- num-batteries idx))
                             find-max-indexed)
          new-result    (conj result n)]
      (if (< idx num-batteries)
        (recur (drop (+ i 1) curr-bank)
               (inc idx)
               new-result)
        (->> new-result (apply str) parse-long)))))

(defn solve [num-batteries]
  (with-open [input (io/reader "input_day3.txt")]
    (->> input
         line-seq
         (map #(max-joltage num-batteries %))
         (reduce +))))

(defn -main []
  (println "Part 1:" (solve 2))
  (println "Part 2:" (solve 12)))
