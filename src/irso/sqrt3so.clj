(ns irso.sqrt3so
  (:use [irso.core])
  (:use [overtone.core])
  (:use [overtone.inst.sampled-piano])) ;; requires 0.7.0. downloads 200MB

;;(use 'irso.core)

;; ======================================================================
;; the song
(defn ^:dynamic sqrt3so [m beat tonic type]
  (let
    [irno-seq sqrt3-1000
     seq1 (calc-seq tonic type 13 irno-seq)
     seq2 (calc-seq tonic type 17 (drop (count seq1) irno-seq))
     seq3 (calc-seq tonic type 15 (drop (+ (count seq1)
                                           (count seq2)) irno-seq))
     seq1r (calc-seq-irno-repeat seq1 5 irno-seq)
     seq2r (calc-seq-irno-repeat seq2 2 (drop (* 2 5) irno-seq))
     seq3r (calc-seq-irno-repeat seq3 2 (drop (+ (* 2 5)
                                                 (* 2 2)) irno-seq))
     foo (println "sqrt3 song")
     
     b000 (play-seq sampled-piano m beat seq1)
     b001 (play-seq sampled-piano m b000 seq1)
     b002 (play-seq sampled-piano m (+ 2 b001) seq2)
     b003 (play-seq sampled-piano m b002 seq2)
     b004 (play-seq sampled-piano m (+ 2 b003) seq3)
     b005 (play-seq sampled-piano m b004 seq3)
     foo (println "introduction from" beat "to" b005)

     ;; hole 240-280?
     b010 (+ 2 b005)
     b0100 (+ b010 (* 9 (num-beats seq1)))
     b011 (+ b010)
     b012 (+ b010 (* 2 (num-beats seq1)))
     b013 (play-seq sampled-piano m b0100 seq1r)
     b014 (play-seq sampled-piano m b011 seq2r)
     b015 (play-seq sampled-piano m b012 seq3r)
     b018 (max b013 b014 b015)
     foo (println "theme from" b010 "to" b018)
     foo (println "  theme1 from" b0100 "to" b013)
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
(defn play-sqrt3so [] 
  (let [metro (metronome 80)]
    (sqrt3so metro (metro) :c3 :pentatonic)
    metro))
;; (play-sqrt3so)
;; (stop)

