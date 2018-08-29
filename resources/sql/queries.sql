-- :name save-history! :! :n
-- :doc creates a new log record
INSERT INTO history
(query_string, query_time)
VALUES (:query_string, :query_time)

-- :name get-history :? :*
-- :doc retrieves a log record given the search_query
SELECT * FROM history