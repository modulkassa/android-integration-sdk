package ru.modulkassa.pos.integrationtest

import android.app.Application
import ru.modulkassa.pos.integration.ModulKassaClient

class DemoApp : Application() {

    companion object {
        lateinit var instance: DemoApp
    }

    lateinit var modulKassaClient: ModulKassaClient

    override fun onCreate() {
        super.onCreate()
        instance = this
        modulKassaClient = ModulKassaClient(this)
    }
}