; next steps:
;   simulation console - play, pause, stop, etc.
;   simlation parameters - number of cells, colorbrewer colors, etc.
;   smarter layout
;   game of life rules
;
; next... pause, play. mouse to click.
;  crossover + maybe run on backend

(ns dixon.core
  [:use-macros [c2.util :only [p pp bind!]]]
  [:use [c2.core :only [unify style]]
        [dixon.grid :only [populate-grid empty-grid grid-step]]
        [dixon.ui :only [uistate]]]
  [:require [c2.scale :as scale]
            [goog.dom :as dom]])

; [dixon.ui :as ui]

(def grid-width 40)
(def grid-height 30)

(def display-atom (atom {}))
(def sim-state (atom {}))

(defn docycle [grid w h onfunc]
  (bind! "#board" 
    ; TODO: why doesn't style work here?
    [:svg#board {:width 820 :height 620}

    (unify @display-atom
       (fn [{:keys [x y state]}]
          ;(js/alert "unify this display")
          (let [css-class (if state "box-on" "box-off")]
            [:rect {:x (* 20 x) :y (* 20 y) :height 24 :width 24
                  :class css-class :rx 3}] )))
                                  ]))

(bind! "#stepnum"
  [:div#stepnum
    (unify @sim-state 
      (fn [[label val]]
;        (js/alert "unify this step")
        [:div (str label " = " val)]))])
     
; (defn update-display-atom []
;   (let [pairs (for [x (range 10) y (range 10)] [x y])]


(def grid-atom (atom [[]]))

(defn init-sim []
  (reset! grid-atom (populate-grid (empty-grid grid-width grid-height)
      #{[2 0] [2 1] [2 2] [1 2] [0 1]}))
  (swap! sim-state assoc :step-num 0)
  (dotimes [_ 1000]
    (let [x (rand-int grid-width)
          y (rand-int grid-height)]
      (reset! grid-atom (populate-grid @grid-atom #{[x y]})))))

(defn grid-is-on [grid y x]
  (nth (nth @grid-atom y) x))

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

(docycle grid-atom grid-width grid-height grid-is-on)

(defn nextloop []
  (if ((:play-state @uistate) #{:stop-awaiting})
    (do
      (swap! sim-state dissoc :initialized)
      (swap! uistate assoc :play-state :stop-complete)))
  (if ((:play-state @uistate) #{:play :step-awaiting})
    (do
      ; see if we need to init sim
      (if (not (:initialized @sim-state))
        (do
          (init-sim)
          (swap! sim-state assoc :initialized true)))
      (if (= (:play-state @uistate) :step-awaiting)
        (swap! uistate assoc :play-state :step-complete))
      (swap! grid-atom grid-step)
      (swap! sim-state update-in [:step-num] inc)
      (reset! display-atom (update-display-atom)))))

(defn animation-loop []
  (.requestAnimationFrame (dom/getWindow) animation-loop)
  ;(.log js/console (str "Looping..." (grid-is-on @grid-atom 9 9)))
  (nextloop))

(animation-loop)
;(js/setInterval #(nextloop) (/ 1000 30))
