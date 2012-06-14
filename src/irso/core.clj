(ns irso.core
  (:use [overtone.core]))
;; hint (use :reload-all 'irso.core)

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

;; a variety of irrational numbers to 1000 digits...
(def pi-1000 '(3 1 4 1 5 9 2 6 5 3 5 8 9 7 9 3 2 3 8 4 6 2 6 4 3 3 8 3
   2 7 9 5 0 2 8 8 4 1 9 7 1 6 9 3 9 9 3 7 5 1 0 5 8 2 0 9 7 4 9 4 4 5
   9 2 3 0 7 8 1 6 4 0 6 2 8 6 2 0 8 9 9 8 6 2 8 0 3 4 8 2 5 3 4 2 1 1
   7 0 6 7 9 8 2 1 4 8 0 8 6 5 1 3 2 8 2 3 0 6 6 4 7 0 9 3 8 4 4 6 0 9
   5 5 0 5 8 2 2 3 1 7 2 5 3 5 9 4 0 8 1 2 8 4 8 1 1 1 7 4 5 0 2 8 4 1
   0 2 7 0 1 9 3 8 5 2 1 1 0 5 5 5 9 6 4 4 6 2 2 9 4 8 9 5 4 9 3 0 3 8
   1 9 6 4 4 2 8 8 1 0 9 7 5 6 6 5 9 3 3 4 4 6 1 2 8 4 7 5 6 4 8 2 3 3
   7 8 6 7 8 3 1 6 5 2 7 1 2 0 1 9 0 9 1 4 5 6 4 8 5 6 6 9 2 3 4 6 0 3
   4 8 6 1 0 4 5 4 3 2 6 6 4 8 2 1 3 3 9 3 6 0 7 2 6 0 2 4 9 1 4 1 2 7
   3 7 2 4 5 8 7 0 0 6 6 0 6 3 1 5 5 8 8 1 7 4 8 8 1 5 2 0 9 2 0 9 6 2
   8 2 9 2 5 4 0 9 1 7 1 5 3 6 4 3 6 7 8 9 2 5 9 0 3 6 0 0 1 1 3 3 0 5
   3 0 5 4 8 8 2 0 4 6 6 5 2 1 3 8 4 1 4 6 9 5 1 9 4 1 5 1 1 6 0 9 4 3
   3 0 5 7 2 7 0 3 6 5 7 5 9 5 9 1 9 5 3 0 9 2 1 8 6 1 1 7 3 8 1 9 3 2
   6 1 1 7 9 3 1 0 5 1 1 8 5 4 8 0 7 4 4 6 2 3 7 9 9 6 2 7 4 9 5 6 7 3
   5 1 8 8 5 7 5 2 7 2 4 8 9 1 2 2 7 9 3 8 1 8 3 0 1 1 9 4 9 1 2 9 8 3
   3 6 7 3 3 6 2 4 4 0 6 5 6 6 4 3 0 8 6 0 2 1 3 9 4 9 4 6 3 9 5 2 2 4
   7 3 7 1 9 0 7 0 2 1 7 9 8 6 0 9 4 3 7 0 2 7 7 0 5 3 9 2 1 7 1 7 6 2
   9 3 1 7 6 7 5 2 3 8 4 6 7 4 8 1 8 4 6 7 6 6 9 4 0 5 1 3 2 0 0 0 5 6
   8 1 2 7 1 4 5 2 6 3 5 6 0 8 2 7 7 8 5 7 7 1 3 4 2 7 5 7 7 8 9 6 0 9
   1 7 3 6 3 7 1 7 8 7 2 1 4 6 8 4 4 0 9 0 1 2 2 4 9 5 3 4 3 0 1 4 6 5
   4 9 5 8 5 3 7 1 0 5 0 7 9 2 2 7 9 6 8 9 2 5 8 9 2 3 5 4 2 0 1 9 9 5
   6 1 1 2 1 2 9 0 2 1 9 6 0 8 6 4 0 3 4 4 1 8 1 5 9 8 1 3 6 2 9 7 7 4
   7 7 1 3 0 9 9 6 0 5 1 8 7 0 7 2 1 1 3 4 9 9 9 9 9 9 8 3 7 2 9 7 8 0
   4 9 9 5 1 0 5 9 7 3 1 7 3 2 8 1 6 0 9 6 3 1 8 5 9 5 0 2 4 4 5 9 4 5
   5 3 4 6 9 0 8 3 0 2 6 4 2 5 2 2 3 0 8 2 5 3 3 4 4 6 8 5 0 3 5 2 6 1
   9 3 1 1 8 8 1 7 1 0 1 0 0 0 3 1 3 7 8 3 8 7 5 2 8 8 6 5 8 7 5 3 3 2
   0 8 3 8 1 4 2 0 6 1 7 1 7 7 6 6 9 1 4 7 3 0 3 5 9 8 2 5 3 4 9 0 4 2
   8 7 5 5 4 6 8 7 3 1 1 5 9 5 6 2 8 6 3 8 8 2 3 5 3 7 8 7 5 9 3 7 5 1
   9 5 7 7 8 1 8 5 7 7 8 0 5 3 2 1 7 1 2 2 6 8 0 6 6 1 3 0 0 1 9 2 7 8
   7 6 6 1 1 1 9 5 9 0 9 2 1 6 4 2 0 1 9 8 9))

