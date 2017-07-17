(ns re-frame-live.events
  (:require [re-frame.core :as re-frame]
            [re-frame-live.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))
