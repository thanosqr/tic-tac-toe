(ns v3.main
  (:require
    [speclj.core :refer :all]
    [v3.best-strategy :refer :all]
    [v3.tic-tac-toe-game :refer :all]
    [v3.main :refer :all]
    ))



(describe "end to end"
  (it "should win against first move"
    (should= 1 1)
    ))




(run-specs)