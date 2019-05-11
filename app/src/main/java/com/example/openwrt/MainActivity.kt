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
import android.support.v7.widget.SimpleItemAnimator




class MainActivity : AppCompatActivity(),
    RouterSelectionFragment.OnListFragmentInteractionListener,
    NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

    }

    override fun onListFragmentInteraction(item: RouterInfo) {
        val directions = RouterSelectionFragmentDirections.action_routerSelectionFragment_to_blankFragment().setIpaddress(item.ip)
        findNavController(this, R.id.fragment).navigate(directions)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        Log.v("Yes!", "Got to Navigation Item Selected")
        return false;
    }

}
