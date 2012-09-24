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

(def grid-width 40)
(def grid-height 30)

(def display-atom (atom {}))

(defn docycle [grid w h onfunc]
  (bind! "#board" 
    [:svg {:style (str "display: block;"
                       "margin: auto;"
                       "height:" 700 ";"
                       "width:" 700 ";")}
    (unify @display-atom
       (fn [{:keys [x y state]}]
          (let [css-class (if state "box-on" "box-off")]
            [:rect {:x (* 20 x) :y (* 20 y) :height 24 :width 24
                  :class css-class :rx 3}] )))
                                  ]))

; (defn update-display-atom []
;   (let [pairs (for [x (range 10) y (range 10)] [x y])]

(def grid (populate-grid (empty-grid grid-width grid-height) #{[2 0] [2 1] [2 2] [1 2] [0 1]}))

(def grid-atom (atom grid))

(dotimes [_ 1000]
  (let [x (rand-int grid-width)
        y (rand-int grid-height)]
    (reset! grid-atom (populate-grid @grid-atom #{[x y]}))))

(defn grid-is-on [grid y x]
  (nth (nth grid y) x))

; gross
; (defn update-display-atom []
;   (loop [board [] x 0]
;     (loop [boardy board y 0]
;       (if (= y 10)
;         boardy
;         (recur (conj boardy {:x x :y y :state (grid-is-on @grid-atom x y)}) (inc y))))
;     (if (= x 10)
;       board
;       (recur board (inc x)))))

; map grid into a list of maps that can be fed to unify
(defn update-display-atom []
  (for [x (range grid-width) y (range grid-height)] {:x x :y y :state (grid-is-on @grid-atom x y)}))

;(docycle grid 35 35 grid-is-on)

(docycle grid-atom grid-width grid-height grid-is-on)

(defn nextloop []
  (swap! grid-atom grid-step)
  (reset! display-atom (update-display-atom)))

(defn animation-loop []
  (.requestAnimationFrame (dom/getWindow) animation-loop)
  ;(.log js/console (str "Looping..." (grid-is-on @grid-atom 9 9)))
  (nextloop))

(animation-loop)
;(js/setInterval #(nextloop) (/ 1000 30))
