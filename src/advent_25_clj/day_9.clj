(ns advent-25-clj.day-9
  (:require
   [clojure.java.io :as io]
   [clojure.math.combinatorics :as combo]
   [clojure.string :as str]))

(defn parse-line [line]
  (->> (str/split line #",")
       (map parse-long)))

(defn length [x1 x2]
  (-> (- x1 x2) abs (+ 1)))

(defn area [points]
  (let [[[x1 y1] [x2 y2]] points]
    (* (length x1 x2) (length y1 y2))))

(defn part-1 [lines]
  (->> (combo/combinations lines 2)
       (map area)
       (sort >)
       first))

(defn -main []
  (with-open [input (io/reader "input_day9.txt")]
    (let [result-1 (->> input line-seq (map parse-line) part-1)]
      (println "Part 1:" result-1))))
