(ns dixon.core
  (:use-macros [c2.util :only [p pp bind!]])
  (:use [c2.core :only [unify style]])
  (:require [c2.scale :as scale]))

#_(bind! "#board" 
  (let [width 500 bar-height 20
        data {"A" 1, "B" 2, "C" 4, "D" 3}
        s (scale/linear :domain [0 (apply max (vals data))]
                        :range [0 width])]

    [:div
     (unify data (fn [[label val]]
                   [:div {:style {:height bar-height
                                  :width (s val)
                                  :background-color "gray"}}
                      [:span {:style {:color "white"}} label]]))]))


(bind! "#board" 
  [:svg#main {:style (str "display: block;"
                          "margin: auto;"
                          "height:" 700 ";"
                          "width:" 700 ";")}
    [:rect.box {:x 672 :y 464 :height 24 :width 24
                :fill "#05182F" :rx 3 :style {:opacity 0}}]])
 
