(ns console.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "This text is printed from src/console/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn foo
  [x y]
  (* x y 10))

(defn hello-world []
  [:div
   [:h1 (:text @app-state)]
   [:h3 (foo 1 2) " watch it change!"]
   [:input {:type "button"
            :value "Hello World"
            :on-click #(prn "YAY!")}]
   [:a {:href "10.27.241.168"} "invoker"]
   [:input {:type "button"
            :value "Connect"
            :on-click #(prn "connected")}]])

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
