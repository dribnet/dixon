(defproject dixon "0.0.1-SNAPSHOT"
  :description "dixon: dots on a screen"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/clojurescript "0.0-1450"]
                 [com.keminglabs/c2 "0.2.0"]
                ]
  :min-lein-version "2.0.0"
  :source-paths ["src/clj"]
  :url "http://example.com/FIXME"

  :plugins [[lein-cljsbuild "0.2.7"]]

  :cljsbuild {
    ; Each entry in the :crossovers vector describes a Clojure namespace
    ; that is meant to be used with the ClojureScript code as well.
    ; The files that make up this namespace will be automatically copied
    ; into the ClojureScript source path whenever they are modified.
;    :crossovers [dixon.core]
    :builds [{
      :source-path "src/cljs"
      :compiler {
        :output-to "web/out/dixon.js"
        :optimizations :whitespace
        :pretty-print true }}]})
