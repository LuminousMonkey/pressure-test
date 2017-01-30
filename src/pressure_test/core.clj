(ns pressure-test.core
  (:require [pandect.algo.sha1 :refer [sha1]]
            [pandect.algo.md5 :refer [md5]]
            [clojure.data.json :as json]))

(def test-dir (file-seq (clojure.java.io/file ".")))

;; Read file structure and nested hashmap to preserve structure.
;;

(defn symbolic-link?
  "Given a Java io File, test if it's a symbolic link."
  [in-file]
  (java.nio.file.Files/isSymbolicLink (.toPath in-file)))

(defn parse-filestring-to-k
  ""
  [file-string]
  (filter #(not= % ".") (clojure.string/split file-string #"\/")))

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

(defn scan-directory
  "Given a file, if it's a directory, it will return a hashmap of the
  directory structure, otherwise it will return the file."
  [in-file]
  (if (.isFile in-file)
    {(.getName in-file) in-file}
    (if (symbolic-link? in-file)
      {(.getName in-file) {}}
      {(.getName in-file) (reduce into {} (map scan-directory (.listFiles in-file)))})))
