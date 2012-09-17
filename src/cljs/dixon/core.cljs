(ns dixon.core
  (:use-macros [c2.util :only [p pp bind!]])
  (:use [c2.core :only [unify style]])
  (:require [c2.scale :as scale]))

(defn docycle [counter]
  (bind! "#board" 
    [:svg {:style (str "display: block;"
                            "margin: auto;"
                            "height:" 700 ";"
                            "width:" 700 ";")}
    (unify (for [x (range 35) y (range 35) :when (= (mod x 5) (mod counter 5))]
              [x y])
        (fn [[x y]]
          [:rect.box {:x (* 20 x) :y (* 20 y) :height 24 :width 24
                  :fill "#05182F" :rx 3 :style {:opacity 1}}]))
                                  ]))

(def count-atom (atom 0))

(defn nextloop []
  (swap! count-atom inc)
  (docycle @count-atom))

(js/setInterval #(nextloop) (/ 1000 30))
