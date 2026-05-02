(ns Alex)

;; Alex made these functions

(defn map [fun lizt]
	;;base case & guard
    (if (empty? lizt)'()

    ;;constructs list w/ fun applied to first in list
    (cons (fun (first lizt))
    (
        ;;recursive call that essentially goes through the list
        map fun (rest lizt)))
    )
)


(defn same [lizt1 lizt2]
    (cond
    ;;empty list + unequal length guard
    (empty? lizt1)(empty? lizt2)(empty? lizt2)false

    ;;checks if first element is ==
    (= (first lizt1)(first lizt2))

    ;;recursive call to method to check rest of list
    (same (rest lizt1)(rest lizt2))


    :else false)
)