
(ns chatroom.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]
            [chatroom.comp.header :refer [comp-header]]
            [chatroom.comp.profile :refer [comp-profile]]
            [chatroom.comp.topics :refer [comp-topics]]
            [chatroom.comp.room :refer [comp-room]]
            [chatroom.comp.login :refer [comp-login]]
            [respo-message.comp.msg-list :refer [comp-msg-list]]))

(def style-body {})

(defn render [store]
  (fn [state mutate!]
    (let [router (get-in store [:state :router])]
      (div
       {:style (merge ui/global ui/fullscreen ui/column)}
       (comp-header (:logged-in? store))
       (if (:logged-in? store)
         (case (:name router)
           :home (comp-topics (:topics store) (:logged-in? store))
           :topic (comp-room (:seeing-messages store) (:data router))
           :profile (comp-profile store)
           nil)
         (comp-login))
       (comp-debug router {:bottom 0, :max-width "100%", :left 0})
       (comp-msg-list (get-in store [:state :notifications]) :state/remove-notification)))))

(def comp-container (create-comp :container render))
