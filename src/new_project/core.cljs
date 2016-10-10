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
        m-time      (.require js/b4w "time")
        m-controls  (.require js/b4w "controls")]

  (defn rotate-cube [val]
    (def cube (.get-object-by-name m-scenes "Cube")) ;; grab our cube object
    (.set-rotation-euler m-trans ;;function call
                         cube    ;;blender object argument
                         0       ;;x axis
                         0       ;;y axis
                         (/ val 4)))  ;;z axis -- this is where the rotation happens

  (defn stageload-cb [data-id success]
    (when (.is-primary-loaded m-data)
          ;;Minimal setup to create a continuous, simple object animation.
          ;;timeline-sensor will feed the rotate-cube callback the global time(s)
          (let [timeline-sensor (.create-timeline-sensor m-controls)
                ;;it looks like you need to throw sensor values in a manifold if
                ;;you want to grab their data, even if it's just one sensor.
                cube-manifold   (.create-sensor-manifold
                            m-controls
                            nil ;;only specify if its one object you want
                            "m_main" ;;manifold id -- can be basically anything
                            (.-CT_CONTINUOUS m-controls) ;;play forever
                            ;;you have to add all sensors to the manifold's
                            ;;array.
                            (clj->js [timeline-sensor])
                            rotate-cube)])))

  (defn loaded-cb [data-id success]
    "Needed for basic scene loading.
    Data-id seems to be the thread the data is loaded. Success is a boolean for
    whether or not the scene loading was successful.")

  (.set m-config "console_verbose" true) ;;get diagnostic info into js console
  (.load m-data
         (str json)   ;;process the argument you gave in the embedded javascript
         loaded-cb    ;;needed to kickoff the B4W engine
         stageload-cb ;;this is where you start manipulating the scenes
         true         ;;Wait until all resources are loaded
         false)       ;;Hide loaded objects and disable physics-objects

  ;; start this bad-body up.  Note we're hooking up the canvas node here
  (.init m-main canvas)))
