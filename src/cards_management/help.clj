(ns cards-management.help)
(require '[datahike.api :as d])
;;"datahike:file:///tmp/api-test"
(def uri "datahike:mem://schemaless")
;; use the optional parameter to control the schema flexibility
(d/create-database uri :schema-on-read true)
(def conn (d/connect uri))

(defn create-new-pro
  "make new project; arg-list:  _project-name _org-name _revenue _start-date"
  [ _project-name _org-name _revenue _start-date]
  (d/transact conn [{:project-name _project-name
                     :org-name _org-name
                     :revenue _revenue
                     :start-date _start-date}])
  )

(defn add-attribute
  [known-attribute known-value new-attribute new-value]
  (let  [ ent (d/q '[:find ?e .
                     :in $ [?attr-name ?value]
                     :where
                     [?e ?attr-name ?value]
                     ] @conn [known-attribute known-value])]
    (d/transact conn [{:db/id ent new-attribute new-value}])
    )
  )
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;BEGIN TEST
(defn make-new-pro
  "make new project"
  [?att-name va]
  (d/transact conn [{?att-name va}]))

(d/q '[:find ?e ?value
       :in $ [?attr-name ?value]
       :where
       [?e ?attr-name ?value]
       ] @conn [:project-name "ALC"])

(def query2 '[:find ?e ?value
              :in $ ?value
              :where
              [?e :project-name ?value]
              ])
(d/q  query2 @conn "ALC")

(d/q '[:find ?e ?v ?a
       :where
       [?e ?v ?a]]
        @conn )


(defn add-attribute2
  [known-attr known-value new-attribute new-value ]
  (d/transact conn [{:db/id [known-attr known-value]
                     new-attribute new-value}])
  )
;;;;;;;;;;;;;;;;;;;;;;;;;;;;; END TEST