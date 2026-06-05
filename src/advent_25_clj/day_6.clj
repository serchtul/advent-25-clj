(ns advent-25-clj.day-6
  (:require
   [clojure.string :as s]))

(defn parse [item]
  (let [clean (s/trim item)]
    (or (parse-long clean)
        (-> clean symbol resolve))))

(defn solve-part1 []
  (->> (slurp "input_day6.txt")
       (s/split-lines)
       (map s/trim)
       (map #(s/split % #"\s+"))
       (map #(map parse %))
       reverse
       (apply map #(apply %1 %&))
       (reduce +)))

(defn parse2 [item]
  (if (contains? #{\+ \*} (last item))
    [(-> item
         (#(subs % 0 (dec (count %))))
         s/trim
         parse-long)
     (-> item last str symbol resolve)]
    (parse-long (s/trim item))))

(defn solve-part2 []
  (->> (slurp "input_day6.txt")
       (s/split-lines)
       (map s/reverse)
       (map #(s/split % #""))
       (apply map str)
       (map parse2)
       (filter some?)
       flatten
       (partition-by number?)
       (partition 2)
       (map (fn [[args [f]]] (apply f args)))
       (reduce +)))

(defn -main []
  (println "Part 1:" (solve-part1))
  (println "Part 2:" (solve-part2)))
