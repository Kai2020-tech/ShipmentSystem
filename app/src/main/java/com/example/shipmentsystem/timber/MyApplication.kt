package com.example.shipmentsystem.timber

import android.app.Application
import com.example.shipmentsystem.db.MyDatabase
import timber.log.Timber

class MyApplication : Application() {
//    var db: MyDatabase = MyDatabase.getInstance(this)
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}