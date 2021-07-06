(ns p5.utils
  (:require [camel-snake-kebab.core :as csk])
  (:import [goog.async Debouncer]))

(defn debounce [f interval]
  (let [dbnc (Debouncer. f interval)]
    ;; We use apply here to support functions of various arities
    (fn [& args]
      (.apply (.-fire dbnc) dbnc (to-array args)))))

(defn jsify-keys
  "Recursively transforms all map keys from strings to camelcase keywords."
  [m]
  (let [f (fn [[k v]] (if (or (keyword? k) (string? k)) [(csk/->camelCaseKeyword k) v] [k v]))]
    ;; only apply to maps
    (clojure.walk/postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))
