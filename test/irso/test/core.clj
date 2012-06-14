(ns irso.test.core
  (:use [irso.core])
  (:use [clojure.test]))

(deftest test-calc-seq
  (are [tonic type len irso result] (= result (calc-seq tonic type len irso))
       :c4 :pentatonic 5 e-1000 '({:pitch 65, :duration 0.5, :velocity 86, :beat 0}
                                  {:pitch 77, :duration 0.5, :velocity 80, :beat 0.5}
                                  {:pitch 62, :duration 2.0, :velocity 92, :beat 1.0}
                                  {:pitch 79, :duration 0.5, :velocity 92, :beat 3.0}
                                  {:pitch 65, :duration 0.75, :velocity 107, :beat 3.5}
                                  {:pitch 79, :duration 0.75, :velocity 89, :beat 4.25})
       ;; FIXME add more 
       ))

(deftest test-offset-seq
  (are [offset in-seq result] (= result (offset-seq offset in-seq))
       ;; test 1 inputs
       4 '({:pitch 1, :duration 1.0, :velocity 4, :beat 0.0}
           {:pitch 2, :duration 0.5, :velocity 5, :beat 1.0}
           {:pitch 3, :duration 2.0, :velocity 6, :beat 1.5})
       ;; test 1 output
       '({:pitch 1, :duration 1.0, :velocity 4, :beat 4.0}
         {:pitch 2, :duration 0.5, :velocity 5, :beat 5.0}
         {:pitch 3, :duration 2.0, :velocity 6, :beat 5.5})
       ;; test 2 inputs
       4 '({:pitch 1, :duration 1.0, :velocity 4, :beat 0.0}
           {:pitch 2, :duration 0.5, :velocity 5, :beat 5.0}
           {:pitch 3, :duration 2.0, :velocity 6, :beat 10.5})
       ;; test 1 output
       '({:pitch 1, :duration 1.0, :velocity 4, :beat 4.0}
         {:pitch 2, :duration 0.5, :velocity 5, :beat 9.0}
         {:pitch 3, :duration 2.0, :velocity 6, :beat 14.5})
       ))

(deftest test-num-beats
  (are [in-seq result] (= result (num-beats in-seq))
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5})
       3.5
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 4.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 5.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 5.5})
       (- 7.5 4.0)
       ))

(deftest test-min-beat
  (are [in-seq result] (= result (min-beat in-seq))
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5})
       0.0
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 4.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 5.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 5.5})
       4.0 
       ))

(deftest test-max-beat
  (are [in-seq result] (= result (max-beat in-seq))
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5})
       3.5
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 4.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 5.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 5.5})
       7.5 
       ))

(deftest test-concat-seq
  (are [in-seq0 in-seq1 in-seq2 result] (= result (concat-seq in-seq0 in-seq1 in-seq2))
       ;; test 1 input
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5})
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5})
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5})
       ;; test 1 result
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5}
         {:pitch 0, :duration 1.0, :velocity 0, :beat 3.5}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 4.5}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 5.0}
         {:pitch 0, :duration 1.0, :velocity 0, :beat 7.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 8.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 8.5})
       ;; test 2 input
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 2.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 3.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 3.5})
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5})
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5})
       ;; test 2 result
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 2.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 3.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 3.5}
         {:pitch 0, :duration 1.0, :velocity 0, :beat 5.5}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 6.5}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 7.0}
         {:pitch 0, :duration 1.0, :velocity 0, :beat 9.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 10.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 10.5})
       ;; test 3 input
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5})
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 2.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 3.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 3.5})
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5})
       ;; test 3 result
       '({:pitch 0, :duration 1.0, :velocity 0, :beat 0.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 1.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 1.5}
         {:pitch 0, :duration 1.0, :velocity 0, :beat 5.5}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 6.5}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 7.0}
         {:pitch 0, :duration 1.0, :velocity 0, :beat 9.0}
         {:pitch 0, :duration 0.5, :velocity 0, :beat 10.0}
         {:pitch 0, :duration 2.0, :velocity 0, :beat 10.5})
       ))

