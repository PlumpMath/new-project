(set-env!
  :source-paths #{"src"}
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/boot-cljs "1.7.228-1"]
                  [cljsjs/blend4web "16.09-2"]
                  [tailrecursion/boot-jetty "0.1.0"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[tailrecursion.boot-jetty :refer [serve]])

(deftask uranium
  "Ensure uranium.js and uranium.js.mem are in target directory."
  []
  ;;uranium.js and uranium.js.mem need to both be in the same subdir as b4w's target html.
  (sift :add-jar {'cljsjs/blend4web #"^*/uranium.js*"}
        :move {#"^*cljsjs/blend4web/common/uranium.js"
               "uranium.js"}
        :to-resource #{#"uranium.js*"}))

(deftask save-n-build
  []
  (comp
    (watch)
    (cljs :optimizations :simple)
    (uranium)
    (target)
    (serve :port 8000)))
