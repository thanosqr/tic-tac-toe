(ns v3.main
  (:require
    [speclj.core :refer :all]
    [v3.best-strategy :refer :all]
    [v3.tic-tac-toe-game :refer :all]
    ))

(def tic-tac-toc-win-lines [
    ; horizontal lines
    [0 1 2]
    [3 4 5]
    [6 7 8]
    ; vertical lones
    [0 3 6]
    [1 4 7]
    [2 5 8]
    ; diagonals
    [0 4 8]
    [2 4 6]])

(def tic-tac-toc-inital-board [ 
  0 0 0
  0 0 0
  0 0 0])



(defn run [player1 player2 board win? filled?]
  (if (win? player2 board)
    (player2 :id)
    (if (filled? board)
      0
      (let [new-board ((player1 :strategy) board)]
        (run player2 player1 new-board win? filled?)))
    ))

(defn run-t3 []
  (run make-a-move make-a-move tic-tac-toc-inital-board (partial win? tic-tac-toc-win-lines)))

(describe 'run
  (it "should terminate if a player won"
    (should= 2
      (run
        {:id 1 :strategy identity}
        {:id 2 :strategy identity}
        :win
        (fn [_ board] (= board :win))
        (partial = :full)
        )))

  (it "should use the first players strategy"
    (should= 1
      (run
        {:id 1 :strategy (fn [_] :win)}
        {:id 2 :strategy identity}
        :start
        (fn [_ board] (= board :win))
        (partial = :full)
        )))

  (it "should let the players take turns"
    (should= 2
      (run
        {:id 1 :strategy (fn [_] :next)}
        {:id 2 :strategy (fn [x] (assert (= x :next)) :win)}
        :start
        (fn [_ board] (= board :win))
        (partial = :full)
        )))

  (it "should stop if the board is full"
    (should= 0
      (run
        {:id 1 :strategy (fn [_] :full)}
        {:id 2 :strategy identity}
        :start
        (fn [_ board] (= board :win))
        (partial = :full)
        )))

  (it "return the winner if there's a win, even if the board is filled"
    (should= 2
      (run
        {:id 1 :strategy identity}
        {:id 2 :strategy identity}
        :win
        (fn [_ board] (= board :win))
        (partial = :win)
        )))

  )





(run-specs)