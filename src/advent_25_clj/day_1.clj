(ns advent-25-clj.day-1
  (:gen-class))
(require '[clojure.java.io :as io])

(defn parse-line [reading]
  (let [sign (if (= (first reading) \L) -1 1)]
    #_(print reading "")
    (-> reading
        (subs 1)
        parse-long
        (* sign))))

(defn rotate-dial
  [get-new-zeros-fn
   {:keys [count, zeros]}
   value]
  (let [next-count (mod (+ count value) 100)
        new-zeros (get-new-zeros-fn next-count count value)]
    #_(println "next" next-count "new zeros" new-zeros)
    {:count next-count :zeros (+ zeros new-zeros)}))

(defn solve [result-str new-zeros-fn]
  (with-open [reader (io/reader "input_day1.txt")]
    (println result-str
             (->> reader
                  line-seq
                  (map parse-line)
                  (reduce (partial rotate-dial new-zeros-fn) {:count 50 :zeros 0})))))

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
