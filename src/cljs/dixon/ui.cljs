(ns dixon.ui)

;;; here are the functions for the ui

(def uistate (atom {:play-state :step-awaiting}))

(defn ^:export step_button []
  (swap! uistate assoc :play-state :step-awaiting))

(defn ^:export play_button []
  (swap! uistate assoc :play-state :play))

(defn ^:export pause_button []
  (swap! uistate assoc :play-state :pause))

(defn ^:export stop_button []
  (swap! uistate assoc :play-state :stop-awaiting))
