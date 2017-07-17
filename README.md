Demonstration of using [cljs-live](https://www.github.com/mhuebert/cljs-live) with re-frame.

`lein figwheel` should be enough to get started trying the demo.

`re-frame-live.user` is the namespace which is used to build the bundle,
so everything required here should be available to the compiler state.

Have a look at `re-frame-live.views` to see how we load the bundles and set up a simple REPL.

To re-generate the bundle, clone cljs-live into the same directory as re_frame_live, and run

```bash
../cljs_live/bundle.sh live-deps.clj
```

cljs-live is still in flux, YMMV :).