(ns dixon.core
  (:use-macros [c2.util :only [p pp bind!]])
  (:use [c2.core :only [unify style]])
  (:require [c2.scale :as scale]))

#_(bind! "#board" 
  [:svg#main {:style (str "display: block;"
                          "margin: auto;"
                          "height:" 700 ";"
                          "width:" 700 ";")}
    [:rect.box {:x 500 :y 400 :height 24 :width 24
                :fill "#05182F" :rx 3 :style {:opacity 1}}]
    [:rect.box {:x 520 :y 420 :height 24 :width 24
                :fill "#05182F" :rx 3 :style {:opacity 1}}]
                                ])

(bind! "#board" 
  [:svg#main {:style (str "display: block;"
                          "margin: auto;"
                          "height:" 700 ";"
                          "width:" 700 ";")}
  (unify (for [x (range 35) y (range 35) :when (even? (+ x y))] [x y])
      (fn [[x y]]
        [:rect.box {:x (* 20 x) :y (* 20 y) :height 24 :width 24
                :fill "#05182F" :rx 3 :style {:opacity 1}}]))
                                ])
