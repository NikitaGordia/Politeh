package com.nikitagordia.politeh.module.main.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.nikitagordia.politeh.module.main.model.data.remote.Lesson

/**
 * Created by nikitagordia on 5/25/18.
 */

interface MainPresenterInterface {

    val lessons: MutableLiveData<List<Lesson>>

    fun initLocalDB(context: Context)
}