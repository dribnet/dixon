; next steps:
;   make bind powered by atom update
;   replace setInterval with requestAnimationFrame
;   simulation console - play, pause, stop, etc.
;   simlation parameters - number of cells, etc.
;   game of life rules
;   mouse hover...

; next... pause, play. mouse to click.
;  crossover + maybe run on backend

(ns dixon.core
  [:use-macros [c2.util :only [p pp bind!]]]
  [:use [c2.core :only [unify style]]
        [dixon.grid :only [populate-grid empty-grid grid-step]]]
  [:require [c2.scale :as scale]])

(defn docycle [grid w h onfunc]
  (bind! "#board" 
    [:svg {:style (str "display: block;"
                            "margin: auto;"
                            "height:" 700 ";"
                            "width:" 700 ";")}
    (unify (for [x (range w) y (range h) :when (onfunc grid y x)]
              [x y])
        (fn [[x y]]
          [:rect.box {:x (* 20 x) :y (* 20 y) :height 24 :width 24
                  :fill "#05182F" :rx 3 :style {:opacity 1}}]))
                                  ]))

(def grid (populate-grid (empty-grid 35 35) #{[2 0] [2 1] [2 2] [1 2] [0 1]}))

(def grid-atom (atom grid))

(defn grid-is-on [grid y x]
  (nth (nth grid y) x))

;(docycle grid 35 35 grid-is-on)

(defn nextloop []
  (swap! grid-atom grid-step)
  (docycle @grid-atom 35 35 grid-is-on))

(js/setInterval #(nextloop) (/ 1000 30))
