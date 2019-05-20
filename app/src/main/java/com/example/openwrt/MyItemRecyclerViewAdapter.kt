package com.example.openwrt

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    fun json(build: JsonObjectBuilder.() -> Unit): JSONObject {
        return JsonObjectBuilder().json(build)
    }

    class JsonObjectBuilder {
        private val deque: Deque<JSONObject> = ArrayDeque()

        fun json(build: JsonObjectBuilder.() -> Unit): JSONObject {
            deque.push(JSONObject())
            this.build()
            return deque.pop()
        }

        infix fun <T> String.To(value: T) {
            deque.peek().put(this, value)
        }
    }

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

        holder.mIdView.text = info.ipString
        holder.mContentView.text = info.ssid
        holder.mSubContentView.visibility = (if (info.expanded) View.VISIBLE else View.GONE)

        with(holder.mSubContentView){
            loginButton.tag = info
            loginButton.setOnClickListener { v ->
                var info: RouterInfo = v.tag as RouterInfo
                //root - administrator

                var connection = RouterConnection(info, uname.text.toString(), password.text.toString(), this@MyItemRecyclerViewAdapter)
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

    override fun getItemCount(): Int = 1

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mSubContentView: LinearLayout = mView.sub_item

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
