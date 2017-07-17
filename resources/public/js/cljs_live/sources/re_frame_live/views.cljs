(ns re-frame-live.views
  (:require [re-frame.core :as re-frame]
            [cljs-live.eval :as e]
            [cljs-live.compiler :as c]))

(enable-console-print!)

(c/load-bundles! ["/js/cljs_live/cljs.core.json"
                  "/js/cljs_live/re_frame_live.user.json"
                  #_"/js/cljs_live/cljs.spec.alpha.json"]
                 (fn []
                   (prn (e/eval '(require '[cljs.core :include-macros true])))
                   (prn (e/eval '(require '[re-frame-live.user :include-macros true])))
                   (prn (e/eval '(in-ns re-frame-live.user)))))


(defn main-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
        [:div
         [:div "Hello from " @name]
         [:textarea {:on-change #(prn (e/eval-str (.. % -target -value)))}
          ]])))
