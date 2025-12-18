(ns advent-25-clj.core
  (:gen-class))
(require '[clojure.java.io :as io])

(defn parse-reading [reading]
  (let [sign (if (= (first reading) \L) -1 1)]
;    (print reading "")
    (* sign (parse-long (subs reading 1)))))

(defn rotate-dial [new-zeros-fn {curr-count :count, zeros :zeros} value]
  (let [next-count (mod (+ curr-count value) 100)
        new-zeros (new-zeros-fn next-count curr-count value)]
;    (println "next" next-count "new zeros" new-zeros)
    {:count next-count :zeros (+ zeros new-zeros)}))

(defn solve [result-str new-zeros-fn]
  (with-open [reader (io/reader "input_day1.txt")]
    (println result-str
             (reduce
              (partial rotate-dial new-zeros-fn)
              {:count 50 :zeros 0}
              (map parse-reading (line-seq reader))))))

(defn -main
  []
  (solve "Part 1 Result:" (fn [next-count & _] (if (zero? next-count) 1 0)))
  (solve "Part 2 Result:" (fn [next-count curr-count value]
                            (let [full-turns (int (/ (abs value) 100))
                                  not-zero-counts? (not (or (zero? next-count) (zero? curr-count)))
                                  clicked-right? (and (pos? value) (< next-count curr-count) not-zero-counts?)
                                  clicked-left? (and (neg? value) (> next-count curr-count) not-zero-counts?)
                                  count-zero? (and (zero? next-count) (pos? (mod value 100)))]
                              (+ full-turns (count (filter true? [count-zero? clicked-right? clicked-left?])))))))
