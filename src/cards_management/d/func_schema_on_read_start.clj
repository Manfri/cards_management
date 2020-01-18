(ns cards-management.d.func-schema-on-read-start)
(require '[datahike.api :as d])
(require '[cards-management.help :refer :all])

;; now you can add any arbitrary data
(def lconn cards-management.help/conn)
;;(make-new-pro :project-name "test")
(create-new-pro "ALC" "sber" 100  "12.01.2020")
(d/transact lconn [{:any "Data"}])
(d/transact lconn [{:func1 '(d/q '[:find ?e ?a ?v
                                   :where
                                   [?e ?a ?v]
                                   ]
                                 @lconn)}])
(def my (d/q '[:find  ?v
               :where
               [?e  :func1 ?v]
               ]
             @lconn))
(println my)
(eval my)


