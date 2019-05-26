package com.example.openwrt

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView


import com.example.openwrt.RouterSelectionFragment.OnListFragmentInteractionListener
import com.example.openwrt.RouterConnection.OnNetworkElementChange
import com.example.openwrt.dummy.DummyContent.DummyItem

import kotlinx.android.synthetic.main.fragment_item.view.*
import android.widget.LinearLayout
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.extensions.jsonBody
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val mInfoList: ArrayList<RouterInfo>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>(), OnNetworkElementChange {

    init {


    }

    override fun onNetworkElementChange(connected: Boolean, info: RouterInfo) {
        if (connected){
            Log.v("List 1", "Connection Accepted!")
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(info)
        }else{
            Log.v("List 1", "Connection Rejected!")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val info = mInfoList[position]

        //holder.mIdView.text = info.ipString
        holder.mFirstIp.setText(info.ipArray?.get(0))
        holder.mSecondIp.setText(info.ipArray?.get(1))
        holder.mThirdIp.setText(info.ipArray?.get(2))
        holder.mFourthIp.setText(info.ipArray?.get(3))

        if(info.netmask and 0xFF != 0){
            Log.v("List 1", "First IP frozen!")
            holder.mFirstIp.isEnabled = false
        }
        if(info.netmask shr 8 and 0xFF != 0){
            Log.v("List 1", "Second IP frozen!")
            holder.mSecondIp.isEnabled = false
        }
        if(info.netmask shr 16 and 0xFF != 0){
            Log.v("List 1", "Third IP frozen!")
            holder.mThirdIp.isEnabled = false
        }
        if(info.netmask shr 24 and 0xFF != 0){
            Log.v("List 1", "Fourth IP frozen!")
            holder.mFourthIp.isEnabled = false
        }

        holder.mContentView.text = info.ssid
        holder.mSubContentView.visibility = (if (info.expanded) View.VISIBLE else View.GONE)

        with(holder.mSubContentView){
            loginButton.tag = info
            loginButton.setOnClickListener { v ->
                //root - administrator
                var connection = RouterConnection(v.tag as RouterInfo, uname.text.toString(), password.text.toString(), this@MyItemRecyclerViewAdapter)
                connection.connect()

            }
        }

        with(holder.mView) {
            tag = info
            setOnClickListener {
                // Change the state
                info.expanded = !info.expanded
                // Notify the adapter that item has changed
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int = mInfoList.size;

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mFirstIp: EditText = mView.firstIP
        val mSecondIp: EditText = mView.secondIP
        val mThirdIp: EditText = mView.thirdIP
        val mFourthIp: EditText = mView.fourthIP
        val mContentView: TextView = mView.content
        val mSubContentView: LinearLayout = mView.sub_item

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
