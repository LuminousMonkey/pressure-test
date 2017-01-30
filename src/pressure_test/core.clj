(ns pressure-test.core
  (:require [clojure.data.json :as json]
            [clojure.walk :as walk]
            [pandect.algo.sha1 :refer [sha1]]
            [pandect.algo.md5 :refer [md5]]))

;; Read file structure and nested hashmap to preserve structure.
;;

(defn symbolic-link?
  "Given a Java io File, test if it's a symbolic link."
  [in-file]
  (java.nio.file.Files/isSymbolicLink (.toPath in-file)))

(defn calc-checksums
  "Given a file, return the SHA! and MD5 hashes of that file in a
  hashmap."
  [in-file]
  {"md5" (md5 in-file)
   "sha1" (sha1 in-file)})

(defn scan-directory
  "Given a file, if it's a directory, it will return a hashmap of the
  directory structure, otherwise it will return the file."
  [in-file]
  (if (.isFile in-file)
    {(.getName in-file) in-file}
    (if (symbolic-link? in-file)
      {(.getName in-file) {}}
      {(.getName in-file) (reduce into {}
                                  (map scan-directory (.listFiles in-file)))})))

(defn get-checksums
  ""
  [in-dirs]
  (walk/postwalk #(if (= java.io.File (class %)) (calc-checksums %) %) in-dirs))

(defn main
  ""
  [in-directory]
  (json/write-str (get-checksums
                   ((scan-directory (clojure.java.io/file in-directory)) in-directory))))
