(ns dixon.grid)

;;; here are the functions for doing GOL

(defn grid-window
  "Returns a lazy sequence of 3-item windows centered
   around each item of coll, padded as necessary with
   pad or nil."
  ([coll] (grid-window nil coll))
  ([pad coll] (partition 3 1 (concat [pad] coll [pad]))))

(defn cell-block
  "creates sequences of 3x3 windows from a triple of 3 sequences."
  [[left mid right]]
  (grid-window (map vector left mid right)))

(defn gol-liveness
  "Returns the liveness (nil on :on) of the center cell for
   the next step"
  [block]
  (let [[_ [_ center _] _] block]
    (case (- (count (filter #{:on} (apply concat block)))
                   (if (= :on center) 1 0))
      2 center
      3 :on
      nil)))

(defn step-row
  "Yileds the next state of the embedded center row."
  [rows-triple]
  (vec (map gol-liveness (cell-block rows-triple))))

(defn grid-step
  "Yields the next state of the grid."
  [grid]
  (vec (map step-row (grid-window (repeat nil) grid))))

(defn empty-grid
  "Creates an empty grid with specificied width and height"
  [w h] (vec (repeat w (vec (repeat h nil)))))

(defn populate-grid
  "Turns :on each of the cells specified as [y, x] coordinates."
  [grid living-cells]
  (reduce (fn [grid coordinates]
            (assoc-in grid coordinates :on))
          grid
          living-cells))

(def glider (populate-grid (empty-grid 6 6) #{[2 0] [2 1] [2 2] [1 2] [0 1]}))