(def tau-1000 '(6 2 8 3 1 8 5 3 0 7 1 7 9 5 8 6 4 7 6 9 2 5 2 8 6 7 6
  6 5 5 9 0 0 5 7 6 8 3 9 4 3 3 8 7 9 8 7 5 0 2 1 1 6 4 1 9 4 9 8 8 9
  1 8 4 6 1 5 6 3 2 8 1 2 5 7 2 4 1 7 9 9 7 2 5 6 0 6 9 6 5 0 6 8 4 2
  3 4 1 3 5 9 6 4 2 9 6 1 7 3 0 2 6 5 6 4 6 1 3 2 9 4 1 8 7 6 8 9 2 1
  9 1 0 1 1 6 4 4 6 3 4 5 0 7 1 8 8 1 6 2 5 6 9 6 2 2 3 4 9 0 0 5 6 8
  2 0 5 4 0 3 8 7 7 0 4 2 2 1 1 1 1 9 2 8 9 2 4 5 8 9 7 9 0 9 8 6 0 7
  6 3 9 2 8 8 5 7 6 2 1 9 5 1 3 3 1 8 6 6 8 9 2 2 5 6 9 5 1 2 9 6 4 6
  7 5 7 3 5 6 6 3 3 0 5 4 2 4 0 3 8 1 8 2 9 1 2 9 7 1 3 3 8 4 6 9 2 0
  6 9 7 2 2 0 9 0 8 6 5 3 2 9 6 4 2 6 7 8 7 2 1 4 5 2 0 4 9 8 2 8 2 5
  4 7 4 4 9 1 7 4 0 1 3 2 1 2 6 3 1 1 7 6 3 4 9 7 6 3 0 4 1 8 4 1 9 2
  5 6 5 8 5 0 8 1 8 3 4 3 0 7 2 8 7 3 5 7 8 5 1 8 0 7 2 0 0 2 2 6 6 1
  0 6 1 0 9 7 6 4 0 9 3 3 0 4 2 7 6 8 2 9 3 9 0 3 8 8 3 0 2 3 2 1 8 8
  6 6 1 1 4 5 4 0 7 3 1 5 1 9 1 8 3 9 0 6 1 8 4 3 7 2 2 3 4 7 6 3 8 6
  5 2 2 3 5 8 6 2 1 0 2 3 7 0 9 6 1 4 8 9 2 4 7 5 9 9 2 5 4 9 9 1 3 4
  7 0 3 7 7 1 5 0 5 4 4 9 7 8 2 4 5 5 8 7 6 3 6 6 0 2 3 8 9 8 2 5 9 6
  6 7 3 4 6 7 2 4 8 8 1 3 1 3 2 8 6 1 7 2 0 4 2 7 8 9 8 9 2 7 9 0 4 4
  9 4 7 4 3 8 1 4 0 4 3 5 9 7 2 1 8 8 7 4 0 5 5 4 1 0 7 8 4 3 4 3 5 2
  5 8 6 3 5 3 5 0 4 7 6 9 3 4 9 6 3 6 9 3 5 3 3 8 8 1 0 2 6 4 0 0 1 1
  3 6 2 5 4 2 9 0 5 2 7 1 2 1 6 5 5 5 7 1 5 4 2 6 8 5 5 1 5 5 7 9 2 1
  8 3 4 7 2 7 4 3 5 7 4 4 2 9 3 6 8 8 1 8 0 2 4 4 9 9 0 6 8 6 0 2 9 3
  0 9 9 1 7 0 7 4 2 1 0 1 5 8 4 5 5 9 3 7 8 5 1 7 8 4 7 0 8 4 0 3 9 9
  1 2 2 2 4 2 5 8 0 4 3 9 2 1 7 2 8 0 6 8 8 3 6 3 1 9 6 2 7 2 5 9 5 4
  9 5 4 2 6 1 9 9 2 1 0 3 7 4 1 4 4 2 2 6 9 9 9 9 9 9 9 6 7 4 5 9 5 6
  0 9 9 9 0 2 1 1 9 4 6 3 4 6 5 6 3 2 1 9 2 6 3 7 1 9 0 0 4 8 9 1 8 9
  1 0 6 9 3 8 1 6 6 0 5 2 8 5 0 4 4 6 1 6 5 0 6 6 8 9 3 7 0 0 7 0 5 2
  3 8 6 2 3 7 6 3 4 2 0 2 0 0 0 6 2 7 5 6 7 7 5 0 5 7 7 3 1 7 5 0 6 6
  4 1 6 7 6 2 8 4 1 2 3 4 3 5 5 3 3 8 2 9 4 6 0 7 1 9 6 5 0 6 9 8 0 8
  5 7 5 1 0 9 3 7 4 6 2 3 1 9 1 2 5 7 2 7 7 6 4 7 0 7 5 7 5 1 8 7 5 0
  3 9 1 5 5 6 3 7 1 5 5 6 1 0 6 4 3 4 2 4 5 3 6 1 3 2 2 6 0 0 3 8 5 5
  7 5 3 2 2 2 3 9 1 8 1 8 4 3 2 8 4 0 3 9 7 8))

