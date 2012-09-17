(defproject dixon "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/clojurescript "0.0-1450"]
                ]
  :plugins [[lein-cljsbuild "0.2.7"]]
  :source-paths ["src/clj"]
  :description "dixon: dots on a screen"
  :url "http://example.com/FIXME"
  :cljsbuild {
    :builds [{
      :source-path "src/cljs"
      :compiler {
        :output-to "web/out/dixon.js"
        :optimizations :whitespace
        :pretty-print true
      }
    }]
  }
)
