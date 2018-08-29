(ns twitter-port.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [twitter-port.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[twitter-port started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[twitter-port has shut down successfully]=-"))
   :middleware wrap-dev})
