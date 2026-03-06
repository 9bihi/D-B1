package com.deutschb1

import android.app.Application
import android.content.Context

class DeutschB1Application : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: DeutschB1Application? = null

        fun getContext(): Context {
            return instance!!.applicationContext
        }
    }
}
