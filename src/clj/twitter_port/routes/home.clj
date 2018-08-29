(ns twitter-port.routes.home
  (:require [twitter-port.layout :as layout]
            [twitter-port.db.core :as db]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [ring.util.response :refer [redirect]]
            [selmer.parser :refer [render-file]]
            [clojure.tools.logging :as log]
            [twitter-port.twitter-functions :as twitter-functions]
            ))


(defn log-query [user-id]
  (db/save-history!
    {:query_string user-id :query_time (java.util.Date.)}))


(defn sorted-tweets [user-id]
  (println "in sorted-tweets")
  (let [statuses (take 10 (reverse(sort-by :favorite_count ((twitter-functions/retrieve-tweets user-id) :body))))]
    statuses))


(defn process-line [status]
  (into[] (re-seq #"#\w+" (:text status))))


(defn extract-hashtags [statuses]
  (println "inside hashtags")
  (let [hashtags (into[] (doall (distinct (flatten (mapv process-line statuses)))))]
    hashtags))


(defn query-page [user-id]
  ; DEBUG
  (log/info "Hello John!!!!!!!!!!!!!!!!!!!!!!!!!")

  (log-query user-id)

  ;retrieve tweets and build the response
  (let [statuses (sorted-tweets user-id)]
    ;extract hashtags from tweets
    (let [hashtags (extract-hashtags statuses)]
      ;render and return the html
      (println hashtags)
      (render-file "charts.html"
        {:user_name user-id :statuses statuses
          :hashtags hashtags :popular_topics []}))))


(defn history-page []
  (render-file "log.html" {:history (db/get-history)}))


(defn home-page []
  (render-file "charts_empty.html" {:message "Please use the search bar to find the target twitter handle."}))


(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/query/:user_id" [user_id] (query-page user_id))
  (GET "/history" [] (history-page))
  )

