
(ns chatroom.comp.header
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.debug :refer [comp-debug]]
            [respo.comp.text :refer [comp-code comp-text]]))

(def style-header
  {:color :white,
   :font-size 16,
   :background-color colors/motif,
   :padding "0 16px",
   :justify-content :space-between,
   :height 48})

(defn render [logged-in?]
  (fn [state mutate!]
    (div
     {:style (merge ui/row-center style-header)}
     (div {} (comp-text "Messages" nil))
     (div
      {:style {:cursor "pointer"}, :event {}}
      (comp-text (if logged-in? "Me" "Guest") nil)))))

(def comp-header (create-comp :header render))
