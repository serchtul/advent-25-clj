(ns advent-25-clj.day-5
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]))

(defn parse-range [line]
  (let [[lower upper] (s/split line #"-")]
    {:lower (parse-long lower)
     :upper (parse-long upper)}))

(defn fresh-item? [fresh-coll item]
  (some #(<= (:lower %) item (:upper %)) fresh-coll))

(defn parse-input []
  (with-open [input (io/reader "input_day5.txt")]
    (let [[fresh-lines available-lines] (->> input
                                             line-seq
                                             doall
                                             (split-with #(not= % "")))
          fresh     (->> fresh-lines
                         (map parse-range)
                         (sort-by (juxt :lower :upper)))
          available (->> available-lines
                         rest
                         (map parse-long))]
      {:fresh fresh
       :available available})))

(defn solve-part1 []
  (let [{:keys [fresh available]} (parse-input)]
    (->> available
         (filter #(fresh-item? fresh %))
         count)))

(defn remove-overlaps [curr prev]
  (let [prev-upper   (:upper prev)
        overlapping? (some-> prev-upper (>= (:lower curr)))]
    (if overlapping?
      (let [lower (+ prev-upper 1)
            upper (:upper curr)]
        (when (<= lower upper) {:lower lower :upper upper}))
      curr)))

(defn solve-part2 []
  (let [{:keys [fresh]} (parse-input)]
    (->> fresh
         (#(map remove-overlaps (rest %) %))
         (filter some?)
         (cons (first fresh))
         (map #(- (:upper %) (:lower %) -1))
         (reduce +))))

(defn -main []
  (println "Part 1:" (solve-part1))
  (println "Part 2:" (solve-part2)))
