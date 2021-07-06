(ns ^:figwheel-hooks sketch.core
  (:require [vecvec.v2d :as v2d]
            [goog.dom :as dom]
            [p5.core :as p5]
            [p5.vector]
            [p5.font]
            [ruin.macros :as macros]))

(enable-console-print!)

;;;; STATE

(def P (atom nil))
(def size 500)
(def text "rinari")
(def font-size 256)


(defn dots [] (->> (range 50)
                   (map #(hash-map :position (v2d/abs (v2d/random size))
                                   :color [(rand-int 255) (rand-int 255) (rand-int 255)]))))


(defn update-state [state]
  state)

(defonce *state (atom {:t 0}))


;;;; P5


(defn preload []
  (swap! *state
         (fn [state]
           (assoc state
                  :font (p5/load-font "../fonts/ChunkFive-Regular.otf")))))

(defn window-resized []
  (p5/resize-canvas (p5/window-width) (p5/window-height)))

(defn setup []
  (let [cnv (p5/create-canvas (p5/window-width) (p5/window-width) "WEBGL")]
    (. cnv (style "display" "block")))
  (p5/rect-mode "CENTER")
  (p5/text-font (get @*state :font))
  (p5/text-size font-size)

  (swap! *state
         (fn [state]
           (assoc state
                  :pts (p5.font/text-to-points
                        (get state :font)
                        text 0 0 font-size
                        {:sample-factor 0.05 ; increase for more points
                         :simplify-threshold 0.0 ; increase to remove collinear points
                         }))))
  (window-resized))

(defn draw []
  (p5/background 0)

  (p5/stroke 255)
  (p5/stroke-weight 2)
  (p5/no-fill)

  (let [f (p5/frame-count)
        d (+ (* 2 (p5/cos (/ f 30))) (+ 10 (p5/sin (/ f 20))))
        angle (+ (* 0.01 f) (- (p5/cos (/ f 50))))]
    (p5/push)
    (p5/translate 60 (/ (* (p5/height) 5) 8))
    (macros/doseq-indexed i [pt (get @*state :pts)]
      (p5/push)
      (p5/translate (get pt :x) (get pt :y))
      (p5/rotate angle)
      (let [xoff (* 10 (p5/sin (/ (+ f (* i 5)) 30)))
            d (+ xoff d)
            weight (p5/max 2 (* 6 (p5/cos (* 0.2 (+ f (* (- i) 1.01)) 0.1))))]
        (p5/stroke-weight weight)
        (p5/line (+ xoff (- d)) (- d) d d))
      (p5/pop)
      )
    (p5/pop))
  (swap! *state update-state))

;;;; INIT

(p5/init! {:preload (fn [] (preload))
           :setup (fn [] (setup))
           :window-resized (fn [] (window-resized))
           :draw (fn [] (draw))})

;;;; FIGWHEEL

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! *state update-in [:__figwheel_counter] inc)
)

(defn ^:before-load before-reload []
  (doseq [elem (dom/getElementsByClass "p5Canvas")]
    (dom/removeNode elem))
  (window-resized))
