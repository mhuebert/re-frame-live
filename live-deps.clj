{:cljsbuild-out "resources/public/js/compiled/out-cljs-live"
 :output-dir    "resources/public/js/cljs_live"
 :bundles       [#_{:name          cljs.core
                    :require-cache [cljs.core cljs.core$macros]}
                 {:name         re-frame-live.user
                  :source-paths ["src/cljs"]
                  :require      [re-frame-live.user]
                  :require-cache [cljs-live.eval
                                  cljs-live.compiler]
                  :provided     [re-frame-live.core]}
                 #_{:name         cljs.spec.alpha
                    :source-paths ["src"]
                    :require      [[cljs.spec.test.alpha]]
                    :provided     [re-frame-live.core]}]}