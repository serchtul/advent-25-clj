(ns advent-25-clj.day-2
  (:require [clojure.string :as s]))

(defn parse-range [s]
  (let [bounds (map parse-long (s/split (s/trim s) #"-"))]
    (range (first bounds) (+ 1 (second bounds)))))

(defn part1-invalid-id? [id]
  (let [id-str (str id)
        cutpoint (-> id-str count (/ 2) int)
        invalid? (= (subs id-str 0 cutpoint) (subs id-str cutpoint))]
    #_(when invalid? (println "invalid" id-str))
    invalid?))

(defn part2-invalid-id? [id]
  (let [id-str (str id)
        invalid?
        (->> id-str
             count
             (range 1)
             (map #(subs id-str 0 %))
             (some #(->> % re-pattern (s/split id-str) count zero?))
             some?)]
    #_(when invalid? (println "invalid" id-str))
    invalid?))

(defn solve [invalid-id?]
  (let [input (slurp "input_day2.txt")]
    (->> (s/split input #",")
         (mapcat parse-range)
         (filter invalid-id?)
         (reduce +))))

(defn -main []
  (println "Part 1:" (solve part1-invalid-id?))
  (println "Part 2:" (solve part2-invalid-id?)))
