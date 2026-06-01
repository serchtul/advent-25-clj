(ns advent-25-clj.day-4
  (:require
   [clojure.java.io :as io]))

(defn get-neighbors [r c]
  (for [rdelta [-1 0 1]
        cdelta [-1 0 1]
        :when (not= 0 rdelta cdelta)]
    [(+ r rdelta) (+ c cdelta)]))

(defn can-remove? [board-vec r c item]
  (if (= item \@) ; Only count rolls' neighbors
    (->> (get-neighbors r c)
         (map #(get-in board-vec %))
         (filter #(= \@ %))
         count
         (> 4))
    false))

(defn mapv-2d-indexed [f board]
  (->> board
       (map-indexed (fn [r row] (map-indexed (fn [c item] (f r c item))
                                             row)))
       (mapv vec)))

(defn solve
  ([max-steps]
   (with-open [input (io/reader "input_day4.txt")]
     (loop [bound   (- max-steps 1)
            removed 0
            board   (->> input line-seq (mapv vec))]
       (let [removable?      (mapv-2d-indexed (partial can-remove? board)
                                              board)
             new-removed     (->> removable? flatten (filter true?) count (+ removed))
             new-board       (mapv-2d-indexed (fn [r c item]
                                                (if (get-in removable? [r c]) \. item))
                                              board)]
         #_(println "Removed" new-removed)
         (if (or (= removed new-removed)
                 (zero? bound))
           new-removed
           (recur (- bound 1)
                  new-removed
                  new-board))))))
  ; Indefinitely remove rolls until stable
  ([] (solve 0)))

(defn -main []
  (println "Part 1:" (solve 1))
  (println "Part 2:" (solve)))
