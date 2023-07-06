package com.example.testcase

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TestApp : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: TestApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        applicationContext()
    }
}