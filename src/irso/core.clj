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
   (use 'overtone.core)
   (connect-external-server 57110)

   (use 'irso.eso)
   (def m (play-eso))
   ;;then use (m) to see what the current beat is

   (use 'irso.piso)
   (def m (play-piso))

   (use 'irso.sqrt2so)
   (def m (play-sqrt2so))

   (use 'irso.sqrt3so)
   (def m (play-sqrt3so))

   (use 'irso.tauso)
   (def m (play-tauso))

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
(defn main-play-song [calc-fn tempo tonic scale-type instrument title]
  (let [foo (prn "Playing" title)
        ;;foo (connect-external-server 57110)
        m (metronome tempo)
        ;;foo (prn "Calculating...")
        seq-list (calc-fn (m) tonic scale-type)
        ;;foo (prn "Drawing...")
        the-frame (draw-seqs seq-list title)
        ;;foo (prn "Playing...")
        final-beat (play-seqs instrument m (m) seq-list)
        ]
    (doseq [cur-beat (repeatedly m)]
      ;;(prn final-beat cur-beat)
      (if (> cur-beat (+ 10 final-beat))
        (do
          (prn "Done.")
          (System/exit 0)))
      ;;(prn "Sleeping...")
      (Thread/sleep 2000))))

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

