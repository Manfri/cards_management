(ns cards-management.help)
(require '[datahike.api :as d])
;;"datahike:file:///tmp/api-test"
(def uri "datahike:mem://schemaless")
;; use the optional parameter to control the schema flexibility
(d/create-database uri :schema-on-read true)
(def conn (d/connect uri))
