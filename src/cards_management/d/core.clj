
(ns cards-management.d.core)

(require '[datahike.api :as d])

;; use the filesystem as storage medium
;;(def uri "datahike:file:///tmp/example")
;;(def uri "datahike:file:///C:/temp")
(def uri "datahike:mem://mem-example")
(def uri2 "datahike:mem://mem-example2")
;; create a database at this place, by default configuration we have a strict
;; schema and temporal index
(d/create-database uri)
(def conn (d/connect uri))

(d/create-database uri2)
(def conn2 (d/connect uri2))

(d/transact conn [{:db/id #db/id[:db.part/db]
                   :db/ident :item/id
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/unique :db.unique/identity
                   }

                  {:db/id #db/id[:db.part/db]
                   :db/ident :item/description
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   }
                  {:db/id #db/id[:db.part/db]
                   :db/ident :item/count
                   :db/valueType :db.type/long
                   :db/index true
                   :db/cardinality :db.cardinality/one
                  }
                  {:db/id #db/id[:db.part/db]
                   :db/ident :tx/error
                   :db/valueType :db.type/boolean
                   :db/index true
                   :db/cardinality :db.cardinality/one
                   }
                  {:db/id #db/id [:db.part/tx]
                   :db/txInstant #inst "2012"}
                 ])

(d/release conn)
(d/transact conn   [{:db/id [:item/id "DLC-1234"]
                     :item/count 500}
                    ]
           )

(d/transact conn   [{:item/id "DLC-1234"
                     :item/count 400}
                   ]
            )
(d/entity db [:item/id "DLC-1234"])
(def db (d/db conn))
(def as-of-eoy-2013 (d/as-of db #inst "2014-01-01"))
(def since-2014 (d/since db #inst "2014-01-01"))
(def history (d/history db))

(def db (d/db conn))


(d/q '[:find ?e  ?a ?b
       :where
       [?e :item/id "DLC-1234" ?a ?b]

       ]
     @conn)


(d/q '[:find ?e  ?a ?b ?c
       :where
       [?e  ?a ?b ?c]
       ]
     @conn)
(require
  '[clojure.pprint :as pp]
  )






