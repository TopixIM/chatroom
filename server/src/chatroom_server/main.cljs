
(ns chatroom-server.main
  (:require [cljs.nodejs :as nodejs]
            [chatroom-server.schema :as schema]
            [chatroom-server.network :refer [run-server! render-clients!]]
            [chatroom-server.updater.core :refer [updater]]
            [cljs.core.async :refer [<!]]
            [cljs.reader :refer [read-string]])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(def db-path "/tmp/cumulo-workflow.edn")

(defn detect-db! [default-data]
  (let [fs (js/require "fs")]
    (if (.existsSync fs db-path)
      (do (println "Read db from" db-path) (read-string (.readFileSync fs db-path "utf8")))
      default-data)))

(defonce writer-db-ref (atom (detect-db! schema/database)))

(defonce reader-db-ref (atom @writer-db-ref))

(defn on-persist! []
  (let [fs (js/require "fs"), raw (pr-str @writer-db-ref)]
    (.writeFileSync fs db-path raw)
    (println "Wrote db to" db-path)))

(defn render-loop! []
  (if (not= @reader-db-ref @writer-db-ref)
    (do
     (reset! reader-db-ref @writer-db-ref)
     (comment println "render loop")
     (render-clients! @reader-db-ref)))
  (js/setTimeout render-loop! 300))

(defn -main []
  (nodejs/enable-util-print!)
  (let [server-ch (run-server! {:port 5021})]
    (go-loop
     []
     (let [[op op-data state-id op-id op-time] (<! server-ch)]
       (println "Action:" op op-data state-id op-id op-time)
       (comment println "Database:" @writer-db-ref)
       (try
        (let [new-db (updater @writer-db-ref op op-data state-id op-id op-time)]
          (reset! writer-db-ref new-db))
        (catch js/Error e (.log js/console e)))
       (recur)))
    (render-loop!))
  (add-watch reader-db-ref :log (fn [] ))
  (.on js/process "exit" on-persist!)
  (println "server started"))

(defn on-jsload! [] (println "code updated.") (render-clients! @reader-db-ref))

(set! *main-cli-fn* -main)
