(ns advent-25-clj.day-8
  (:require [clojure.java.io :as io]
            [clojure.math.combinatorics :as combo]
            [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-coord [line]
  (map parse-long (str/split line #",")))

(defn with-distance-squared [points]
  {:points points
   :d-squared (->> points
                   (apply map -)
                   (map #(* % %))
                   (reduce +))})

(defn sorted-closest-pairs []
  (with-open [reader (io/reader "input_day8.txt")]
    (->> reader
         line-seq
         (map parse-coord)
         (#(combo/combinations % 2))
         (map with-distance-squared)
         (sort-by :d-squared)
         (map :points))))

(defn build-circuits [continue? box-pairs]
  (reduce-kv (fn [{:keys [circuits]} idx box-pair]
               (let [existing-idxs (->> circuits
                                        (keep-indexed #(when (some %2 box-pair) %1))
                                        set)
                     curr-circuit  (->> existing-idxs
                                        (map #(nth circuits %))
                                        (reduce set/union))
                     next-circuit  (apply conj curr-circuit box-pair)
                     next-circuits (->> circuits
                                        (keep-indexed #(when (not (contains? existing-idxs %1)) %2))
                                        (#(conj % next-circuit)))
                     next-fn       (if (continue? next-circuits (inc idx) box-pairs) identity reduced)]
                 (next-fn {:last box-pair
                           :circuits next-circuits})))
             {:circuits []}
             (vec box-pairs)))

(defn part-1-continue? [_ idx _] (< idx 1000))
(defn part-2-continue? [circuits _ box-pairs]
  (not (and (= (count circuits) 1)
            (->> box-pairs
                 (map set)
                 (every? #(set/subset? % (first circuits)))))))

(defn -main []
  (let [closest-pairs (sorted-closest-pairs)]
    (println "Part 1 Solution:" (->> closest-pairs
                                     (build-circuits part-1-continue?)
                                     :circuits
                                     (map count)
                                     (sort >)
                                     (take 3)
                                     (reduce *)))
    (println "Part 2 Solution:" (->> closest-pairs
                                     (build-circuits part-2-continue?)
                                     :last
                                     (map first) ; x coordinate
                                     (reduce *)))))
