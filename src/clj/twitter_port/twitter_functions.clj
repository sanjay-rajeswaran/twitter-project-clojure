(ns twitter-port.twitter-functions
  (:require
  	[twitter.oauth :refer [make-oauth-creds]]
  	[twitter.api.restful :as twt])
  (:import [twitter.callbacks.protocols SyncSingleCallback]))

  ; (:use [twitter.oauth]
  ;       [twitter.callbacks]
  ;       [twitter.callbacks.handlers]
  ;       [twitter.api.restful])
  ; (:import [twitter.callbacks.protocols SyncSingleCallback]))

(def my-creds (make-oauth-creds "YfJKQCqZ7CkLVD9IBuRI4aErn"
                                "4yvAZ9rskRLTlnmV2Yb61xzpeiGBwd6ibpOX0GKlmxZHTj41Nz"
                                "2932174502-HQ0YCvLQ82J1jmf22wSjoICak7WGFA9o1qhaABa"
                                "3qAax2aRlYI7V8HbrM2tdhFyOQNwbEdf5rw3NXJUKPlGi"))

(defn retrieve-tweets [user-id]
  (twt/statuses-user-timeline :oauth-creds my-creds :params {:screen-name user-id}))