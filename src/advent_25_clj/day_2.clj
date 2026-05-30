(ns advent-25-clj.day-2
  (:require [clojure.string :as s]))

(defn- parse-range [s]
  (let [bounds (map parse-long (s/split (s/trim s) #"-"))]
    (range (first bounds) (+ 1 (second bounds)))))

(defn part1-invalid-id? [id]
  (let [id-str (str id)
        middle (-> id-str count (/ 2) int)
        sub-id (partial subs id-str)]
    (= (sub-id 0 middle) (sub-id middle))))

(defn part2-invalid-id? [id]
  (let [id-str (str id)]
    (->> id-str
         count
         (range 1)
         (map #(subs id-str 0 %))
         (some #(->> % re-pattern (s/split id-str) empty?))
         some?)))

(defn solve [invalid-id?]
  (let [input (slurp "input_day2.txt")]
    (->> (s/split input #",")
         (mapcat parse-range)
         (filter invalid-id?)
         (reduce +))))

(defn -main []
  (println "Part 1:" (solve part1-invalid-id?))
  (println "Part 2:" (solve part2-invalid-id?)))
