(ns Javier)

(defn same [lizt1 lizt2]
  (cond
    ;; both lists empty at same time
    (empty? lizt1) (empty? lizt2)

    ;; only one empty → not equal
    (empty? lizt2) false

    ;; compare first elements
    (= (first lizt1) (first lizt2))
    (same (rest lizt1) (rest lizt2))

    ;; mismatch case
    :else false))

(defn intersect [lizt1 lizt2]
  (cond
    ;; base case
    (empty? lizt1) '()

    ;; if first element exists in lizt2
    (some #(= % (first lizt1)) lizt2)
    (cons (first lizt1)
          (intersect (rest lizt1) lizt2))

    ;; otherwise skip it
    :else
    (intersect (rest lizt1) lizt2)))