(ns console.core
  (:require [cljs-http.client :as http]
            [reagent.core :as reagent :refer [atom]]
            [re-com.core :as rc :refer [box
                                        button
                                        h-box
                                        info-button
                                        input-text
                                        label
                                        p
                                        single-dropdown
                                        throbber
                                        title
                                        v-box]]))

(enable-console-print!)

(def app-state (atom {:invoking? false}))

(def action (atom nil))
(def nspace (atom nil))
(def target (atom nil))
(def charts-branch (atom nil))
(def target-branch (atom nil))

(def action-choices
  [{:id :install :label "install"}
   {:id :upgrade :label "upgrade"}
   {:id :delete :label "delete"}])

(defn action-dropdown
  []
  [v-box
   :children [[h-box
               :gap "3px"
               :children [[:span.field-label "action"]
                          [info-button
                           :info "Cluster action you want to make."
                           :width "200px"]]]
              [single-dropdown
               :choices action-choices
               :model action
               :on-change #(reset! action %)
               :width "120px"]]])

(defn target-input
  []
  [v-box
   :children [[h-box
               :gap "3px"
               :children [[:span.field-label "target"]
                          [info-button
                           :info "Project's name on which your action is applied."
                           :width "200px"]]]
              [input-text
               :model target
               :on-change #(reset! target %)
               :width "120px"]]])

(defn namespace-input
  []
  [v-box
   :children [[h-box
               :gap "3px"
               :children [[:span.field-label "namespace"]
                          [info-button
                           :info "Cluster namespace in which you action is scoped."
                           :width "200px"]]]
              [input-text
               :model nspace
               :on-change #(reset! nspace %)
               :width "120px"]]])

(defn charts-branch-input
  []
  [v-box
   :children [[h-box
               :gap "3px"
               :children [[:span.field-label "charts branch"]
                          [info-button
                           :info (str "(Optional) The Git branch of your charts"
                                      " repo, default to master.")
                           :width "200px"]]]
              [input-text
               :model charts-branch
               :on-change #(reset! charts-branch %)
               :placeholder "master"
               :width "120px"]]])

(defn target-branch-input
  []
  [v-box
   :children [[h-box
               :gap "3px"
               :children [[:span.field-label "target branch"]
                          [info-button
                           :info (str "(Optional) The Git branch of your target"
                                      " repo, default to master.")
                           :width "200px"]]]
              [input-text
               :model target-branch
               :on-change #(reset! target-branch %)
               :placeholder "master"
               :width "120px"]]])

(defn console-panel
  []
  [v-box
   :size "auto"
   :gap "30px"
   :children [[title
               :label "Invoker console"
               :level :level1]
              [title
               :label "Combine reagents and invoke the spell as you want."
               :level :level4]
              [h-box
               :size "auto"
               :gap "10px"
               :children [[action-dropdown]
                          [target-input]
                          [namespace-input]
                          [charts-branch-input]
                          [target-branch-input]]]
              [h-box
               :children [[button
                           :class "btn-primary"
                           :label "invoke"
                           :on-click
                           #(do
                              (swap! app-state assoc :invoking? true)
                              (prn @action @charts-branch @nspace @target @target-branch))
                           :tooltip "Run the command in your cluster."
                           :tooltip-position :below-center]
                          (when (:invoking? @app-state) [throbber])]
               :gap "10px"
               :size "auto"]]])

(reagent/render-component [console-panel] (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
