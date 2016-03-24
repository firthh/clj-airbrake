(ns clj-airbrake.environ
  (:require [environ.core :refer [env]]
            [clj-airbrake.core :as ab]))

(def config
  {:api-key          (env :airbrake-api-key)
   :project          (env :airbrake-project)
   :environment-name (env :environment)})

(def notify (partial ab/notify config))

(defmacro with-airbrake [extra-data & body]
  `(try
     ~@body
     (catch Throwable t#
       (notify t#
               ~extra-data)
       (throw t#))))
