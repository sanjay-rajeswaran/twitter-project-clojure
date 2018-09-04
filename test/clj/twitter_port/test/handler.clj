(ns twitter-port.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [twitter-port.handler :refer :all]
            [mount.core :as mount]
            [twitter-port.db.core :as db]))

(use-fixtures
  :once
  (fn [f]
    (mount/start #'twitter-port.config/env
                 #'twitter-port.handler/app)
    (f)))

(deftest test-app
  (testing "home route"
    (let [response (app (request :get "/query"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response (app (request :get "/invalids"))]
      (is (= 404 (:status response))))))

  (testing "query-fail route"
    (let [response (app (request :get "/query/twitter_port"))]
      (is (= 200 (:status response)))))

  (testing "query route"
    (let [response (app (request :get "/query/nerdysense/"))]
      (is (= 200 (:status response)))))

; 166d964ccab418464279b6f2c58d12e9e63cda38