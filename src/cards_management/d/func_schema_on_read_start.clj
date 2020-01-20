(ns cards-management.d.func-schema-on-read-start)
(require '[datahike.api :as d])
(require '[cards-management.help :refer :all])
(def lconn cards-management.help/conn)


(create-new-pro "ALC" "sber" 100  "12.01.2020")
(add-attribute :project-name "ALC" :leader "rahul" )
(add-attribute :project-name "ALC" :funct '(d/q '[:find ?e ?a ?v
                                                  :where
                                                  [?e ?a ?v]
                                                  ]
                                                @lconn) )
;;;;;;;;;;;;;;;;;;;;, BEGIN TEST
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
;(d/delete-database cards-management.help/uri)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; END TEST


