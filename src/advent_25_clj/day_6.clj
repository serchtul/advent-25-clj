(ns advent-25-clj.day-6
  (:require
   [clojure.string :as s]))

(defn parse [item]
  (let [clean (s/trim item)]
    (or (parse-long clean)
        (-> clean symbol resolve))))

(defn solve []
  (->> (slurp "input_day6.txt")
       (s/split-lines)
       (map s/trim)
       (map #(s/split % #"\s+"))
       (map #(map parse %))
       reverse
       (apply map #(apply %1 %&))
       (reduce +)))

(defn -main []
  (println "Part 1:" (solve)))
