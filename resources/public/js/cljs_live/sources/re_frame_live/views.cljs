(ns re-frame-live.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [cljs-live.eval :as e]
            [cljs-live.compiler :as c]))

(enable-console-print!)

(def eval-log
  "A reactive atom to store the log of evaluations."
  (reagent/atom (list)))

(defn eval-str!
  "Evaluate a string, and write it to the log."
  [source]
  (swap! eval-log conj (-> (e/eval-str source)
                           ;; add a unique key to each eval result.
                           ;; we use this below because React wants
                           ;; some unique value for every item in a list.
                           (assoc :time (.now js/Date)))))


;; always eval in the re-frame-live.user namespace.
;; cljs-live.compiler/c-env is the default 'environent' atom
;; which has an :ns key, which is used to store the current
;; namespace for the repl.
(defonce _ (swap! e/c-env assoc :ns (symbol "re-frame-live.user")))

;; downloads the core ClojureScript bundle, plus the re-frame-live.user namespace bundle.
(c/load-bundles! ["/js/cljs_live/cljs.core.json"
                  "/js/cljs_live/re_frame_live.user.json"
                  #_"/js/cljs_live/cljs.spec.alpha.json"]
                 (fn []
                   ;; when the bundles have loaded, require them into the namespace.
                   (e/eval '(require '[cljs.core :include-macros true]))
                   (e/eval '(require '[re-frame-live.user :include-macros true]))

                   ;; notify the user that things are ready to go.
                   (eval-str! "\"Ready to begin.\"")))

(defn show-result
  "Render an evaluation result."
  [{:keys [value error time]}]
  [:div {:key   time
         :style {:padding    10
                 :border-top "4px solid #eee"
                 :margin     10}}
   (if error (str error)
             value)])

(defn main-panel []
  [:div
   [:div "Press Ctrl-Enter to eval."]
   [:textarea {:style       {:width 400
                             :height 140
                             :border "4px solid #eee"}
               :on-key-down #(when (and (= 13 (.-keyCode %))
                                        (.-ctrlKey %))
                               (eval-str! (.. % -target -value)))}]

   ;; `seq` is a recommended way to see if a list has items in it
   (if (empty? @eval-log)
     (show-result {:value "Loading..."})
     (map show-result @eval-log))])
