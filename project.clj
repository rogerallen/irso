(defproject irso "1.0.0-SNAPSHOT"
  :description "Irrational Songs in Overtone"
  :repositories {"local" ~(str (.toURI (java.io.File. "maven_repository")))}
  :dependencies [ [org.clojure/clojure "1.3.0"]
                  [overtone "0.7.0"]
                  [quil "1.5.0"]
                  ]
  :plugins [ [lein-swank "1.4.4"] ]
  :main irso.core
  ;; :repl-options {:timeout 60000} ;; timeout in ms
  )
