(ns irso.piso
  (:use [irso.irno])
  (:use [irso.irso]))

;; ======================================================================
;; the song
(defn ^:dynamic calc-piso [beat tonic type]
  (let [irno-seq pi-1000
        foo (println "pi song")
        seq1 (calc-seq tonic type 13 irno-seq)
        seq2 (calc-seq tonic type 17 (drop (count seq1) irno-seq))
        seq3 (calc-seq tonic type 19 (drop (+ (count seq1)
                                              (count seq2)) irno-seq))
        seq1r (calc-seq-irno-repeat seq1 5 (drop (* 2 0) irno-seq))
        seq2r (calc-seq-irno-repeat seq2 4 (drop (* 2 5) irno-seq))
        seq3r (calc-seq-irno-repeat seq3 4 (drop (* 2 9) irno-seq))
        intro-seq (offset-seq beat (concat-seq seq1
                                               seq1
                                               (offset-seq 2 seq2)
                                               seq2
                                               (offset-seq 2 seq3)
                                               seq3))
        intro-end (max-beat intro-seq)
        foo (println "introduction from" beat "to" intro-end)

        theme-start (+ 2 intro-end)
        theme-seq1r-start (+ theme-start (* 0 (num-beats seq1)))
        theme-seq2r-start (+ theme-start (* 2 (num-beats seq1)))
        theme-seq3r-start (+ theme-start (* 0 (num-beats seq1)))
        theme-seq1r (offset-seq theme-seq1r-start seq1r)
        theme-seq2r (offset-seq theme-seq2r-start seq2r)
        theme-seq3r (offset-seq theme-seq3r-start seq3r)
        theme-end (max (max-beat theme-seq1r)
                       (max-beat theme-seq2r)
                       (max-beat theme-seq3r))
        foo (println "theme from" theme-start "to" theme-end)
        foo (println "  theme1 from" theme-start "to" (max-beat theme-seq1r))
        foo (println "  theme2 from" theme-seq2r-start "to" (max-beat theme-seq2r))
        foo (println "  theme3 from" theme-seq3r-start "to" (max-beat theme-seq3r))
        
        concl-start (- theme-end (* 2 (num-beats seq1)))
        concl-seq (offset-seq concl-start (concat-seq seq3
                                                      seq3
                                                      (offset-seq 2 seq2)
                                                      seq2
                                                      (offset-seq 2 seq1)
                                                      seq1))
        concl-end (max-beat concl-seq)
        foo (println "conclusion from" concl-start "to" concl-end)
        ]
    (list intro-seq
          theme-seq1r
          theme-seq2r
          theme-seq3r
          concl-seq)))

(def piso-tempo-points
  [0.0    20.0
   17.0   100.0
   105.0  80.0
   118.0  100.0
   131.0  80.0
   200.0  105.0
   300.0  90.0
   400.0  95.0
   500.0  90.0
   513.0  20.0
   1000.0 20.01])
