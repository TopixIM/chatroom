
(ns chatroom.comp.message
  (:require [hsl.core :refer [hsl]]
            [respo.alias :refer [create-comp div span input button]]
            [respo.comp.text :refer [comp-text]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.debug :refer [comp-debug]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]))

(def style-author
  {:line-height "16px",
   :color :white,
   :font-size 12,
   :background-color (hsl 260 60 92),
   :padding "0 8px",
   :display :inline-block})

(defn render [message]
  (fn [state mutate!]
    (let [author (:author message)]
      (div
       {}
       (div {:style style-author, :attrs {:inner-text (:name author)}})
       (comp-space 8 nil)
       (comp-text (:text message) nil)
       (comment comp-debug (:author message) nil)))))

(def comp-message (create-comp :message render))
