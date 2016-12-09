
(ns chatroom-server.twig.topic (:require [recollect.bunch :refer [create-twig]]))

(def twig-topic
  (create-twig
   :topic
   (fn [topic author] (-> topic (dissoc :messages) (assoc :author author)))))
