(defproject console "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}



  :min-lein-version "2.7.1"

  :dependencies [[cljs-http "0.1.45"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [org.clojure/core.async  "0.4.474"]
                 [reagent "0.7.0"]
                 [re-com "2.4.0"]]

  :plugins [[lein-figwheel "0.5.16"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]

                ;; The presence of a :figwheel configuration here
                ;; will cause figwheel to inject the figwheel client
                ;; into your build
                :figwheel {:on-jsload "console.core/on-js-reload"
                           ;; Websocket server that the Browser can connect back
                           ;; to. Uses `window.location.hostname` from JS for
                           ;; GKE cluster service.
                           :websocket-host :js-client-host}

                :compiler {:main console.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/console.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                           ;; https://github.com/binaryage/cljs-devtools
                           :preloads [devtools.preload]}}
               ;; This next build is a compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/console.js"
                           :main console.core
                           :optimizations :advanced
                           :pretty-print false}}]}

  ;; Setting up nREPL for Figwheel and ClojureScript dev
  ;; Please see:
  ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl
  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.9"]
                                  [cider/piggieback "0.3.10"]
                                  [figwheel-sidecar "0.5.16"]
                                  [nrepl "0.4.4"]]

                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["dev"]
                   ;; TODO: Move plugins to profiles.clj
                   :plugins [[cider/cider-nrepl "0.18.0"]]
                   :repl-options {:init (fig-start)
                                  :init-ns user
                                  :host "0.0.0.0"
                                  :port 55555
                                  :nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                   ;; need to add the compliled assets to the :clean-targets
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}})
