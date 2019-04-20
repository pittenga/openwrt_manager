package com.example.openwrt

import android.content.Context
import android.net.DhcpInfo
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import androidx.navigation.Navigation.findNavController
import com.example.openwrt.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.text.format.Formatter.formatIpAddress
import android.net.wifi.WifiManager



class MainActivity : AppCompatActivity(),
    RouterSelectionFragment.OnListFragmentInteractionListener,
    NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val wm = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        Log.v("OnCreate", "SSID: " + wm.connectionInfo.ssid)
        Log.v("OnCreate", "IP: " + ipToString(wm.connectionInfo.ipAddress))
        Log.v("OnCreate", "Network ID: " + ipToString(wm.dhcpInfo.gateway))

    }

    private fun ipToString(i: Int): String {
        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)

    }

    override fun onListFragmentInteraction(item: DhcpInfo) {
        findNavController(this, R.id.fragment).navigate(R.id.action_routerSelectionFragment_to_blankFragment);
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        Log.v("Yes!", "Got to Navigation Item Selected")
        return false
    }

}
