(ns JaviIntersect)

(defn intersect [lizt1 lizt2]
	(cond
		(empty? lizt1) '()

		(some #(= (first lizt1) %) lizt2)
		(cons (first lizt1) (intersect (rest lizt1) lizt2))

		:else
		(intersect (rest lizt1) lizt2)))