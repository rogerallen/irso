(defproject irso "1.0.0-SNAPSHOT"
  :description "Irrational Songs in Overtone"
  :dependencies [ [org.clojure/clojure "1.3.0"]
                  ;;[overtone "0.7.1"]
                  [overtone "0.8.0-SNAPSHOT"]
                  [quil "1.5.0"]
                  [oversampler "0.2.0-SNAPSHOT"]
                  ]
  :plugins [ [lein-swank "1.4.4"] ]
  :main ^{:skip-aot true} irso.core
  ;; :repl-options {:timeout 60000} ;; timeout in ms
  )
