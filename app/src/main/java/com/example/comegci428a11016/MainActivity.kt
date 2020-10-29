//Thanakrit Yotapagde 5981016

package com.example.comegci428a11016

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.comegci428a11016.models.DataProvider
import com.example.comegci428a11016.models.FortuneCookies
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cookies_items.view.*
import kotlinx.android.synthetic.main.menu1_layout.*

class MainActivity : AppCompatActivity() {

    var data: ArrayList<FortuneCookies>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setCustomView(R.layout.menu1_layout)
        floatTV.text = "Fortune Cookies"
        addBTN.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
        data = DataProvider.getData()
        val cookiesAdapter = CookiesArrayAdaper(data!!)

        if (intent.getStringExtra("CkMSG") != null) {
            val CkMSG = intent.getStringExtra("CkMSG")
            val Ckdate = intent.getStringExtra("CkDate")
            val CkSTAT = intent.getStringExtra("CkSTAT")
            data!!.add(FortuneCookies(CkMSG!!, CkSTAT!!, Ckdate!!))
        } else {
            Log.d("test", "no data / null ")
        }
        cookiesAdapter.notifyDataSetChanged()
        mainListView.adapter = cookiesAdapter

    }

    class CookiesArrayAdaper(var mCookies: ArrayList<FortuneCookies>) : BaseAdapter() {

        override fun getCount(): Int {
            return mCookies.size
        }

        override fun getItem(position: Int): Any {
            return mCookies[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val cookiees = mCookies[position]
            val view: View

            if (convertView == null) {
                val layoutInflater = LayoutInflater.from(parent!!.context)
                view = layoutInflater.inflate(R.layout.cookies_items, parent, false)
                val viewHolder = ViewHolder(
                    view.messageTextV,
                    view.messageTextV2,
                    view.incookieListV,
                    view.imageCookies
                )
                view.tag = viewHolder
            } else {
                view = convertView
            }

            val viewHolder = view.tag as ViewHolder
            viewHolder.messageTextV.text = cookiees.message
            viewHolder.messageTextV2.text = cookiees.ckdate
            viewHolder.incookieListV.text = cookiees.message

            if (cookiees.status == "positive") {
                view.messageTextV.setTextColor(Color.parseColor("#2196F3"))
            } else {
                view.messageTextV.setTextColor(Color.parseColor("#FF9922"))
            }

            viewHolder.imageCookies.setImageResource(R.drawable.opened_cookie)
            notifyDataSetChanged()

            view.setOnClickListener {
                view.animate().setDuration(1000).alpha(0f).withEndAction({
                    mCookies.removeAt(position)
                    notifyDataSetChanged()
                    view.alpha = 1F
                })
            }
            return view
        }

        private class ViewHolder(
            val messageTextV: TextView,
            val messageTextV2: TextView,
            val incookieListV: TextView,
            val imageCookies: ImageView
        )
    }

}