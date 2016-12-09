
(ns chatroom-server.twig.container
  (:require [recollect.bunch :refer [create-twig]]
            [chatroom-server.twig.user :refer [twig-user]]
            [chatroom-server.twig.topic :refer [twig-topic]]
            [chatroom-server.twig.message :refer [twig-message]]))

(def twig-container
  (create-twig
   :container
   (fn [db state]
     (let [logged-in? (some? (:user-id state)), router (:router state), users (:users db)]
       {:state state,
        :logged-in? logged-in?,
        :statistics {},
        :user (if logged-in? (twig-user (get users (:user-id state))) nil),
        :data (if logged-in?
          (case (:name router)
            :home
              (->> (:topics db)
                   (map
                    (fn [entry]
                      (let [[k v] entry]
                        [k (twig-topic v (twig-user (get users (:author-id v))))])))
                   (into {}))
            :topic
              (if (and logged-in? (= (:name router) :topic))
                (->> (get-in db [:topics (:data router) :messages])
                     (map
                      (fn [entry]
                        (let [[k message] entry]
                          [k
                           (twig-message
                            message
                            (twig-user (get users (:author-id message))))])))
                     (into {}))
                nil)
            nil)
          nil)}))))
