
(ns chatroom-server.twig.message (:require [recollect.bunch :refer [create-twig]]))

(def twig-message
  (create-twig :message (fn [message author] (-> message (assoc :author author)))))
