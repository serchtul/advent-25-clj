(ns advent-25-clj.core
  (:gen-class))

(defn -main [& args]
  (let [day (first args)
        ns-sym (some-> day
                       (->> (str "advent-25-clj."))
                       symbol)]
    (if ns-sym
      (try
        (require ns-sym)
        (if-let [main-fn (ns-resolve ns-sym '-main)]
          (main-fn)
          (println (str ns-sym " has no -main function")))
        (catch java.io.FileNotFoundException _
          (println (str "No solution found for '" day "'"))))
      (println "Usage: lein run <day> (e.g. lein run day-1)"))))
