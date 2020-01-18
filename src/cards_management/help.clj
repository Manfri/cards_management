(ns cards-management.help)
(require '[datahike.api :as d])
;;"datahike:file:///tmp/api-test"
(def uri "datahike:mem://schemaless")
;; use the optional parameter to control the schema flexibility
(d/create-database uri :schema-on-read true)
(def conn (d/connect uri))
(defn make-new-pro
  "make new project"
  [?att-name va]
  (d/transact conn [{?att-name va}]))

(defn create-new-pro
  "make new project; arg-list:  _project-name _org-name _revenue _start-date"
  [ _project-name _org-name _revenue _start-date]
  (d/transact conn [{:project-name _project-name
                     :org-name _org-name
                     :revenue _revenue
                     :start-date _start-date}]))
