package com.example.openwrt

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.result.Result

class RouterConnection(private var info: RouterInfo, private var uname: String, private var pwd: String, private var listener: OnNetworkElementChange){

    private var key: String? = ""
    var connected: Boolean = false
    var failed: Boolean = false

    init{

    }


    interface OnNetworkElementChange {
        fun onNetworkElementChange(connected: Boolean, info: RouterInfo)
    }

    //User Model
    data class LoginResponse(val id: Int = 0, val result: String? = null, val error: String? = null)

    fun connect() {
        Fuel.post("http://${info.ipString}/cgi-bin/luci/rpc/auth")
            .jsonBody("{\"id\": 1, \"method\": \"login\", \"params\": [\"$uname\", \"$pwd\"]}")
            .responseObject<LoginResponse> { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        val (data, _) = result
                        if (data?.error.isNullOrEmpty() and !data?.result.isNullOrEmpty()) {
                            this.key = data?.result
                            Log.v("Response", this.key.toString())
                            this.connected = true
                            listener.onNetworkElementChange(true, info)
                        } else {
                            Log.v("Response", "Failed Connection!")
                            this.key = null
                            this.failed = true
                            listener.onNetworkElementChange(false, info)
                        }
                    }
                    is Result.Failure -> {
                        listener.onNetworkElementChange(false, info)
                    }

                }
            }
    }
}