(defproject advent-25-clj "0.1.0-SNAPSHOT"
  :description "Using Advent of Code's 2025 Edition as a means to learning Clojure"
  :url "https://github.com/serchtul/advent-25-clj"
  :license {:name "MIT"
            :url "https://opensource.org/license/mit"}
  :dependencies [[org.clojure/clojure "1.12.2"]
                 [clj-http "2.0.0"]]
  :main ^:skip-aot advent-25-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
