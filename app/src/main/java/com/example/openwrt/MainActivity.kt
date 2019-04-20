package com.example.openwrt

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import androidx.navigation.Navigation.findNavController
import com.example.openwrt.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(),
    RouterSelectionFragment.OnListFragmentInteractionListener,
    NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        Log.v("Yes!", "Got here!" + item.toString());

        findNavController(this, R.id.fragment).navigate(R.id.action_routerSelectionFragment_to_blankFragment);
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        Log.v("Yes!", "Got to Navigation Item Selected");
        return false;
    }

}
