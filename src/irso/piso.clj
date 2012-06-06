(ns irso.piso
  (:use [irso.core])
  (:use [overtone.core])
  (:use [overtone.inst.sampled-piano])) ;; requires 0.7.0. downloads 200MB

;;(use 'irso.core)

;; ======================================================================
;; the song
(defn ^:dynamic piso [m beat tonic type]
  (let
    [seq1 (calc-seq tonic type 13 0 pi-1000)
     seq2 (calc-seq tonic type 17 (count seq1) pi-1000)
     seq3 (calc-seq tonic type 19 (+ (count seq1) (count seq2)) pi-1000)
     foo (println "pi song")
     
     b000 (play-seq sampled-piano m beat seq1)
     b001 (play-seq sampled-piano m b000 seq1)
     b002 (play-seq sampled-piano m (+ 2 b001) seq2)
     b003 (play-seq sampled-piano m b002 seq2)
     b004 (play-seq sampled-piano m (+ 2 b003) seq3)
     b005 (play-seq sampled-piano m b004 seq3)
     foo (println "introduction from" beat "to" b005)
     
     b010 (+ 2 b005)
     b011 (+ b010 (* 3 (num-beats seq1)))
     b012 (+ b010 (* 5 (num-beats seq1)))
     b013 (play-repeated-snote-seq sampled-piano m b010 tonic type seq1 3 pi-1000)
     b014 (play-repeated-snote-seq sampled-piano m b011 tonic type seq2 2 pi-1000)
     b015 (play-repeated-snote-seq sampled-piano m b012 tonic type seq3 2 pi-1000)
     foo (println "theme from" b010 "to" (max b013 b014 b015))
              
     b020 (play-seq sampled-piano m (+ 4 b015) seq3)
     b021 (play-seq sampled-piano m b020 seq3)
     b022 (play-seq sampled-piano m (+ 4 b021) seq2)
     b023 (play-seq sampled-piano m b022 seq2)
     b024 (play-seq sampled-piano m (+ 4 b023) seq1)
     b025 (play-seq sampled-piano m b024 seq1)
     foo (println "conclusion from" b015 "to" b025)]
    nil))

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

;; ======================================================================
;; and play...
(defn play-piso [] 
  (let [metro (metronome 80)]
    (piso metro (metro) :c3 :pentatonic)
    metro))
;; (play-piso)
;; (stop)

;; debugging
;; for ^:dynamic, see http://stackoverflow.com/questions/8875353/why-im-getting-cant-dynamically-bind-non-dynamic-var
;; (use 'clojure.tools.trace)
;; (dotrace [piso play-repeated-snote-seq] (piso metro (metro) :c3 :pentatonic))

