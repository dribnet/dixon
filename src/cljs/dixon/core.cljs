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
  [:require [c2.scale :as scale]
            [goog.dom :as dom]])

(defn docycle [grid w h onfunc]
  (bind! "#board" 
    [:svg {:style (str "display: block;"
                       "margin: auto;"
                       "height:" 700 ";"
                       "width:" 700 ";")}
    (unify (for [x (range w) y (range h) :when (not= nil (onfunc grid y x))]
              [x y])
        (fn [[x y]]
          (let [css-class (if (onfunc grid y x) "box-on" "box-off")]
            [:rect {:x (* 20 x) :y (* 20 y) :height 24 :width 24
                  :class css-class :rx 3}] )))
                                  ]))

(def grid (populate-grid (empty-grid 10 10) #{[2 0] [2 1] [2 2] [1 2] [0 1]}))

(def grid-atom (atom grid))

(defn grid-is-on [grid y x]
  (nth (nth grid y) x))

;(docycle grid 35 35 grid-is-on)

(defn nextloop []
  (swap! grid-atom grid-step)
  (docycle @grid-atom 10 10 grid-is-on))

(defn animation-loop []
  (.requestAnimationFrame (dom/getWindow) animation-loop)
  (nextloop))

(animation-loop)
;(js/setInterval #(nextloop) (/ 1000 30))
