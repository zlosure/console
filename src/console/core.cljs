(ns console.core
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs-http.client :as http]))

(enable-console-print!)

(println "This text is printed from src/console/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(def action (atom nil))
(def ns (atom nil))
(def target (atom nil))

(defn hello-world []
  [:div
   [:input {:type :button
            :value "Invoker home page"
            :on-click #(http/get "http://invoker.admin:8080")}]
   [:h3 "Action"]
   [:div
    [:input {:type :button
             :value "install"
             :on-click #(reset! action :install)}]
    [:input {:type :button
             :value "upgrade"
             :on-click #(reset! action :upgrade)}]
    [:input {:type :button
             :value "delete"
             :on-click #(reset! action :delete)}]]
   [:h3 "Target"]
   [:div
    [:input {:type :text
             :value @target
             :on-change #(reset! target (-> % .-target .-value))}]]
   [:h3 "Namespace"]
   [:div
    [:input {:type :text
             :value @ns
             :on-change #(reset! ns (-> % .-target .-value))}]]
   [:h3 "Your command is"]
   [:div
    [:strong @action] " target " [:strong @target] " in namespace " [:strong @ns]]
   [:div
    [:input {:type :button
             :value "invoke"
             :on-click #(http/post "http://invoker.admin:8080/api/v0.1/invoke"
                                   {:json-params {:action @action
                                                  :namespace @ns
                                                  :target @target}})}]]])

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
