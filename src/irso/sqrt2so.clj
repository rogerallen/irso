(ns irso.sqrt2so
  (:use [irso.core])
  (:use [overtone.core])
  (:use [overtone.inst.sampled-piano])) ;; requires 0.7.0. downloads 200MB

;;(use 'irso.core)

;; ======================================================================
;; the song
(defn ^:dynamic calc-sqrt2so [beat tonic type]
  (let [irno-seq sqrt2-1000
        foo (println "sqrt2 song")
        seq1 (calc-seq tonic type 13 irno-seq)
        seq2 (calc-seq tonic type 17 (drop (count seq1) irno-seq))
        seq3 (calc-seq tonic type 19 (drop (+ (count seq1)
                                              (count seq2)) irno-seq))
        seq1r (calc-seq-irno-repeat seq1 5 (drop (* 2 0) irno-seq))
        seq2r (calc-seq-irno-repeat seq2 4 (drop (* 2 5) irno-seq))
        seq3r (calc-seq-irno-repeat seq3 5 (drop (* 2 9) irno-seq))
        intro-seq (offset-seq beat (concat-seq seq1
                                               seq1
                                               (offset-seq 2 seq2)
                                               seq2
                                               (offset-seq 2 seq3)
                                               seq3))
        intro-end (max-beat intro-seq)
        foo (println "introduction from" beat "to" intro-end)

        theme-start (+ 2 intro-end)
        theme-seq1r-start (+ theme-start (* 1 (num-beats seq1)))
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

(defn ^:dynamic sqrt2so [m beat tonic type]
  (let [seq-list (calc-sqrt2so beat tonic type)]
    (draw-seqs seq-list "Sqrt2 Song")
    (play-seqs sampled-piano m beat seq-list)))

;; ======================================================================
;; Add effects to create the proper mood

;; TBD

;; ======================================================================
;; and play...
(defn play-sqrt2so [] 
  (let [metro (metronome 80)]
    (sqrt2so metro (metro) :c3 :pentatonic)
    metro))
;; (play-sqrt2so)
;; (stop)

