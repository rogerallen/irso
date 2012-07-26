(ns irso.core
  (:use [overtone.live]
        [overtone.inst.sampled-piano]
        [irso.rhythm]
        [irso.irno]
        [irso.irso]
        [irso.eso]
        [irso.phiso]
        [irso.piso]
        [irso.sqrt2so]
        [irso.sqrt3so]
        [irso.tauso]
        )
  (:gen-class)
  )
  
;; ======================================================================
(defn play-song [calc-fn fx-fn tempo-points tonic scale-type instrument title]
  (let [m (pwl-metronome tempo-points)
        seq-list (calc-fn (m) tonic scale-type)
        foo (fx-fn instrument)
        ;; play seq some beats after startup to give some time for window to come up
        final-beat (play-seqs instrument m 4 seq-list)
        the-frame (draw-seqs seq-list m 4 title)
        ]
    {:metronome m
     :last-beat final-beat
     :window-frame the-frame}))

(defn play-song-and-wait [calc-fn fx-fn tempo-points tonic scale-type instrument title]
  (let [foo (println "Playing...")
        {m :metronome 
         final-beat :last-beat
         ;;_ (println "final-beat" final-beat)
         the-frame :window-frame} (play-song calc-fn fx-fn tempo-points tonic
                                             scale-type instrument title)
        ]
    (while (<= (m) (+ 4 final-beat))
      ;;(println "cur-beat" (m))
      (Thread/sleep 1000))
    (println "Done.")))

(defn time-stamp-wav [s]
  (str s
       "_"
       (.format (java.text.SimpleDateFormat. "yyMMdd_HHmm") (now))
       ".wav"))

;; ======================================================================
(defn play-eso []
  (play-song-and-wait
   calc-eso setup-irso-fx eso-tempo-points
   :c3 :pentatonic
   sampled-piano
   "E Song"))

(defn play-phiso []
  (play-song-and-wait
   calc-phiso setup-irso-fx phiso-tempo-points
   :c3 :pentatonic
   sampled-piano
   "Phi Song"))

(defn play-piso []
  (play-song-and-wait
   calc-piso setup-irso-fx piso-tempo-points
   :c3 :pentatonic
   sampled-piano
   "Pi Song"))

(defn play-sqrt2so []
  (play-song-and-wait
   calc-sqrt2so setup-irso-fx sqrt2so-tempo-points
   :c3 :pentatonic
   sampled-piano
   "Sqrt2 Song"))

(defn play-sqrt3so []
  (play-song-and-wait
   calc-sqrt3so setup-irso-fx sqrt3so-tempo-points
   :c3 :pentatonic
   sampled-piano
   "Sqrt3 Song"))

(defn play-tauso []
  (play-song-and-wait
   calc-tauso setup-irso-fx tauso-tempo-points
   :c3 :pentatonic
   sampled-piano
   "Tau Song"))

(defn play-testso []
  (play-song-and-wait
   calc-testso setup-irso-fx testso-tempo-points
   :c3 :pentatonic
   sampled-piano
   "Test Song"))

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
(def-main-play testso)

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
;; main main ... what should this do?
(defn -main [& args]
  (println "Welcome to irso! These are your args:" args)
  ;; Hmmm lein2 deprecated :run-aliases I guess?
  ;; :run-aliases {:eso         irso.core/main-play-eso
  ;;               :phiso       irso.core/main-play-phiso
  ;;               :piso        irso.core/main-play-piso
  ;;               :sqrt2so     irso.core/main-play-sqrt2so
  ;;               :sqrt3so     irso.core/main-play-sqrt3so
  ;;               :tauso       irso.core/main-play-tauso
  ;;               :rec-eso     irso.core/main-record-eso
  ;;               :rec-phiso   irso.core/main-record-phiso
  ;;               :rec-piso    irso.core/main-record-piso
  ;;               :rec-sqrt2so irso.core/main-record-sqrt2so
  ;;               :rec-sqrt3so irso.core/main-record-sqrt3so
  ;;               :rec-tauso   irso.core/main-record-tauso
  ;;               }
  ;; (println "main doesn't do anything...exiting.")
  (cond
   (= ":eso"         (first args)) (main-play-eso)
   (= ":phiso"       (first args)) (main-play-phiso)
   (= ":piso"        (first args)) (main-play-piso)
   (= ":sqrt2so"     (first args)) (main-play-sqrt2so)
   (= ":sqrt3so"     (first args)) (main-play-sqrt3so)
   (= ":tauso"       (first args)) (main-play-tauso)
   (= ":testso"      (first args)) (main-play-testso)
   (= ":rec-eso"     (first args)) (main-record-eso)
   (= ":rec-phiso"   (first args)) (main-record-phiso)
   (= ":rec-piso"    (first args)) (main-record-piso)
   (= ":rec-sqrt2so" (first args)) (main-record-sqrt2so)
   (= ":rec-sqrt3so" (first args)) (main-record-sqrt3so)
   (= ":rec-tauso"   (first args)) (main-record-tauso)
   true (do
          (println "no args, nothing to do. Exiting.")
          (System/exit 0))))

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
