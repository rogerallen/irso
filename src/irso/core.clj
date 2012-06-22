(ns irso.core
  (:use [overtone.live]
        [overtone.inst.sampled-piano]
        [irso.irso]
        [irso.eso]
        [irso.phiso]
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
        final-beat (play-seqs instrument m 10 seq-list)
        the-frame (draw-seqs seq-list m 10 title)
        ]
    m))

;; ======================================================================
(defn play-song-and-wait [calc-fn tempo tonic scale-type instrument title]
  (let [foo (println "Playing...")
        m (metronome tempo)
        seq-list (calc-fn (m) tonic scale-type)
        ;; play seq some beats after startup to give some time for window to come up
        final-beat (play-seqs instrument m 8 seq-list)
        the-frame (draw-seqs seq-list m 8 title)
        ]
    (while (<= (m) (+ 8 final-beat))
        (Thread/sleep 1000))
    (println "Done.")))

(defn time-stamp-wav [s]
  (str s
       "_"
       (.format (java.text.SimpleDateFormat. "yyMMdd_HHmm") (now))
       ".wav"))

;; ======================================================================
(defn play-eso []
  (play-song-and-wait calc-eso 80 :c3 :pentatonic sampled-piano "E Song"))

(defn play-phiso []
  (play-song-and-wait calc-phiso 80 :c3 :pentatonic sampled-piano "Phi Song"))

(defn play-piso []
  (play-song-and-wait calc-piso 80 :c3 :pentatonic sampled-piano "Pi Song"))

(defn play-sqrt2so []
  (play-song-and-wait calc-sqrt2so 80 :c3 :pentatonic sampled-piano "Sqrt2 Song"))

(defn play-sqrt3so []
  (play-song-and-wait calc-sqrt3so 80 :c3 :pentatonic sampled-piano "Sqrt3 Song"))

(defn play-tauso []
  (play-song-and-wait calc-tauso 80 :c3 :pentatonic sampled-piano "Tau Song"))

;; ======================================================================
(defn main-play-eso []
  (play-eso)
  (System/exit 0))  ;; FIXME -- is this really necessary?

(defn main-play-phiso []
  (play-phiso)
  (System/exit 0))

(defn main-play-piso []
  (play-piso)
  (System/exit 0))

(defn main-play-sqrt2so []
  (play-sqrt2so)
  (System/exit 0))

(defn main-play-sqrt3so []
  (play-sqrt3so)
  (System/exit 0))

(defn main-play-tauso []
  (play-tauso)
  (System/exit 0))

;; ======================================================================
(defn main-record-eso []
  (let [filename (time-stamp-wav "~/eso")]
    (println "recording to" filename)
    (recording-start filename)
    (play-eso)
    (recording-stop)
    (System/exit 0)))

(defn main-record-phiso []
  (let [filename (time-stamp-wav "~/phiso")]
    (println "recording to" filename)
    (recording-start filename)
    (play-phiso)
    (recording-stop)
    (System/exit 0)))

(defn main-record-piso []
  (let [filename (time-stamp-wav "~/piso")]
    (println "recording to" filename)
    (recording-start filename)
    (play-piso)
    (recording-stop)
    (System/exit 0)))

(defn main-record-sqrt2so []
  (let [filename (time-stamp-wav "~/sqrt2so")]
    (println "recording to" filename)
    (recording-start filename)
    (play-sqrt2so)
    (recording-stop)
    (System/exit 0)))

(defn main-record-sqrt3so []
  (let [filename (time-stamp-wav "~/sqrt3so")]
    (println "recording to" filename)
    (recording-start filename)
    (play-sqrt3so)
    (recording-stop)
    (System/exit 0)))

(defn main-record-tauso []
  (let [filename (time-stamp-wav "~/tauso")]
    (println "recording to" filename)
    (recording-start filename)
    (play-tauso)
    (recording-stop)
    (System/exit 0)))