(def sqrt2-1000 '(1 4 1 4 2 1 3 5 6 2 3 7 3 0 9 5 0 4 8 8 0 1 6 8 8 7
  2 4 2 0 9 6 9 8 0 7 8 5 6 9 6 7 1 8 7 5 3 7 6 9 4 8 0 7 3 1 7 6 6 7
  9 7 3 7 9 9 0 7 3 2 4 7 8 4 6 2 1 0 7 0 3 8 8 5 0 3 8 7 5 3 4 3 2 7
  6 4 1 5 7 2 7 3 5 0 1 3 8 4 6 2 3 0 9 1 2 2 9 7 0 2 4 9 2 4 8 3 6 0
  5 5 8 5 0 7 3 7 2 1 2 6 4 4 1 2 1 4 9 7 0 9 9 9 3 5 8 3 1 4 1 3 2 2
  2 6 6 5 9 2 7 5 0 5 5 9 2 7 5 5 7 9 9 9 5 0 5 0 1 1 5 2 7 8 2 0 6 0
  5 7 1 4 7 0 1 0 9 5 5 9 9 7 1 6 0 5 9 7 0 2 7 4 5 3 4 5 9 6 8 6 2 0
  1 4 7 2 8 5 1 7 4 1 8 6 4 0 8 8 9 1 9 8 6 0 9 5 5 2 3 2 9 2 3 0 4 8
  4 3 0 8 7 1 4 3 2 1 4 5 0 8 3 9 7 6 2 6 0 3 6 2 7 9 9 5 2 5 1 4 0 7
  9 8 9 6 8 7 2 5 3 3 9 6 5 4 6 3 3 1 8 0 8 8 2 9 6 4 0 6 2 0 6 1 5 2
  5 8 3 5 2 3 9 5 0 5 4 7 4 5 7 5 0 2 8 7 7 5 9 9 6 1 7 2 9 8 3 5 5 7
  5 2 2 0 3 3 7 5 3 1 8 5 7 0 1 1 3 5 4 3 7 4 6 0 3 4 0 8 4 9 8 8 4 7
  1 6 0 3 8 6 8 9 9 9 7 0 6 9 9 0 0 4 8 1 5 0 3 0 5 4 4 0 2 7 7 9 0 3
  1 6 4 5 4 2 4 7 8 2 3 0 6 8 4 9 2 9 3 6 9 1 8 6 2 1 5 8 0 5 7 8 4 6
  3 1 1 1 5 9 6 6 6 8 7 1 3 0 1 3 0 1 5 6 1 8 5 6 8 9 8 7 2 3 7 2 3 5
  2 8 8 5 0 9 2 6 4 8 6 1 2 4 9 4 9 7 7 1 5 4 2 1 8 3 3 4 2 0 4 2 8 5
  6 8 6 0 6 0 1 4 6 8 2 4 7 2 0 7 7 1 4 3 5 8 5 4 8 7 4 1 5 5 6 5 7 0
  6 9 6 7 7 6 5 3 7 2 0 2 2 6 4 8 5 4 4 7 0 1 5 8 5 8 8 0 1 6 2 0 7 5
  8 4 7 4 9 2 2 6 5 7 2 2 6 0 0 2 0 8 5 5 8 4 4 6 6 5 2 1 4 5 8 3 9 8
  8 9 3 9 4 4 3 7 0 9 2 6 5 9 1 8 0 0 3 1 1 3 8 8 2 4 6 4 6 8 1 5 7 0
  8 2 6 3 0 1 0 0 5 9 4 8 5 8 7 0 4 0 0 3 1 8 6 4 8 0 3 4 2 1 9 4 8 9
  7 2 7 8 2 9 0 6 4 1 0 4 5 0 7 2 6 3 6 8 8 1 3 1 3 7 3 9 8 5 5 2 5 6
  1 1 7 3 2 2 0 4 0 2 4 5 0 9 1 2 2 7 7 0 0 2 2 6 9 4 1 1 2 7 5 7 3 6
  2 7 2 8 0 4 9 5 7 3 8 1 0 8 9 6 7 5 0 4 0 1 8 3 6 9 8 6 8 3 6 8 4 5
  0 7 2 5 7 9 9 3 6 4 7 2 9 0 6 0 7 6 2 9 9 6 9 4 1 3 8 0 4 7 5 6 5 4
  8 2 3 7 2 8 9 9 7 1 8 0 3 2 6 8 0 2 4 7 4 4 2 0 6 2 9 2 6 9 1 2 4 8
  5 9 0 5 2 1 8 1 0 0 4 4 5 9 8 4 2 1 5 0 5 9 1 1 2 0 2 4 9 4 4 1 3 4
  1 7 2 8 5 3 1 4 7 8 1 0 5 8 0 3 6 0 3 3 7 1 0 7 7 3 0 9 1 8 2 8 6 9
  3 1 4 7 1 0 1 7 1 1 1 1 6 8 3 9 1 6 5 8 1 7 2 6 8 8 9 4 1 9 7 5 8 7
  1 6 5 8 2 1 5 2 1 2 8 2 2 9 5 1 8 4 8 8 4 7))

