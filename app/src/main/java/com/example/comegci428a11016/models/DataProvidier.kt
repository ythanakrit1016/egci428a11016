//Thanakrit Yotapagde 5981016

package com.example.comegci428a11016.models

object DataProvider {
    private var data = ArrayList<FortuneCookies>()
    fun getData(): ArrayList<FortuneCookies> {
        return data
    }

    init {
        data.add(FortuneCookies("You're Lucky", "positive", "Date: 10-Oct-2016 12:00"))
        data.add(FortuneCookies("You will get A", "positive", "Date: 10-Oct-2016 13:00"))
        data.add(FortuneCookies("Don't Panic", "negative", "Date: 10-Oct-2016 13:00"))
    }
}