(deftest test-concat-seq2
  (is (= (concat-seq (calc-seq :c4 :pentatonic 3 e-1000)
                     (offset-seq 3 (calc-seq :c4 :pentatonic 3 e-1000))
                     (calc-seq :c4 :pentatonic 3 e-1000))
         '({:pitch 65, :duration 0.5, :velocity 86, :beat 0}
           {:pitch 77, :duration 0.5, :velocity 80, :beat 0.5}
           {:pitch 62, :duration 2.0, :velocity 92, :beat 1.0}
           {:pitch 65, :duration 0.5, :velocity 86, :beat 6.0}
           {:pitch 77, :duration 0.5, :velocity 80, :beat 6.5}
           {:pitch 62, :duration 2.0, :velocity 92, :beat 7.0}
           {:pitch 65, :duration 0.5, :velocity 86, :beat 9.0}
           {:pitch 77, :duration 0.5, :velocity 80, :beat 9.5}
           {:pitch 62, :duration 2.0, :velocity 92, :beat 10.0}))))

(deftest test-irno-repeat
  (is (= (calc-seq-irno-repeat (calc-seq :c3 :pentatonic 3 e-1000) 2 '(0 1 2 4))
         '({:pitch 53, :duration 0.5, :velocity 86, :beat 0.0}
           {:pitch 65, :duration 0.5, :velocity 80, :beat 0.5}
           {:pitch 50, :duration 2.0, :velocity 92, :beat 1.0}
           {:pitch 53, :duration 0.5, :velocity 86, :beat 9.0}
           {:pitch 65, :duration 0.5, :velocity 80, :beat 9.5}
           {:pitch 50, :duration 2.0, :velocity 92, :beat 10.0}
           {:pitch 53, :duration 0.5, :velocity 86, :beat 12.0}
           {:pitch 65, :duration 0.5, :velocity 80, :beat 12.5}
           {:pitch 50, :duration 2.0, :velocity 92, :beat 13.0}
           {:pitch 53, :duration 0.5, :velocity 86, :beat 15.0}
           {:pitch 65, :duration 0.5, :velocity 80, :beat 15.5}
           {:pitch 50, :duration 2.0, :velocity 92, :beat 16.0}))))

(deftest test-concat-irno-repeat
  (is (= (concat-seq
          (calc-seq :c4 :pentatonic 3 e-1000)
          (calc-seq-irno-repeat (calc-seq :c3 :pentatonic 3 e-1000) 2 '(0 1 2 4)))
         '({:pitch 65, :duration 0.5, :velocity 86, :beat 0}
           {:pitch 77, :duration 0.5, :velocity 80, :beat 0.5}
           {:pitch 62, :duration 2.0, :velocity 92, :beat 1.0}
           {:pitch 53, :duration 0.5, :velocity 86, :beat 3.0}
           {:pitch 65, :duration 0.5, :velocity 80, :beat 3.5}
           {:pitch 50, :duration 2.0, :velocity 92, :beat 4.0}
           {:pitch 53, :duration 0.5, :velocity 86, :beat 12.0}
           {:pitch 65, :duration 0.5, :velocity 80, :beat 12.5}
           {:pitch 50, :duration 2.0, :velocity 92, :beat 13.0}
           {:pitch 53, :duration 0.5, :velocity 86, :beat 15.0}
           {:pitch 65, :duration 0.5, :velocity 80, :beat 15.5}
           {:pitch 50, :duration 2.0, :velocity 92, :beat 16.0}
           {:pitch 53, :duration 0.5, :velocity 86, :beat 18.0}
           {:pitch 65, :duration 0.5, :velocity 80, :beat 18.5}
           {:pitch 50, :duration 2.0, :velocity 92, :beat 19.0}))))
      