(def sqrt3-1000 '(1 7 3 2 0 5 0 8 0 7 5 6 8 8 7 7 2 9 3 5 2 7 4 4 6 3
  4 1 5 0 5 8 7 2 3 6 6 9 4 2 8 0 5 2 5 3 8 1 0 3 8 0 6 2 8 0 5 5 8 0
  6 9 7 9 4 5 1 9 3 3 0 1 6 9 0 8 8 0 0 0 3 7 0 8 1 1 4 6 1 8 6 7 5 7
  2 4 8 5 7 5 6 7 5 6 2 6 1 4 1 4 1 5 4 0 6 7 0 3 0 2 9 9 6 9 9 4 5 0
  9 4 9 9 8 9 5 2 4 7 8 8 1 1 6 5 5 5 1 2 0 9 4 3 7 3 6 4 8 5 2 8 0 9
  3 2 3 1 9 0 2 3 0 5 5 8 2 0 6 7 9 7 4 8 2 0 1 0 1 0 8 4 6 7 4 9 2 3
  2 6 5 0 1 5 3 1 2 3 4 3 2 6 6 9 0 3 3 2 2 8 8 6 6 5 0 6 7 2 2 5 4 6
  6 8 9 2 1 8 3 7 9 7 1 2 2 7 0 4 7 1 3 1 6 6 0 3 6 7 8 6 1 5 8 8 0 1
  9 0 4 9 9 8 6 5 3 7 3 7 9 8 5 9 3 8 9 4 6 7 6 5 0 3 4 7 5 0 6 5 7 6
  0 5 0 7 5 6 6 1 8 3 4 8 1 2 9 6 0 6 1 0 0 9 4 7 6 0 2 1 8 7 1 9 0 3
  2 5 0 8 3 1 4 5 8 2 9 5 2 3 9 5 9 8 3 2 9 9 7 7 8 9 8 2 4 5 0 8 2 8
  8 7 1 4 4 6 3 8 3 2 9 1 7 3 4 7 2 2 4 1 6 3 9 8 4 5 8 7 8 5 5 3 9 7
  6 6 7 9 5 8 0 6 3 8 1 8 3 5 3 6 6 6 1 1 0 8 4 3 1 7 3 7 8 0 8 9 4 3
  7 8 3 1 6 1 0 2 0 8 8 3 0 5 5 2 4 9 0 1 6 7 0 0 2 3 5 2 0 7 1 1 1 4
  4 2 8 8 6 9 5 9 9 0 9 5 6 3 6 5 7 9 7 0 8 7 1 6 8 4 9 8 0 7 2 8 9 9
  4 9 3 2 9 6 4 8 4 2 8 3 0 2 0 7 8 6 4 0 8 6 0 3 9 8 8 7 3 8 6 9 7 5
  3 7 5 8 2 3 1 7 3 1 7 8 3 1 3 9 5 9 9 2 9 8 3 0 0 7 8 3 8 7 0 2 8 7
  7 0 5 3 9 1 3 3 6 9 5 6 3 3 1 2 1 0 3 7 0 7 2 6 4 0 1 9 2 4 9 1 0 6
  7 6 8 2 3 1 1 9 9 2 8 8 3 7 5 6 4 1 1 4 1 4 2 2 0 1 6 7 4 2 7 5 2 1
  0 2 3 7 2 9 9 4 2 7 0 8 3 1 0 5 9 8 9 8 4 5 9 4 7 5 9 8 7 6 6 4 2 8
  8 8 9 7 7 9 6 1 4 7 8 3 7 9 5 8 3 9 0 2 2 8 8 5 4 8 5 2 9 0 3 5 7 6
  0 3 3 8 5 2 8 0 8 0 6 4 3 8 1 9 7 2 3 4 4 6 6 1 0 5 9 6 8 9 7 2 2 8
  7 2 8 6 5 2 6 4 1 5 3 8 2 2 6 6 4 6 9 8 4 2 0 0 2 1 1 9 5 4 8 4 1 5
  5 2 7 8 4 4 1 1 8 1 2 8 6 5 3 4 5 0 7 0 3 5 1 9 1 6 5 0 0 1 6 6 8 9
  2 9 4 4 1 5 4 8 0 8 4 6 0 7 1 2 7 7 1 4 3 9 9 9 7 6 2 9 2 6 8 3 4 6
  2 9 5 7 7 4 3 8 3 6 1 8 9 5 1 1 0 1 2 7 1 4 8 6 3 8 7 4 6 9 7 6 5 4
  5 9 8 2 4 5 1 7 8 8 5 5 0 9 7 5 3 7 9 0 1 3 8 8 0 6 6 4 9 6 1 9 1 1
  9 6 2 2 2 2 9 5 7 1 1 0 5 5 5 2 4 2 9 2 3 7 2 3 1 9 2 1 9 7 7 3 8 2
  6 2 5 6 1 6 3 1 4 6 8 8 4 2 0 3 2 8 5 3 7 1 6 6 8 2 9 3 8 6 4 9 6 1
  1 9 1 7 0 4 9 7 3 8 8 3 6 3 9 5 4 9 5 9 3 8))

