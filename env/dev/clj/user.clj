(ns user
  (:require [twitter-port.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [twitter-port.core :refer [start-app]]
            [twitter-port.db.core]
            [conman.core :as conman]
            [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'twitter-port.core/repl-server))

(defn stop []
  (mount/stop-except #'twitter-port.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn restart-db []
  (mount/stop #'twitter-port.db.core/*db*)
  (mount/start #'twitter-port.db.core/*db*)
  (binding [*ns* 'twitter-port.db.core]
    (conman/bind-connection twitter-port.db.core/*db* "sql/queries.sql")))

(defn reset-db []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


