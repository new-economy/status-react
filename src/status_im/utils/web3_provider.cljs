(ns status-im.utils.web3-provider
  (:require [taoensso.timbre :as log]
            [status-im.native-module.core :as status]
            [status-im.js-dependencies :as dependencies]))

(defn make-web3 []
  (dependencies/Web3.
   #js {:sendAsync (fn [payload callback]
                     (status/call-web3
                      (.stringify js/JSON payload)
                      (fn [response]
                        (if (= "" response)
                          (log/warn :web3-response-error)
                          (callback nil (.parse js/JSON response))))))}))
