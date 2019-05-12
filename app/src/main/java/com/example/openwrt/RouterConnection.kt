package com.example.openwrt

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.google.gson.Gson

class RouterConnection(private var ip: String, private var uname: String, private var pwd: String){

    private var key: String? = ""

    init{

    }

    //User Model
    data class LoginResponse(val id: Int = 0, val result: String = "", val error: String? = null) {

        //User Deserializer
        class Deserializer : ResponseDeserializable<LoginResponse> {
            override fun deserialize(content: String) = Gson().fromJson(content, LoginResponse::class.java)
        }

    }

    fun connect(): Boolean{
        Fuel.post("http://$ip/cgi-bin/luci/rpc/auth")
            .jsonBody("{\"id\": 1, \"method\": \"login\", \"params\": [\"$uname\", \"$pwd\"]}")
            .responseObject(LoginResponse.Deserializer()) { request, response, result ->
                Log.v("Response", request.toString())
                Log.v("Response", response.responseMessage)
                Log.v("Response", result.toString())
                val (loginInfo, error) = result
                if(loginInfo?.error == null){
                    this.key = loginInfo?.result
                    Log.v("Response", this.key)
                }else{
                    false
                }

            }
        return true
    }
}