//Thanakrit Yotapagde 5981016

package com.example.comegci428a11016

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.menu2_layout.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

var curDate = ""

class MainActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        imageView.setImageResource(R.drawable.closed_cookie)

        val actionBar = supportActionBar
        actionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setCustomView(R.layout.menu2_layout)
        floatTV2.text = "Fortune Cookies"
        returnBTN.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }

        wishBTN.setOnClickListener {
            Toast.makeText(this, "Waiting", Toast.LENGTH_SHORT).show()
            loadJSON()
        }

    }

    private fun loadJSON() {

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm")
        val formatted = current.format(formatter)

        curDate = "Date: " + formatted.toString()

        val num = kotlin.random.Random.nextInt(0, 9).toString()
        val jsonURL = "https://egco428-json.firebaseio.com/fortunecookies/" + num + ".json"
        val client = OkHttpClient()

        val asyncTask = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<String, String, String>() {

            override fun doInBackground(vararg arg: String?): String {
                val builder = Request.Builder()
                builder.url(arg[0].toString())
                val request = builder.build()
                try {
                    val response = client.newCall(request)
                        .execute() // call or send request to web service and get data back to response
                    return response.body!!.string()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return ""
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                val cookiesText = Gson().fromJson(result, Cookiess::class.java)
                imageView.setImageResource(R.drawable.opened_cookie)
                if (cookiesText.status == "positive") {
                    actualresultTV.setTextColor(Color.parseColor("#2196F3"))
                } else {
                    actualresultTV.setTextColor(Color.parseColor("#FF9922"))
                }
                actualresultTV.text = "        Result : " + cookiesText.message
                incookieTV.text = cookiesText.message
                dateTV.text = curDate + "    "
                wishBTN.text = "Save"
                wishBTN.setOnClickListener {
                    var intent = Intent(this@MainActivity2, MainActivity::class.java)
                    intent.putExtra("CkMSG", cookiesText.message)
                    intent.putExtra("CkDate", curDate)
                    intent.putExtra("CkSTAT", cookiesText.status)
                    startActivity(intent)
                }
            }

        }
        asyncTask.execute(jsonURL)
    }

}

class Cookiess(var message: String, var status: String)