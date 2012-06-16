(ns irso.tauso
  (:use [irso.core])
  (:use [overtone.core])
  (:use [overtone.inst.sampled-piano])) ;; requires 0.7.0. downloads 200MB

;;(use 'irso.core)

;; ======================================================================
;; the song
(defn ^:dynamic calc-tauso [beat tonic type]
  (let [irno-seq tau-1000
        foo (println "tau song")
        seq1 (calc-seq tonic type 13 irno-seq)
        seq2 (calc-seq tonic type 17 (drop (count seq1) irno-seq))
        seq3 (calc-seq tonic type 19 (drop (+ (count seq1)
                                              (count seq2)) irno-seq))
        seq1r (calc-seq-irno-repeat seq1 5 (drop (* 2 13) irno-seq))
        seq2r (calc-seq-irno-repeat seq2 4 (drop (* 2 18) irno-seq))
        seq3r (calc-seq-irno-repeat seq3 4 (drop (* 2 22) irno-seq))
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

(defn ^:dynamic tauso [m beat tonic type]
  (let [seq-list (calc-tauso beat tonic type)]
    (draw-seqs seq-list "Tau Song")
    (play-seqs sampled-piano m beat seq-list)))

(defn ^:dynamic tauso [m beat tonic type]
  (let
    [irno-seq tau-1000
     seq1 (calc-seq tonic type 13 irno-seq)
     seq2 (calc-seq tonic type 17 (drop (count seq1) irno-seq))
     seq3 (calc-seq tonic type 19 (drop (+ (count seq1)
                                           (count seq2)) irno-seq))
     seq1r (calc-seq-irno-repeat seq1 3 irno-seq)
     seq2r (calc-seq-irno-repeat seq2 2 (drop (count seq1) irno-seq))
     seq3r (calc-seq-irno-repeat seq3 2 (drop (+ (count seq1)
                                                 (count seq2)) irno-seq))
     foo (println "tau song")
     
     b000 (play-seq sampled-piano m beat seq1)
     b001 (play-seq sampled-piano m b000 seq1)
     b002 (play-seq sampled-piano m (+ 2 b001) seq2)
     b003 (play-seq sampled-piano m b002 seq2)
     b004 (play-seq sampled-piano m (+ 2 b003) seq3)
     b005 (play-seq sampled-piano m b004 seq3)
     foo (println "introduction from" beat "to" b005)

     b010 (+ 2 b005)
     b011 (+ b010 (* 2 (num-beats seq1)))
     b012 (+ b010 (* 1 (num-beats seq1)))
     b013 (play-seq sampled-piano m b010 seq1r)
     b014 (play-seq sampled-piano m b011 seq2r)
     b015 (play-seq sampled-piano m b012 seq3r)
     b018 (max b013 b014 b015)
     foo (println "theme from" b010 "to" b018)
     foo (println "  theme1 from" b010 "to" b013)
     foo (println "  theme2 from" b011 "to" b014)
     foo (println "  theme3 from" b012 "to" b015)     

     b020 (min b013 b014 b015)
     b022 (play-seq sampled-piano m (+ 4 b020) seq3)
     b023 (play-seq sampled-piano m b022 seq3)
     b024 (play-seq sampled-piano m (+ 4 b023) seq2)
     b025 (play-seq sampled-piano m b024 seq2)
     b026 (play-seq sampled-piano m (+ 4 b025) seq1)
     b027 (play-seq sampled-piano m b026 seq1)
     foo (println "conclusion from" b020 "to" b027)]
    nil))

;; ======================================================================
;; Add effects to create the proper mood

;; TBD

;; ======================================================================
;; and play...
(defn play-tauso [] 
  (let [metro (metronome 80)]
    (tauso metro (metro) :c3 :pentatonic)
    metro))
;; (play-tauso)
;; (stop)

