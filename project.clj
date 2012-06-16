(defproject irso "1.0.0-SNAPSHOT"
  :description "Irrational Songs in Overtone"
  :repositories {"local" ~(str (.toURI (java.io.File. "maven_repository")))}
  :dependencies [ [org.clojure/clojure "1.3.0"]
                  [overtone "0.7.0-LOCALSNAPSHOT"]
                  ]
  :plugins [ [lein-swank "1.4.4"] ]
  :main irso.core
  :run-aliases {:eso irso.core/main-play-eso}
  )