(def e-1000 '(2 7 1 8 2 8 1 8 2 8 4 5 9 0 4 5 2 3 5 3 6 0 2 8 7 4 7 1
  3 5 2 6 6 2 4 9 7 7 5 7 2 4 7 0 9 3 6 9 9 9 5 9 5 7 4 9 6 6 9 6 7 6
  2 7 7 2 4 0 7 6 6 3 0 3 5 3 5 4 7 5 9 4 5 7 1 3 8 2 1 7 8 5 2 5 1 6
  6 4 2 7 4 2 7 4 6 6 3 9 1 9 3 2 0 0 3 0 5 9 9 2 1 8 1 7 4 1 3 5 9 6
  6 2 9 0 4 3 5 7 2 9 0 0 3 3 4 2 9 5 2 6 0 5 9 5 6 3 0 7 3 8 1 3 2 3
  2 8 6 2 7 9 4 3 4 9 0 7 6 3 2 3 3 8 2 9 8 8 0 7 5 3 1 9 5 2 5 1 0 1
  9 0 1 1 5 7 3 8 3 4 1 8 7 9 3 0 7 0 2 1 5 4 0 8 9 1 4 9 9 3 4 8 8 4
  1 6 7 5 0 9 2 4 4 7 6 1 4 6 0 6 6 8 0 8 2 2 6 4 8 0 0 1 6 8 4 7 7 4
  1 1 8 5 3 7 4 2 3 4 5 4 4 2 4 3 7 1 0 7 5 3 9 0 7 7 7 4 4 9 9 2 0 6
  9 5 5 1 7 0 2 7 6 1 8 3 8 6 0 6 2 6 1 3 3 1 3 8 4 5 8 3 0 0 0 7 5 2
  0 4 4 9 3 3 8 2 6 5 6 0 2 9 7 6 0 6 7 3 7 1 1 3 2 0 0 7 0 9 3 2 8 7
  0 9 1 2 7 4 4 3 7 4 7 0 4 7 2 3 0 6 9 6 9 7 7 2 0 9 3 1 0 1 4 1 6 9
  2 8 3 6 8 1 9 0 2 5 5 1 5 1 0 8 6 5 7 4 6 3 7 7 2 1 1 1 2 5 2 3 8 9
  7 8 4 4 2 5 0 5 6 9 5 3 6 9 6 7 7 0 7 8 5 4 4 9 9 6 9 9 6 7 9 4 6 8
  6 4 4 5 4 9 0 5 9 8 7 9 3 1 6 3 6 8 8 9 2 3 0 0 9 8 7 9 3 1 2 7 7 3
  6 1 7 8 2 1 5 4 2 4 9 9 9 2 2 9 5 7 6 3 5 1 4 8 2 2 0 8 2 6 9 8 9 5
  1 9 3 6 6 8 0 3 3 1 8 2 5 2 8 8 6 9 3 9 8 4 9 6 4 6 5 1 0 5 8 2 0 9
  3 9 2 3 9 8 2 9 4 8 8 7 9 3 3 2 0 3 6 2 5 0 9 4 4 3 1 1 7 3 0 1 2 3
  8 1 9 7 0 6 8 4 1 6 1 4 0 3 9 7 0 1 9 8 3 7 6 7 9 3 2 0 6 8 3 2 8 2
  3 7 6 4 6 4 8 0 4 2 9 5 3 1 1 8 0 2 3 2 8 7 8 2 5 0 9 8 1 9 4 5 5 8
  1 5 3 0 1 7 5 6 7 1 7 3 6 1 3 3 2 0 6 9 8 1 1 2 5 0 9 9 6 1 8 1 8 8
  1 5 9 3 0 4 1 6 9 0 3 5 1 5 9 8 8 8 8 5 1 9 3 4 5 8 0 7 2 7 3 8 6 6
  7 3 8 5 8 9 4 2 2 8 7 9 2 2 8 4 9 9 8 9 2 0 8 6 8 0 5 8 2 5 7 4 9 2
  7 9 6 1 0 4 8 4 1 9 8 4 4 4 3 6 3 4 6 3 2 4 4 9 6 8 4 8 7 5 6 0 2 3
  3 6 2 4 8 2 7 0 4 1 9 7 8 6 2 3 2 0 9 0 0 2 1 6 0 9 9 0 2 3 5 3 0 4
  3 6 9 9 4 1 8 4 9 1 4 6 3 1 4 0 9 3 4 3 1 7 3 8 1 4 3 6 4 0 5 4 6 2
  5 3 1 5 2 0 9 6 1 8 3 6 9 0 8 8 8 7 0 7 0 1 6 7 6 8 3 9 6 4 2 4 3 7
  8 1 4 0 5 9 2 7 1 4 5 6 3 5 4 9 0 6 1 3 0 3 1 0 7 2 0 8 5 1 0 3 8 3
  7 5 0 5 1 0 1 1 5 7 4 7 7 0 4 1 7 1 8 9 8 6 1 0 6 8 7 3 9 6 9 6 5 5
  2 1 2 6 7 1 5 4 6 8 8 9 5 7 0 3 5 0 3 5))

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
  (+ 80 (* 3 index)))

