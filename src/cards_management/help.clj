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
  (d/transact conn [{:project-name _project-name {:db/unique}
                     :org-name _org-name
                     :revenue _revenue
                     :start-date _start-date}]))

;; :attr "value" -> :new-attr new value
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;BEGIN TEST

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
;;;;;;;;;;;;;;;;;;;;;;;;;;;;; END TEST

(defn add-attribute
  [known-attribute known-value new-attribute new-value]
  (def ent (d/q '[:find ?e
                  :in $ [?attr-name ?value]
                  :where
                  [?e ?attr-name ?value]
                  ] @conn [known-attribute known-value]))
  (println (get {[ent]} 0))
  ;;(d/transact conn [{:db/id ent
                    ;; new-attribute new-value}])
  )
(add-attribute :project-name "ALC" :leader "Rahul")


(defn add-attribute2
  [known-attr known-value new-attribute new-value ]
  (d/transact conn [{:db/id [known-attr known-value]
                     new-attribute new-value}])
  )
