(ns irso.rhythm
  (:use [overtone.music time]
        [overtone.music.rhythm]))

;; A local copy of code from
;; https://github.com/rogerallen/explore_overtone/blob/master/src/explore_overtone/my_rhythm.clj
;; see that code for documentation, updates, etc.

(defn bpm2mspb
  "beats/min -> milliseconds/beat"
  [bpm]
  (* 1000 (/ 60.0 (java.lang.Math/abs bpm))))

;; (now) ticks are in ms, not s.
(defn bpm2bps
  "beats/min -> beats/millisecond"
  [bpm]
  (/ (java.lang.Math/abs bpm) 60.0))

(defn bps2bpm
  "beats/sec -> beats/min"
  [bps]
  (* bps 60.0))

(defn pwl-fn
  "piece-wise-linear function.  Given a list of [x0 y0 x1 y1 ... xN
  yN] control points, along with a current x value, solve for the y
  value & return it.  If x < x0, return y0.  If x > xN, return yN."
  [control-points x]
  (let [[xs0 xs1] (split-with #(<= % x) (take-nth 2 control-points))
        [ys0 ys1] (split-at (count xs0) (take-nth 2 (drop 1 control-points)))]
    (if (empty? xs0)
      (first ys1) ;; x < x0, return y0
      (if (empty? xs1)
        (last ys0) ;; x > xN, return yN
        (let [x0 (if (empty? xs0) (first xs1) (last xs0))
              x1 (if (empty? xs1) (last xs0) (first xs1))
              y0 (if (empty? ys0) (first ys1) (last ys0))
              y1 (if (empty? ys1) (last ys0) (first ys1))
              m (/ (- y1 y0) (- x1 x0))]
          (+ y0 (* m (- x x0))))))))

;; need help to determine y0-y1 range in this case
(defn lin-time2beat
  "returns the beat corresponding to current time t (in
seconds). y0,v0 and y1,v1 are beat,tempo(in beats/second) start & end
points."
  [y0 v0 y1 v1 t]
  (let [a v0
        b (/ (- v1 v0) (- y1 y0))
        y (+ y0 (* (/ a b) (- (Math/exp (* b t)) 1.0)))]
    y))

;; easy to determine y0-y1 range in this case
(defn lin-beat2time
  "returns the time (in seconds) corresponding to current beat y.
y0,v0 and y1,v1 are beat,tempo(in beats/second) start & end
points."
  [y0 v0 y1 v1 y]
  (let [a v0
        b (/ (- v1 v0) (- y1 y0))
        u (+ a (* b (- y y0)))
        t (/ (Math/log (/ u a)) b)]
    t))

