package com.example.openwrt

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody

class RouterConnection(private var ip: String, private var uname: String, private var pwd: String){

    init{

    }

    fun connect(): Boolean{
        Fuel.post("$ip/cgi-bin/luci/rpc/auth")
            .jsonBody("{\"id\": 1, \"method\": \"login\", \"params\": [\"$uname\", \"$pwd\"]}")
            .response { request, response, result ->
                Log.v("Response", request.toString())
                Log.v("Response", response.responseMessage)
                val (bytes, error) = result
                if (bytes != null) {
                    Log.v("Response", "[response bytes] ${String(bytes)}")
                }
                false

            }
        return true
    }
}