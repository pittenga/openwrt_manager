package com.example.openwrt

import android.text.Editable
import android.util.Log

class RouterInfo(ip: Int, ssid: String, netmask: Int) {
    var ip: Int = ip
    var netmask: Int = netmask
    var ssid: String = ssid
    var uname: String = ""
    var password: String = ""
    var ipString: String
    var ipArray: ArrayList<String>? = null

    // State of the item
    var expanded: Boolean = false

    init {
        this.ipString = ipToString(ip)
        this.ipArray = ipToArray(ip)
    }

    private fun ipToString(i: Int): String {
        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)

    }

    private fun ipToArray(i: Int): ArrayList<String> {
        var list = arrayListOf<String>()
        list.add((i and 0xFF).toString())
        list.add((i shr 8 and 0xFF).toString())
        list.add((i shr 16 and 0xFF).toString())
        list.add((i shr 24 and 0xFF).toString())
        return list
    }



}