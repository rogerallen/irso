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
  
;; ======================================================================
(defn play-song [calc-fn fx-fn tempo tonic scale-type instrument title]
  (let [m (metronome tempo)
        seq-list (calc-fn (m) tonic scale-type)
        foo (fx-fn instrument)
        ;; play seq some beats after startup to give some time for window to come up
        final-beat (play-seqs instrument m 8 seq-list)
        the-frame (draw-seqs seq-list m 8 title)
        ]
    {:metronome m
     :last-beat final-beat
     :window-frame the-frame}))

(defn play-song-and-wait [calc-fn fx-fn tempo tonic scale-type instrument title]
  (let [foo (println "Playing...")
        {m :metronome 
         final-beat :last-beat
         the-frame :window-frame} (play-song calc-fn fx-fn tempo tonic
                                             scale-type instrument title)
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
  (play-song-and-wait calc-eso setup-irso-fx 80 :c3 :pentatonic sampled-piano
                      "E Song"))

(defn play-phiso []
  (play-song-and-wait calc-phiso setup-irso-fx 80 :c3 :pentatonic sampled-piano
                      "Phi Song"))

(defn play-piso []
  (play-song-and-wait calc-piso setup-irso-fx 80 :c3 :pentatonic sampled-piano
                      "Pi Song"))

(defn play-sqrt2so []
  (play-song-and-wait calc-sqrt2so setup-irso-fx 80 :c3 :pentatonic sampled-piano
                      "Sqrt2 Song"))

(defn play-sqrt3so []
  (play-song-and-wait calc-sqrt3so setup-irso-fx 80 :c3 :pentatonic sampled-piano
                      "Sqrt3 Song"))

(defn play-tauso []
  (play-song-and-wait calc-tauso setup-irso-fx 80 :c3 :pentatonic sampled-piano
                      "Tau Song"))

;; ======================================================================
;; main entry points for playing
(defmacro def-main-play [name]
  `(defn ~(symbol (str "main-play-" name)) [] 
     (~(symbol (str "play-" name)))
     (System/exit 0))) ;; FIXME -- is this really necessary?
;; instantiate main entry points
;; use (macroexpand-1 '(def-main-play eso)) to see what this expands to
(def-main-play eso)
(def-main-play phiso)
(def-main-play piso)
(def-main-play sqrt2so)
(def-main-play sqrt3so)
(def-main-play tauso)

;; ======================================================================
;; main entry points for recording
(defmacro def-main-record [name]
  `(defn ~(symbol (str "main-record-" name)) []
     (let [~'filename (time-stamp-wav ~(str "~/" name))]
       (println "recording to" ~'filename)
       (recording-start ~'filename)
       (~(symbol (str "play-" name)))
       (recording-stop)
       (System/exit 0))))

;; instantiate main entry points
(def-main-record eso)
(def-main-record phiso)
(def-main-record piso)
(def-main-record sqrt2so)
(def-main-record sqrt3so)
(def-main-record tauso)

;; ======================================================================
;; repl cut-n-paste stuff
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
