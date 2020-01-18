(ns cards-management.d.func-schema-on-read-start)
(require '[datahike.api :as d])
(require '[cards-management.help :refer :all])

;; now you can add any arbitrary data
(def lconn cards-management.help/conn)
(d/transact lconn [{:any "Data"}])

(def my (d/q '[:find ?e ?v
               :where
               [?e  :any "Data" ?v]
               ]
             @lconn))
(println my)
(println "haha")
