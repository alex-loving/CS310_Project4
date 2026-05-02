(ns Ethan)

;; Author: Ethan
(defn member
  ([check arr]
   (member check arr 0))     ;; starting index
  ([check arr index]
   (if (>= index (count arr))
     false
     (if (= (nth arr index) check)
       true
       (recur check arr (inc index))))))

(defn append [lizt1 lizt2]
  (if (empty? lizt1)
    lizt2
    (cons (first lizt1)
          (append (rest lizt1) lizt2))))