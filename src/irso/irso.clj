(ns irso.irso
  (:use [overtone.live]
        [irso.rhythm :only [pwl-fn vr-metro-now-beat vr-metro-time]])
  (:require [quil.core])
  )

(defn linear-map
  "given points (x0,y0), (x1,y1) calculate linear relation y given x"
  [x0 x1 y0 y1 x]
  (let [dydx (/ (- y1 y0) (- x1 x0))
        dx (- x x0)]
    (+ y0 (* dydx dx))))
        
;; change to play the first digits as pitches
(defn digits2inotes
  "given a list of digits, make it into a list of index notes"
  [digit-seq]
  (let [n (int (/ (count digit-seq) 3))]
    (map #(hash-map :pitch-index %1 :velocity-index %2 :duration-index %3)
         (take n digit-seq)
         (take n (drop n digit-seq))
         (take n (drop (* 2 n) digit-seq)))))

(defn index2pitch
  "given a digit in range 0..9 find index in scale defined by
     tonic & type.  E.g. (index2pitch :c4 :major 1) -> 62"
  [tonic type index]
  (nth (vec (scale tonic type (range 1 10))) (mod index 10)))

(defn index2velocity
  "given a digit 'n' in range 0..9, find a velocity to play"
  [index]
  (+ 20 (* 8 index)))

(defn velocity2attack
  "sampled-piano uses attack & level, not velocity"
  [v]
  (linear-map 0 127 0.2 0.05 v))

(defn velocity2level
  "sampled-piano uses attack & level, not velocity"
  [v]
  (linear-map 0 127 0.0 1.0 v))

(defn index2duration
  "given a digit 'n' in range 0..9, find a length in beats"
  [index]
  (cond ;; pick one below...
    ;;        0    1    2    3    4    5    6    7    8    9
    false ([ 4.00 2.00 1.33 1.00 0.80 0.66 0.57 0.50 0.44 0.40] index)  ;; 1/f
    false ([ 4.00 2.00 1.50 1.00 0.75 0.75 0.50 0.50 0.50 0.25] index)  ;; 16x range
    true ([ 4.00 2.00 1.50 1.00 0.75 0.75 0.50 0.50 0.50 0.50] index)  ;; 8x range
    false  ([ 4.00 2.00 1.50 1.50 1.25 1.25 1.00 1.00 1.00 1.00] index))) ;; 4x range

(defn inote2snote
  "given an index-note, create a sequence-note with a place for a beat."
  [tonic type cur-inote]
  (hash-map
   :pitch (index2pitch tonic type (:pitch-index cur-inote))
   :velocity (index2velocity (:velocity-index cur-inote))
   :duration (index2duration (:duration-index cur-inote))
   :beat 0))

(defn duration2beat
  "given 2 sequence notes, overwrite the nxt beat"
  [cur-snote nxt-snote]
  (assoc nxt-snote :beat (+ (:duration cur-snote) (:beat cur-snote))))

(defn duration2beat2
  "given 2 sequence notes, increment the value of nxt beat"
  [cur-snote nxt-snote]
  (assoc nxt-snote
    :beat (+ (:duration cur-snote) (:beat cur-snote) (:beat nxt-snote))))

;; ======================================================================
;; music sequence code.  ??? Should we use a different name than 'seq'
;; since that also aliases with the clojure concept?

;; FIXME better name
(defn only-rest-beats
  "given a snote-seq, zero out all the beats that are contiguous and
  can be calculated via :duration, leaving only the beats that
  describe rest beats.  For use in concat-seq."
  [snote-seq]
  (drop 1
        (reductions #(dissoc (assoc %2 :beat (:beat2 %2)) :beat2)
                    ;; create intermediate beat2, remove in prev reduction
                    (reductions #(assoc %2
                                   :beat2 (- (:beat %2) (+ (:duration %1) (:beat %1))))
                                {:duration 0 :beat 0 :beat2 0} ;; init
                                snote-seq))))

(defn concat-seq
  "given a seq of snote-seq's that should start relative to beat 0,
  join them all into one larger sequence.  Need duration2beat2 in
  order to allow use of offset-seq in the list of inputs."
  [& zs]
  (reductions duration2beat2 (apply concat (map only-rest-beats zs))))

