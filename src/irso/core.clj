(ns irso.core
  (:use [overtone.live]
        [overtone.inst.sampled-piano]
        [irso.irso]
        [irso.eso])
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
(defn main-play-eso []
  (let [foo (prn "Connecting...")
        ;;foo (connect-external-server 57110)
        m (metronome 80)
        foo (prn "Calculating...")
        seq-list (calc-eso (m) :c3 :pentatonic)
        ]
    (prn "Drawing...")
    (draw-seqs seq-list "E Song")
    (prn "Playing...")
    (play-seqs sampled-piano m (m) seq-list)
    (prn "Done?")
    ))
