(ns advent-25-clj.day-7
  (:require
   [clojure.string :as s]))

(defn down  [[r c]] [(inc r) c])
(defn up    [[r c]] [(dec r) c])
(defn right [[r c]] [r (inc c)])
(defn left  [[r c]] [r (dec c)])
(def right-down (comp right down))
(def left-down (comp left down))

(defn has-splitter? [coord diagram dir]
  (->> coord dir (get-in diagram) (= \^)))

(defn solve [diagram]
  (let [[row column]     [(-> diagram count dec) (-> diagram first count dec)]
        visited   (atom {})
        visit     (fn find-path [coord last-split]
                    (let [splits?     (partial has-splitter? coord diagram)
                          down-split? (splits? down)
                          next-split  (if down-split? coord last-split)
                          visited?    (some? (get @visited coord))]
                      (when down-split?
                        (swap! visited update-in [coord :count] (fnil inc 0))
                        (when last-split
                          (swap! visited update-in [last-split :next] (fnil conj #{}) coord)))
                      (when (and (not visited?)
                                 (= \. (get-in diagram coord)))
                        (find-path (up coord) next-split)
                        (when (splits? right-down)
                          (find-path (right coord) next-split))
                        (when (splits? left-down)
                          (find-path (left coord) next-split)))))]

    (dotimes [delta (count diagram)]
      (visit [row (- column delta)] nil))
    (doseq [coord (->> @visited keys (sort-by (juxt first second)) reverse)
            :let  [next-coords (get-in @visited [coord :next])]
            :when (some? next-coords)
            next-coord next-coords]
      (swap! visited
             update-in [next-coord :count]
             + (get-in @visited [coord :count]) -1))
    @visited))

(defn part-1 [visited]
  (->> visited
       vals
       (filter :next)
       count
       inc))

(defn part-2 [visited]
  (->> visited
       vals
       (filter #(-> % :next not))
       (sort-by :count)
       last
       :count))

(defn -main []
  (let [visited-splitters (->> "input_day7.txt"
                               slurp
                               s/split-lines
                               (mapv vec)
                               solve)]
    (println "Part 1:" (part-1 visited-splitters))
    (println "Part 2:" (part-2 visited-splitters))))
