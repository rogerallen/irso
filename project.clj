(defproject irso "1.0.0-SNAPSHOT"
  :description "Irrational Songs in Overtone"
  :repositories {"local" ~(str (.toURI (java.io.File. "maven_repository")))}
  :dependencies [ [org.clojure/clojure "1.3.0"]
                  [overtone "0.7.0-SNAPSHOT"]
                  [quil "1.5.0"]
                  ]
  :plugins [ [lein-swank "1.4.4"] ]
  :main irso.core
  :run-aliases {:eso         irso.core/main-play-eso
                :phiso       irso.core/main-play-phiso
                :piso        irso.core/main-play-piso
                :sqrt2so     irso.core/main-play-sqrt2so
                :sqrt3so     irso.core/main-play-sqrt3so
                :tauso       irso.core/main-play-tauso
                :rec-eso     irso.core/main-record-eso
                :rec-phiso   irso.core/main-record-phiso
                :rec-piso    irso.core/main-record-piso
                :rec-sqrt2so irso.core/main-record-sqrt2so
                :rec-sqrt3so irso.core/main-record-sqrt3so
                :rec-tauso   irso.core/main-record-tauso
                }
  )
