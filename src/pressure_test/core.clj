(ns pressure-test.core
  (:require [pandect.algo.sha1 :refer [sha1]]
            [pandect.algo.md5 :refer [md5]]
            [clojure.data.json :as json]))

(def test-dir (file-seq (clojure.java.io/file ".")))

(defn parse-filestring-to-k
  ""
  [file-string]
  (filter #(not= % ".") (clojure.string/split file-string #"\/")))

(defn list-directory
  "Given a directory, list all the files in it. Returns a hash-map."
  [directory]
  )


(defn process-file
  ""
  [current-map in-file]
  (if (.isFile in-file)
    (let [md5 (digest/md5 in-file)
          sha1 (digest/sha1 in-file)
          path-component-seq (parse-filestring-to-k (.getPath in-file))]
      (assoc-in current-map path-component-seq {:md5 md5 :sha1 sha1}))
    current-map))

(defn main
  ""
  [in-directory]
  (let [dir-seq (file-seq in-directory)]
    (json/write-str (first (vals (reduce process-file {} dir-seq))))))
