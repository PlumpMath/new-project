(ns new-project.core
  (:require blend4web))

(def m-version (.require js/b4w "version"))

(.log js/console "Using Blend4Web Version" (.version m-version))

(defn ^:export start
  [json]
  (let [m-main      (.require js/b4w "main")
        m-data      (.require js/b4w "data")
        canvas      (.getElementById js/document "container")
        m-config    (.require js/b4w "config")
        m-trans     (.require js/b4w "transform")
        m-scenes    (.require js/b4w "scenes")
        m-time      (.require js/b4w "time")]

  (defn rotate-cube [val]
    (def cube (.get-object-by-name m-scenes "Cube"))
    (.set-rotation-euler m-trans cube 0 0 val))

  (defn stageload-cb [data-id success]
    (when (.is-primary-loaded m-data)
              ;;This will spin for about 13 minutes.
              (.animate m-time 0 2000 800000 rotate-cube)))

  (defn loaded-cb [data-id success]
    "Needed for basic scene loading.
    Data-id seems to be the thread the data is loaded. Success is a boolean for
    whether or not the scene loading was successful.")

  (.set m-config "console_verbose" true)
  (.load m-data
         (str json)
         loaded-cb
         stageload-cb
         true
         false)

  (.init m-main canvas)))