(defn pwl-time2beat
  "Given a list of [x0 y0 x1 y1 ... xN yN] tempo control points, along
  with a current x (time) value, solve for the beat value & return it.
  If x < x0 or x > xN, return nil"
  [control-points x]
  ;; correllate control points with time first.  solve for all t
  ;; values & store in ts first.  FIXME-optimize & do this once.
  (let [ts (reductions + (conj (map #(apply lin-beat2time %)
                                    (map vector
                                         (take-nth 2 control-points)
                                         (take-nth 2 (drop 1 control-points))
                                         (take-nth 2 (drop 2 control-points))
                                         (take-nth 2 (drop 3 control-points))
                                         (take-nth 2 (drop 2 control-points)))) 0.0))
        [ts0 ts1] (split-with #(<= % x) ts)
        [xs0 xs1] (split-at (count ts0) (take-nth 2 control-points))
        [ys0 ys1] (split-at (count ts0) (take-nth 2 (drop 1 control-points)))
        ]
    (if (empty? xs0) nil ;; x < x0
        (if (empty? xs1) nil ;; x >= xN
            (let [x0 (last xs0)
                  x1 (first xs1)
                  y0 (last ys0)
                  y1 (first ys1)
                  t0 (last ts0)]
              (lin-time2beat x0 y0 x1 y1 (- x t0)))))))

(defn pwl-beat2time
  "Given a list of [x0 y0 x1 y1 ... xN yN] tempo control points, along
  with a current x (beat) value, solve for the time value & return it.
  If x < x0 or x > xN, return nil"
  [control-points x]
  (let [[xs0 xs1] (split-with #(<= % x) (take-nth 2 control-points))
        [ys0 ys1] (split-at (count xs0) (take-nth 2 (drop 1 control-points)))]
    (if (empty? xs0)
      nil ;; x < x0
      (if (empty? xs1)
        nil ;; x >= xN ;; FIXME -- special case x = xN
        (let [x0 (last xs0)
              x1 (first xs1)
              y0 (last ys0)
              y1 (first ys1)
              t0 (reduce + (map #(apply lin-beat2time %)
                                (map vector
                                     xs0
                                     ys0
                                     (drop 1 xs0)
                                     (drop 1 ys0)
                                     (drop 1 xs0))))]
          (+ t0 (lin-beat2time x0 y0 x1 y1 x)))))))


;; ======================================================================
;; Variable Rate Metronome #2 -- using more direct methods
;; ======================================================================
;; Add to the Metronome protocol
(defprotocol VRMetronome
  (vr-metro-beat [metro ticks]
    "convert (now) to a precise floating-point beat value.  Not a whole number.")
  (vr-metro-now-beat [metro]
    "convert (now) to a precise floating-point beat value.  Not a whole number.")
  (vr-metro-time [metro b]
    "convert beat b to a precise time value in milliseconds (ticks)."))

(deftype VariableRateMetronome2 [start beat2bps-fn time2beat-fn beat2time-fn]

  VRMetronome
  (vr-metro-beat [metro ticks]
    "convert ticks (in ms) to a precise floating-point beat value.  Not a whole number."
    (let [delta-ticks (- ticks @start)
          delta-seconds (* 0.001 delta-ticks)]
      (@time2beat-fn delta-seconds)))
  (vr-metro-now-beat [metro]
    "convert (now) to a precise beat value"
    (vr-metro-beat metro (now)))
  (vr-metro-time [metro b]
    "convert b to a precise time in ticks (ms)"
    (+ @start (* 1000 (@beat2time-fn b))))

  IMetronome
  (metro-start [metro] @start)
  (metro-start [metro start-beat]
    (let [new-start (- (now) (- (vr-metro-time metro start-beat) @start))]
      (reset! start new-start)
      new-start))
  (metro-tick  [metro] (bpm2mspb (bps2bpm (@beat2bps-fn (vr-metro-now-beat metro)))))
  (metro-beat  [metro] (inc (long (vr-metro-now-beat metro))))
  (metro-beat  [metro b] (vr-metro-time metro b))
  (metro-bpm   [metro] (bps2bpm (@beat2bps-fn (vr-metro-now-beat metro))))
  (metro-bpm   [metro bpm-control-points]
    (let [cur-beat (metro-beat metro)
          bps-control-points (apply vector (flatten (map vector
                                                         (take-nth 2 bpm-control-points)
                                                         (map bpm2bps
                                                              (take-nth 2 (drop 1 bpm-control-points))))))
          new-beat2bps-fn (partial pwl-fn bps-control-points)
          new-time2beat-fn (partial pwl-time2beat bps-control-points)
          new-beat2time-fn (partial pwl-beat2time bps-control-points)
          new-start (- (metro-beat metro cur-beat) (* 1000 (beat2time-fn cur-beat)))]
      (reset! start new-start)
      (reset! beat2bps-fn new-beat2bps-fn)
      (reset! time2beat-fn new-time2beat-fn)
      (reset! beat2time-fn new-beat2time-fn)
      nil)) ;; return is different from metronome.  okay?

  clojure.lang.ILookup
  (valAt [this key] (.valAt this key nil))
  (valAt [this key not-found]
    (cond (= key :start) @start
          (= key :bpm) (metro-bpm this)
          :else not-found))

  clojure.lang.IFn
  (invoke [this] (metro-beat this))
  (invoke [this arg]
    (cond
     (number? arg) (metro-beat this arg)
     (= :bpm arg) (metro-beat this)
     :else (throw (Exception. (str "Unsupported metronome arg: " arg)))))
  ;; I don't know what this is. ??? (invoke [this _ new-bpm] (.bpm this new-bpm)))
  )

(defn pwl-metronome 
  [bpm-control-points]
  (let [start (atom (now))
        bps-control-points (apply vector (flatten (map vector
                                                       (take-nth 2 bpm-control-points)
                                                       (map bpm2bps
                                                            (take-nth 2 (drop 1 bpm-control-points))))))
        beat2bps-fn (atom (partial pwl-fn bps-control-points))
        time2beat-fn (atom (partial pwl-time2beat bps-control-points))
        beat2time-fn (atom (partial pwl-beat2time bps-control-points))]
    (VariableRateMetronome2. start beat2bps-fn time2beat-fn beat2time-fn)))
