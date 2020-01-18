(ns cards-management.core)

(require '[datahike.api :as d])

;; use the filesystem as storage medium
;;(def uri "datahike:file:///tmp/example")
;;(def uri "datahike:file:///C:/temp")
(def uri "datahike:mem://mem-example")
;; create a database at this place, by default configuration we have a strict
;; schema and temporal index
(d/create-database uri)

(def conn (d/connect uri))

;; the first transaction will be the schema we are using
(d/transact conn [{:db/ident :name
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one }
                  {:db/ident :age
                   :db/valueType :db.type/long
                   :db/cardinality :db.cardinality/one }])

(d/transact conn [{:db/ident :telephone
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one }
                  ])



;; lets add some data and wait for the transaction
(d/transact conn [{:name  "Alice", :age   20 }
                  {:name  "Bob", :age   30 }
                  {:name  "Charlie", :age   40 }
                  {:age 15 }])

(d/transact conn [
                  {:name  "Bob", :age   30, :telephone "1281733" }
                  ])







;; search the data
(d/q '[:find ?e ?x
       :where
       [?e :age 30]
       [?e :name ?x]]
     @conn)

(d/q '[:find ?x (count ?e)
       :where
       [?e :name ?x]
       ]
     @conn)

(d/q '[:find ?x
       :where
       [_ :name ?x]
       ]
     @conn)

(d/q '[:find ?x
       :in $
       :where
       [$_ :name ?x]
       ]
     @conn)

(d/q '[:find ?e ?n ?a
       :where
       [?e :name "Bob"]
       [?e :age ?a]
       [?e ?n ?a]]
     @conn)

(d/q '[:find ?e ?a
       :where
       [?e :name "Bob"]
       [?e :age ?a]]
     @conn)
;; => #{[3 "Alice" 20] [4 "Bob" 30] [5 "Charlie" 40]}

;; add new entity data using a hash map
(d/transact conn {:tx-data [{:db/id 3 :age 25}]})

;; if you want to work with queries like in
;; https://grishaev.me/en/datomic-query/,
;; you may use a hashmap
(d/q {:query '{:find [?e ?n ?a ]
               :where [[?e :name ?n]
                       [?e :age ?a]]}
      :args [@conn]})
;; => #{[5 "Charlie" 40] [4 "Bob" 30] [3 "Alice" 25]}

;; query the history of the data
(d/q '[:find ?a
       :where
       [?e :name "Alice"]
       [?e :age ?a]]
     (d/history @conn))
;; => #{[20] [25]}

;; you might need to release the connection, e.g. for leveldb
(d/release conn)

;; clean up the database if it is not need any more
(d/delete-database uri)
