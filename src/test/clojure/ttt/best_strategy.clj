(ns v3.best-strategy
  (:require
    [speclj.core :refer :all]
    ))

(def win 0)
(def draw 1)
(def loss 2)

(defn make-a-move [state win? generate-moves rank-move]
  (let [available-moves (generate-moves state)
        winning-moves (filter win? available-moves)]
    (assert (not-empty available-moves))
    (if (empty? winning-moves)
      (first (sort-by second (map (partial rank-move win? generate-moves rank-move) available-moves)))
      [(first winning-moves) win])
    )
  )

(defn rank-move [win? generate-moves rank-move state]
  [state (second (make-a-move state win? generate-moves rank-move))])

(defn mock-rank-moves [win? generate-moves rank-move state]
  (if (= state :future-win) [:future-win win] [state loss]))

(defn mock-win? [x]
  (= x :win))

(defn mock-generate-moves [x]
  (case x
    :no-moves []
    :one-move [:a]
    :with-winning-move [:a :win]
    :with-future-win [:a :future-win])
  )

(describe 'make-a-move
  (it "throws if there's no move"
    (should-throw
      (make-a-move :no-move mock-win? mock-generate-moves mock-rank-moves)))

  (it "picks the only move available"
    (should= :a
      (first (make-a-move  :one-move mock-win? mock-generate-moves mock-rank-moves))))

  (it "picks the winning move"
    (should= [:win win]
      (make-a-move :with-winning-move mock-win? mock-generate-moves mock-rank-moves)))

  (it "picks the worst move for the opponent"
    (should= [:future-win win]
      (make-a-move :with-future-win mock-win? mock-generate-moves mock-rank-moves)))

  (it "test"
    (should= [:future-win win]
      (make-a-move
        :start
        mock-win?
        (fn [x] (case x
                  :start [:future-win :loss]
                  :loss [:player2-future-win]
                  :player2-future-win [:win]
                  :future-win [:win]))
        rank-move)))

  )





(run-specs)