(defn velocity2attack
  "sampled-piano uses attack & level, not velocity"
  [v]
  (linear-map 0 127 0.2 0.05 v))

(defn velocity2level
  "sampled-piano uses attack & level, not velocity"
  [v]
  (linear-map 0 127 0.0 0.8 v))

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
  "play a list of (pitch velocity duration curbeat) where snote-seq is offset by beat"
  [inst m beat lazy-snote-seq]
  (last ; return beat following sequence
   (let [snote-seq (doall lazy-snote-seq)] ;; remove laziness here
     (for [cur-snote snote-seq]
       (let [cur-pitch (:pitch cur-snote)
             cur-attack (velocity2attack (:velocity cur-snote))
             cur-level (velocity2level (:velocity cur-snote))
             cur-dur (:duration cur-snote)
             cur-beat (+ beat (:beat cur-snote))
             k-beat 1.6]
         ;;(println "note-on:" beat cur-beat cur-pitch cur-snote)
         ;; FIXME (def pk ...) is not local to this routine.  
         (at (m cur-beat) (def pk (inst :note cur-pitch
                                        :level cur-level
                                        :attack cur-attack)))
         ;;(println "note-off:" (+ cur-beat (* k-beat cur-dur)))
         (at (m (+ cur-beat (* k-beat cur-dur))) (ctl pk :gate 0))
         (+ cur-beat cur-dur))))))
  
