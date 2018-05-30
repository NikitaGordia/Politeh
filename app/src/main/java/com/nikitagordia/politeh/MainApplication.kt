package com.nikitagordia.politeh

import android.app.Application
import io.realm.Realm

/**
 * Created by nikitagordia on 5/30/18.
 */


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
    }
}