package com.example.masterand

import android.app.Application
import com.example.masterand.containers.AppContainer
import com.example.masterand.containers.AppDataContainer

class MasterAndApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}