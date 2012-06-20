(ns irso.core
  (:use [overtone.live]
        [overtone.inst.sampled-piano]
        [irso.irso]
        [irso.eso]
        [irso.piso]
        [irso.sqrt2so]
        [irso.sqrt3so]
        [irso.tauso])
  )
;; paste these into the repl (not C-x C-e)
#_(
   (use 'irso.core)
   (use 'irso.irso)
   
   ;;(use 'overtone.core)
   ;;(connect-external-server 57110)

   (use :reload-all 'irso.test.core)
   (test-calc-seq)
   (test-offset-seq)
   (test-num-beats)
   (test-min-beat)
   (test-max-beat)
   (test-concat-seq)
   (test-concat-seq2)
)
  
;; ======================================================================
(defn play-song [calc-fn tempo tonic scale-type instrument title]
  (let [foo (prn "Playing" title)
        m (metronome tempo)
        seq-list (calc-fn (m) tonic scale-type)
        the-frame (draw-seqs seq-list m title)
        final-beat (play-seqs instrument m (m) seq-list)
        ]
    m))

;; ======================================================================
(defn main-play-song [calc-fn tempo tonic scale-type instrument title]
  (let [foo (prn "Playing" title)
        ;;foo (connect-external-server 57110)
        m (metronome tempo)
        ;;foo (prn "Calculating...")
        seq-list (calc-fn (m) tonic scale-type)
        ;;foo (prn "Drawing...")
        the-frame (draw-seqs seq-list m title)
        ;;foo (prn "Playing...")
        final-beat (play-seqs instrument m (m) seq-list)
        ]
    (doseq [cur-beat (repeatedly m)]
      ;;(prn final-beat cur-beat)
      (if (> cur-beat (+ 10 final-beat))
        (do
          (prn "Done.")
          (System/exit 0))) ;; FIXME -- is this really necessary?
      ;;(draw-seqs-update seq-list (m)) ;; YUCK!  slideshow
      ;;(prn "Sleeping...")
      (Thread/sleep 1000))))

(defn main-play-eso []
  (main-play-song calc-eso 80 :c3 :pentatonic sampled-piano "E Song"))

(defn main-play-piso []
  (main-play-song calc-piso 80 :c3 :pentatonic sampled-piano "Pi Song"))

(defn main-play-sqrt2so []
  (main-play-song calc-sqrt2so 80 :c3 :pentatonic sampled-piano "Sqrt2 Song"))

(defn main-play-sqrt3so []
  (main-play-song calc-sqrt3so 80 :c3 :pentatonic sampled-piano "Sqrt3 Song"))

(defn main-play-tauso []
  (main-play-song calc-tauso 80 :c3 :pentatonic sampled-piano "Tau Song"))

