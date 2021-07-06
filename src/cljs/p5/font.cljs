(ns p5.font
  (:require [p5.utils]))

(defn text-to-points
  ([font txt x y font-size]
   (js->clj
    (. font textToPoints txt x y font-size)
    :keywordize-keys true))
  ([font txt x y font-size options]
   (js->clj
    (. font textToPoints txt x y font-size (clj->js (p5.utils/jsify-keys options)))
    :keywordize-keys true)))
