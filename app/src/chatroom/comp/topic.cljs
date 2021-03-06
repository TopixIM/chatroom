
(ns chatroom.comp.topic
  (:require [hsl.core :refer [hsl]]
            [respo.alias :refer [create-comp div span input button]]
            [respo.comp.text :refer [comp-text]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.debug :refer [comp-debug]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]))

(defn on-topic [topic-id topic-title]
  (fn [e dispatch!]
    (dispatch!
     :router/change
     {:router nil, :name :topic, :title (str topic-title), :data topic-id})))

(def style-topic {:font-size 16, :cursor :pointer})

(def style-name
  {:line-height "16px",
   :color :white,
   :font-size 12,
   :background-color (hsl 260 60 88),
   :padding "0 8px",
   :display :inline-block})

(defn render [topic]
  (fn [state mutate!]
    (let [author (:author topic)]
      (div
       {:style style-topic, :event {:click (on-topic (:id topic) (:title topic))}}
       (comment comp-debug (:author topic) nil)
       (div {:style style-name, :attrs {:inner-text (:name author)}})
       (comp-space 8 nil)
       (comp-text (:title topic) nil)))))

(def comp-topic (create-comp :topic render))
