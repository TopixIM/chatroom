
(ns chatroom.comp.message
  (:require [respo.alias :refer [create-comp div span input button]]
            [respo.comp.text :refer [comp-text]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.debug :refer [comp-debug]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]))

(defn render [message]
  (fn [state mutate!]
    (let [author (:author message)]
      (div
       {}
       (comp-text (:name author) nil)
       (comp-space 8 nil)
       (comp-text (:text message) nil)
       (comment comp-debug (:author message) nil)))))

(def comp-message (create-comp :message render))