(defn offset-seq
  "given a snote-seq and offset, return a sequence that is offset by that beat"
  [offset snote-seq]
  (map #(assoc %1 :beat (+ offset (:beat %1))) snote-seq))
                    
(defn min-beat
  "starting beat"
  [snote-seq]
  (:beat (first snote-seq)))

(defn max-beat
  "how long is a snote sequence? last duration + last beat"
  [snote-seq]
  (let [last-snote (last snote-seq)]
    (+ (:beat last-snote) (:duration last-snote))))

(defn num-beats
  "how long is a snote sequence?"
  [snote-seq]
  (- (max-beat snote-seq) (min-beat snote-seq)))

(defn calc-seq
  "calc some seq-notes in a certain key up to num-beats in duration
  from the-series values. returns a lazy list of seq-note values."
  [tonic type num-beats the-series]
  (for [ n (reductions duration2beat
                       (map #(inote2snote tonic type %)
                            (digits2inotes the-series)))
        :while (< (:beat n) num-beats)]
    (if (> (+ (:beat n) (:duration n)) num-beats)
      (assoc n :duration (- num-beats (:beat n)))
      n)))

;; FIXME -- better name
(defn ^:dynamic calc-seq-irno-repeat
  "given snote-seq and a count of play/rest pairs, find play/rest
  counts from irno-seq.  Calc play/rest seq & return new snote-seq."
  [snote-seq num-play-rests irno-seq]
  (let [snote-seq-len (num-beats snote-seq)
        ;; repeat something 1-4 times 1234123412
        subset-irno-seq (map #(+ 1 (mod % 4)) (take (* 2 num-play-rests) irno-seq))
        ;; 3 1 4 1 5 9 -> repeat-counts = 3 4 5, rest-counts = 1 1 9
        repeat-counts (take-nth 2 subset-irno-seq)
        rest-count-sums (conj (take-nth 2 (drop 1 (reductions + subset-irno-seq))) 0)
        ;; seq-indexes tell when to play the seq as multiple of seq-len
        ;; 0,1,2,[3],4,5,6,7,[8],9,10,11,12,13,[14...17],18
        seq-indexes (flatten (map #(map (fn [x] (+ %2 x)) %1)
                                  (map range repeat-counts)
                                  rest-count-sums))]
    ;;(println "snote-seq indexes & len" seq-indexes snote-seq-len)
    (for [cur-index seq-indexes
          cur-snote snote-seq]
      (assoc cur-snote :beat (+ (* cur-index snote-seq-len) (:beat cur-snote))))))

(defn play-seq
  "play a list of (pitch velocity duration curbeat) where snote-seq is offset by in-beat"
  [inst m in-beat lazy-snote-seq]
  (last ; return beat following sequence
   (let [snote-seq (doall lazy-snote-seq)] ;; remove laziness here
     (for [cur-snote snote-seq]
       (let [cur-pitch (:pitch cur-snote)
             cur-attack (velocity2attack (:velocity cur-snote))
             cur-level (velocity2level (:velocity cur-snote))
             cur-dur (:duration cur-snote)
             cur-beat (+ in-beat (:beat cur-snote))
             cur-tick (m cur-beat)
             ;;_ (println "play-seq" cur-beat (long (- cur-tick (metro-start m))) (long cur-tick) cur-pitch)
             k-beat 1.6
             cur-inst (at cur-tick (inst :note cur-pitch
                                         :level cur-level
                                         :attack cur-attack))]
         (at (m (+ cur-beat (* k-beat cur-dur))) (ctl cur-inst :gate 0))
         (+ cur-beat cur-dur))))))
  
(defn play-seqs
  "play a list of snote-seq"
  [inst m in-beat snote-seqs]
  (last
   (for [snote-seq snote-seqs]
     (play-seq inst m in-beat snote-seq))))

;; ======================================================================
;; a test sequence
(defn calc-testso [beat tonic type]
  (let [foo (println "test song")
        irno-seq (concat (take 32 (cycle (range 10))) ;; pitch
                         (repeat 32 6)                ;; velocity
                         (repeat 32 3))               ;; duration
        seq1 (calc-seq tonic type 32 irno-seq)]
    (list seq1)))
(def testso-tempo-points
  [   0.00  20.0
      9.99  80.0
     10.00 120.0
     20.00 120.01
     30.00  80.0
   1000.00  80.01])

;; ======================================================================
;; code for displaying sequences

;; colors from the solarized theme
(def base-colors
  (hash-map
   :base03    '(0x00 0x2b 0x36)
   :base02    '(0x07 0x36 0x42)
   :base01    '(0x58 0x6e 0x75)
   :base00    '(0x65 0x7b 0x83)
   :base0     '(0x83 0x94 0x96)
   :base1     '(0x93 0xa1 0xa1)
   :base2     '(0xee 0xe8 0xd5)
   :base3     '(0xfd 0xf6 0xe3)))
(def fore-colors
  (hash-map
   :yellow    '(0xb5 0x89 0x00)
   :orange    '(0xcb 0x4b 0x16)
   :red       '(0xdc 0x32 0x2f)
   :magenta   '(0xd3 0x36 0x82)
   :violet    '(0x6c 0x71 0xc4)
   :blue      '(0x26 0x8b 0xd2)
   :cyan      '(0x2a 0xa1 0x98)
   :green     '(0x85 0x99 0x00)))
(defn nth-fore-color [i]
  (let [num-colors (count fore-colors)
        nth-key (nth (keys fore-colors) (mod i num-colors))]
    (fore-colors nth-key)))

;; quil ftw!
(defn window-setup []
  (quil.core/smooth)
  (quil.core/frame-rate 30) ;; 60 made for a hot laptop...
  (apply quil.core/background (:base1 base-colors)))

(defn window-draw [snote-seqs the-metronome offset-beat] ;; offset to give time for window to popup
  (let [seq-space 10
        seq-space2 4
        w (quil.core/width)
        draw-w (- w (* 2 seq-space))
        seq-w draw-w ;; FIXME?
        seq-h 50
        draw-h (- (* (+ seq-h seq-space2) (count snote-seqs)) seq-space2)
        h (+ (* 2 seq-space) draw-h)
        max-seq-beats (reduce max (map max-beat snote-seqs))
        ;;cur-beat (- (/ (- (now) (metro-start the-metronome))
        ;;               (metro-tick the-metronome)) offset-beat)
        cur-beat (- (vr-metro-now-beat the-metronome) offset-beat)
        cur-tick (now) ;;(vr-metro-time the-metronome cur-beat)
        ;;_ (println "q-cur-beat" cur-beat (long (- cur-tick (metro-start the-metronome))) (long cur-tick))
        ]
    ;; background
    (apply quil.core/fill (:base1 base-colors))
    (quil.core/no-stroke)
    (quil.core/rect 0 0 w h)
    ;; drawing area background
    (apply quil.core/fill (:base00 base-colors))
    (quil.core/rect seq-space seq-space draw-w draw-h)  ;; FIXME round-rect
    ;; draw the sequences...
    (doseq [[i cur-seq] (map-indexed vector snote-seqs)]
      (let [cur-min-beat (min-beat cur-seq)
            cur-num-beats (num-beats cur-seq)
            x0 (+ seq-space (* seq-w (/ cur-min-beat max-seq-beats)))
            x1 (* seq-w (/ cur-num-beats max-seq-beats))
            y0 (+ seq-space (* (+ seq-h seq-space2) i))
            y1 seq-h]
        ;;(println i "drawRect" x0 y0 x1 y1)
        (quil.core/stroke-weight 0.8)
        (apply quil.core/fill (:base2 base-colors)) 
        (apply quil.core/stroke (:base02 base-colors))
        (quil.core/rect x0 y0 x1 y1)
        ;; draw the notes...
        (doseq [snote cur-seq]
          (let [nx0 (+ seq-space
                       (* seq-w (/ (:beat snote) max-seq-beats)))
                nx1 (+ seq-space
                       (* seq-w (/ (+ (:beat snote) (:duration snote))
                                   max-seq-beats)))
                ny (- (+ y0 y1)
                      (* seq-h (/ (:pitch snote) 127)))]
            ;;(println "drawStroke" nx0 nx1 ny)
            (if (and (>= cur-beat (:beat snote)) (<= cur-beat (+ (:beat snote) (:duration snote))))
              (do (quil.core/stroke-weight 2.5)
                  (apply quil.core/stroke (:base00 base-colors)))
              (do
                (quil.core/stroke-weight 1.5)
                (apply quil.core/stroke (nth-fore-color i))))
            (quil.core/line nx0 ny nx1 ny)))
        ))
    ;; draw line for metronome
    (if (and (>= cur-beat 0) (<= cur-beat max-seq-beats))
      (let [bx (+ seq-space (* seq-w (/ cur-beat max-seq-beats)))]
        (quil.core/stroke-weight 1.0)
        (apply quil.core/stroke (:base03 base-colors))
        (quil.core/line bx seq-space bx (+ seq-space draw-h))))
    ;; draw top rect outline
    (quil.core/stroke-weight 0.5)
    (quil.core/no-fill)
    (apply quil.core/stroke (:base02 base-colors))
    (quil.core/rect seq-space seq-space draw-w draw-h)))

(defn draw-seqs [snote-seqs the-metronome offset-beat window-name]
  (let [seq-h 50
        seq-space 10
        seq-space2 4
        draw-h (- (* (+ seq-h seq-space2) (count snote-seqs)) seq-space2)
        h (+ (* 2 seq-space) draw-h)]
    (quil.core/defsketch window-sketch
      :title "window-name" ;; FIXME
      :setup window-setup
      :draw (partial window-draw snote-seqs the-metronome offset-beat)
      :size [(* 0.95 (.width (.getScreenSize (java.awt.Toolkit/getDefaultToolkit)))) h])))

;; ======================================================================
;; fx-functions
(defsynth fx-my-echo
  [bus 0 max-delay 4.0 delay-time 0.5 decay-time 1.0]
  (let [source (in bus)
        echo (comb-c source max-delay delay-time decay-time)]
    (replace-out bus (pan2 (+ echo source) 0))))

(defsynth fx-my-reverb
  [bus 0
   roomsize 30.0
   revtime 4.50
   damping 0.40
   inputbw 0.39
   spread 14.93
   drylevel 0.25
   earlyreflevel 0.20
   taillevel 0.10
   lpf-freq 1000.0
   maxroomsize 300.0
   gate 1.0]
  (let [source (in bus)
        my-reverb (* gate
                     (g-verb (* gate source)
                             roomsize revtime damping inputbw spread
                             drylevel earlyreflevel taillevel maxroomsize))
        lpf-reverb (lpf my-reverb lpf-freq)]
    (replace-out bus (+ lpf-reverb source))))

(defn setup-irso-fx [inst]
  (clear-fx inst)
  (def fx1 (inst-fx! inst fx-my-reverb)))
