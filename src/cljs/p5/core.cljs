(ns p5.core
  (:refer-clojure :exclude [pop])
  (:require [camel-snake-kebab.core :as csk]))

(def p (atom nil))

(defn- set-instance! [p_] (swap! p (fn [] p_)))
(defn instance [] @p)

(defn- make-real-name [name]
  (let [name-str (csk/->camelCaseString name)]
    name-str))

(defn- do-init [p_ cbs]
  (set-instance! p_)
  (doseq [[cb-name cb] cbs]
    (let [real-name (csk/->camelCaseString cb-name)]
      (aset p_ real-name cb))))

(defn init! [cbs]
  (new js/p5 (fn [p_] (do-init p_ cbs))))

(defn window-width [] (. @p -windowWidth))
(defn window-height [] (. @p -windowHeight))
(defn width [] (. @p -width))
(defn height [] (. @p -height))
(defn mouse-x [] (. @p -mouseX))
(defn mouse-y [] (. @p -mouseY))
(defn mouse-is-pressed [] (. @p -mouseIsPressed))
(defn frame-count [] (. @p -frameCount))

(defn sin [x] (. @p sin x))
(defn cos [x] (. @p cos x))
(defn tan [x] (. @p tan x))

(defn create-canvas
  ([width height] (. @p createCanvas width height))
  ([width height renderer] (. @p createCanvas width height renderer)))

(defn load-font [filename]
  (. @p loadFont filename))

(defn text-font [font]
  (. @p textFont font))

(defn text-size [size]
  (. @p textSize size))

(defn resize-canvas [width height]
  (. @p resizeCanvas width height))

(defn rect-mode [mode]
  (. @p rectMode mode))

(defn create-vector
  ([] (. @p createVector))
  ([x] (. @p createVector x))
  ([x y] (. @p createVector x y))
  ([x y z] (. @p createVector x y z)))

(defn background
  ([c] (. @p background c))
  ([r g b] (. @p background r g b))
  ([r g b a] (. @p background r g b a)))

(defn ellipse [x y rx ry]
  (. @p ellipse x y rx ry))

(defn line [x1 y1 x2 y2]
  (. @p line x1 y1 x2 y2))

(defn rotate [a]
  (. @p rotate a))

(defn fill
  ([c] (. @p fill c))
  ([r g b] (. @p fill r g b))
  ([r g b a] (. @p fill r g b a)))

(defn no-fill []
  (. @p noFill))

(defn stroke [s]
  (. @p stroke s))

(defn no-stroke []
  (. @p noStroke))

(defn stroke-weight [w]
  (. @p strokeWeight w))

(defn push []
  (. @p push))

(defn pop []
  (. @p pop))

(defn translate [x y]
  (. @p translate x y))
