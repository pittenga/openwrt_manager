package com.example.openwrt

class RouterInfo(ip: Int, ssid: String) {
    var ip: Int
    var ssid: String
    var uname: String
    var password: String
    var ipString: String

    // State of the item
    var expanded: Boolean

    init {
        this.ip = ip
        this.ssid = ssid
        this.uname = ""
        this.password = ""
        this.expanded = false
        this.ipString = ipToString(ip)
    }

    private fun ipToString(i: Int): String {
        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)

    }



}