(ns v3.tic-tac-toe-game
  (:require
    [speclj.core :refer :all]
    ))

(def empty-square 0)

(defn generate-all-moves
  ([board player]
    (generate-all-moves board (- (count board) 1) player []))

  ([board index player moves]

    (if (< index 0)
      moves
      (generate-all-moves board (- index  1) player
        (if (= empty-square (board index))
          (cons (assoc board index player) moves)
          moves))))

  )

(defn filled? [board]
  (every? (partial not= empty-square) board))

(defn player-controls-win-line? [board player win-line]
  (every? (partial = player)  (map (partial nth board) win-line))
  )

(defn win?
  [win-lines player board]
  (seq (filter (partial player-controls-win-line? board player) win-lines)))



(describe 'filled?
  (it "is false when there's at least one empty square"
    (should-not (filled? [1 empty-square 1])))

  (it "is true when there's no empty square"
    (should (filled? [ 1 ])))

  (it "is true when given an empty board"
    (should (filled? [])))
  )

(describe 'win?
  (it "returns true if there's a win line"
    (should      (win? [[0 1 2]] 1 [1 1 1])))

  (it "returns false if there's no win line"
    (should-not
      (win? [[0 1 2]] 1 [1 2 1])))

  (it "checks all win lines"
    (should
      (win?  [[0 1 2] [2 3 4]] 1 [1 2 1 1 1])))
  )


(describe 'generate-all-moves
  (it "makes a move"
    (should= [ [1] ]
      (generate-all-moves [0] 1)))

  (it "returns empty list if there are no moves"
    (should= []
      (generate-all-moves [1] 1)))

  (it "does not overwrite moves "
    (should= []
      (generate-all-moves [2] 1)))

  (it "makes moves in size 2 board"
    (should= [ [1 1] ]
      (generate-all-moves [0 1] 1)))

  (it "finds moves that are deeper"
    (should= [ [1 1] ]
      (generate-all-moves [1 0] 1)))

  (it "finds all the moves"
    (should-contain [1 0]
      (generate-all-moves [0 0] 1))
    (should-contain [0 1]
      (generate-all-moves [0 0] 1)))
  )




(run-specs)