(defn play-seqs
  "play a list of snote-seq"
  [inst m beat snote-seqs]
  (last
   (for [snote-seq snote-seqs]
     (play-seq inst m beat snote-seq))))

(defn draw-seqs [snote-seqs]
  "Draw the sequences in snote-seqs"
  (let [draw-w 1200
        seq-w draw-w
        seq-h 50
        seq-space 10
        seq-space2 4
        draw-h (- (* (+ seq-h seq-space2) (count snote-seqs)) seq-space2)
        w (+ (* 2 seq-space) draw-w)
        h (+ (* 2 seq-space) draw-h)
        max-seq-beats (reduce max (map max-beat snote-seqs))
        ]
    (doto (javax.swing.JFrame. "draw-seqs")
      (.setContentPane 
       (doto (proxy [javax.swing.JPanel] []
               (paintComponent [^java.awt.Graphics g]
                 (let [g (doto ^java.awt.Graphics2D (.create g)
                               ;;(.scale 1.0 1.0)
                               ;;(.translate 5.0 5.0)
                               (.setStroke (java.awt.BasicStroke. 2.0)))
                       ]
                   (.setColor g (java.awt.Color. 0x93 0xa1 0xa1))
                   (.fillRect g 0 0 w h)
                   (.setColor g (java.awt.Color. 0x65 0x7b 0x83))
                   (.fillRoundRect g seq-space seq-space draw-w draw-h 9 9)
                   (doseq [[i [cur-min-beat cur-num-beats cur-seq]]
                           (map-indexed vector
                                        (map vector
                                             (map min-beat snote-seqs)
                                             (map num-beats snote-seqs)
                                             snote-seqs))]
                     ;;(println "aaa" i cur-num-beats)
                     (let [x0 (+ seq-space (* seq-w (/ cur-min-beat max-seq-beats)))
                           x1 (* seq-w (/ cur-num-beats max-seq-beats))
                           y0 (+ seq-space (* (+ seq-h seq-space2) i))
                           y1 seq-h]
                       ;;(println i "drawRect" x0 y0 x1 y1)
                       (.setStroke g (java.awt.BasicStroke. 1.4))
                       (.setColor g (java.awt.Color. 0xee 0xe8 0xd5))
                       (.fillRoundRect g x0 y0 x1 y1 9 9)
                       (.setColor g (java.awt.Color. 0x07 0x36 0x42))
                       (.drawRoundRect g x0 y0 x1 y1 9 9)
                       (doseq [snote cur-seq]
                         (let [nx0 (+ seq-space
                                      (* seq-w (/ (:beat snote) max-seq-beats)))
                               nx1 (+ seq-space
                                      (* seq-w (/ (+ (:beat snote) (:duration snote))
                                                  max-seq-beats)))
                               ny (- (+ y0 y1)
                                     (* seq-h (/ (:pitch snote) 127)))]
                           (.setColor g (nth (list
                                              (java.awt.Color. 0xb5 0x89 0x00)
                                              (java.awt.Color. 0xcb 0x4b 0x16)
                                              (java.awt.Color. 0xdc 0x32 0x2f)
                                              (java.awt.Color. 0xd3 0x36 0x82)
                                              (java.awt.Color. 0x6c 0x71 0xc4)
                                              (java.awt.Color. 0x26 0x8b 0xd2)
                                              (java.awt.Color. 0x2a 0xa1 0x98)
                                              (java.awt.Color. 0x85 0x99 0x00))
                                             (mod i 8)))
                           (.drawLine g nx0 ny nx1 ny)))
                       ))
                   (.setStroke g (java.awt.BasicStroke. 2.0))
                   (.setColor g (java.awt.Color. 0x07 0x36 0x42))
                   (.drawRoundRect g seq-space seq-space draw-w draw-h 9 9)
                   )))
         (.setPreferredSize (java.awt.Dimension. w h))))
      .pack
      (.setVisible true))))
  
