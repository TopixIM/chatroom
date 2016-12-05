
(ns chatroom-server.updater.core
  (:require [chatroom-server.updater.state :as state]
            [chatroom-server.updater.user :as user]
            [chatroom-server.updater.topic :as topic]
            [chatroom-server.updater.message :as message]
            [chatroom-server.updater.router :as router]))

(defn updater [db op op-data state-id op-id op-time]
  (case op
    :state/connect (state/connect db op-data state-id op-id op-time)
    :state/disconnect (state/disconnect db op-data state-id op-id op-time)
    :user/log-in (user/log-in db op-data state-id op-id op-time)
    :user/sign-up (user/sign-up db op-data state-id op-id op-time)
    :user/log-out (user/log-out db op-data state-id op-id op-time)
    :state/remove-notification (state/remove-notification db op-data state-id op-id op-time)
    :topic/create (topic/add-one db op-data state-id op-id op-time)
    :router/change (router/change db op-data state-id op-id op-time)
    :message/create (message/create db op-data state-id op-id op-time)
    db))
