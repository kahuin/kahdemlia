(ns kahdemlia.encoding
  #?(:clj
     (:import java.util.Base64))
  #?(:cljs
     (:require
       [goog.crypt.base64 :as b64]
       [clojure.string :as string])))

(defn- str->b64
  [s]
  #?(:clj  (map #(mod % 256) (.decode (Base64/getDecoder) s))
     :cljs (b64/decodeStringToByteArray s)))

(defn- b64->str
  [arr]
  #?(:clj  (.encodeToString (Base64/getEncoder) (byte-array arr))
     :cljs (b64/encodeByteArray (clj->js arr))))

(defn octets->base64
  [arr]
  (-> arr
      (b64->str)
      (clojure.string/replace #"/" "_")
      (clojure.string/replace #"\+" "-")
      (clojure.string/replace #"=" "")))

(defn base64->octets
  [s]
  (-> s
      (clojure.string/replace #"[_]" "/")
      (clojure.string/replace #"[-]" "+")
      (str->b64)
      (seq)))

(defn ->octets
  [s-or-arr]
  (if (string? s-or-arr) (base64->octets s-or-arr) s-or-arr))

(defn ->base64
  [s-or-arr]
  (if (string? s-or-arr) s-or-arr (octets->base64 s-or-arr)))
