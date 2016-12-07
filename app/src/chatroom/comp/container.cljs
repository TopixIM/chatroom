
(ns chatroom.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]
            [chatroom.comp.home :refer [comp-home]]
            [chatroom.comp.header :refer [comp-header]]
            [respo-message.comp.msg-list :refer [comp-msg-list]]))

(def style-body {})

(defn render [store]
  (fn [state mutate!]
    (div
     {:style (merge ui/global ui/fullscreen ui/column)}
     (comp-header (:logged-in? store))
     (div {:style style-body} (comp-home store))
     (comp-debug (:logged-in? store) {:bottom 0, :max-width "100%", :left 0})
     (comp-msg-list (get-in store [:state :notifications]) :state/remove-notification))))

(def comp-container (create-comp :container render))
