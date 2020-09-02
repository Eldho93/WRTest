package com.eldho.whiterabbitsample

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        VolleySingleton.initConfi(this)
    }
}