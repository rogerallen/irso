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
(defn main-play-eso []
  (let [foo (prn "Starting...")
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

(defn main-play-piso []
  (let [m (metronome 80)
        seq-list (calc-piso (m) :c3 :pentatonic)
        ]
    (draw-seqs seq-list "Pi Song")
    (play-seqs sampled-piano m (m) seq-list)
    ))

(defn main-play-sqrt2so []
  (let [m (metronome 80)
        seq-list (calc-sqrt2so (m) :c3 :pentatonic)
        ]
    (draw-seqs seq-list "Sqrt2 Song")
    (play-seqs sampled-piano m (m) seq-list)
    ))

(defn main-play-sqrt3so []
  (let [m (metronome 80)
        seq-list (calc-sqrt3so (m) :c3 :pentatonic)
        ]
    (draw-seqs seq-list "Sqrt3 Song")
    (play-seqs sampled-piano m (m) seq-list)
    ))

(defn main-play-tauso []
  (let [m (metronome 80)
        seq-list (calc-tauso (m) :c3 :pentatonic)
        ]
    (draw-seqs seq-list "Tau Song")
    (play-seqs sampled-piano m (m) seq-list)
    ))

