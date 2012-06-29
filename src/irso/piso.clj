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

;; ======================================================================
;; Add effects to create the proper mood
#_(do 
  ;;(def fx0 (inst-fx sampled-piano fx-freeverb))
  ;;(ctl fx0 :room-size 1.5)
  ;;(ctl fx0 :dampening 0.5)
  ;;(ctl fx0 :wet-dry   0.5) ;; dry = direct.  wet = reflections
  ;;
  ;; hmm, freeverb seems to resolve eventually to a "ringing" tone that
  ;; is distracting.
  
  ;; try just reverb...
  (def fx1 (inst-fx sampled-piano fx-reverb))
  
  ;; using lowpass filter to remove "ringing" tone.  
  (defsynth fx-lpf
    [bus 0 freq 20000]
    (let [src (in bus)]
      (replace-out bus (lpf src freq))))
  (def fx2 (inst-fx sampled-piano fx-lpf))
  (ctl fx2 :freq      2400)
  )
;; should also try 'panning' the cutoff frequencies, etc. during music
;;(clear-fx sampled-piano)

