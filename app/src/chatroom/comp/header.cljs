
(ns chatroom.comp.header
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]))

(defn on-profile [e dispatch!]
  (dispatch! :router/change {:router nil, :name :profile, :data nil}))

(def style-header
  {:color :white,
   :font-size 20,
   :font-weight 300,
   :background-color colors/motif,
   :padding "0 16px",
   :justify-content :space-between,
   :font-family "Josefin Sans",
   :height 48})

(defn on-home [e dispatch!]
  (dispatch! :router/change {:router nil, :name :home, :data nil}))

(def style-cursor {:cursor "pointer"})

(defn render [logged-in?]
  (fn [state mutate!]
    (div
     {:style (merge ui/row-center style-header)}
     (div {:style style-cursor, :event {:click on-home}} (comp-text "Chat" nil))
     (div
      {:style style-cursor, :event {:click on-profile}}
      (comp-text (if logged-in? "Me" "Guest") nil)))))

(def comp-header (create-comp :header render))
