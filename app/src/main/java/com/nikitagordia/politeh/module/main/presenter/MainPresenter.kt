package com.nikitagordia.politeh.module.main.presenter

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.nikitagordia.politeh.module.main.model.data.remote.Lesson
import com.nikitagordia.politeh.module.main.model.repository.LocalSourceLessonInterface
import com.nikitagordia.politeh.module.main.model.repository.SourceLessonInterface
import com.nikitagordia.politeh.module.main.model.repository.SubscribeLessonInterface
import com.nikitagordia.politeh.repository.db.realm.RealmImpl
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.RetrofitImpl
import com.nikitagordia.politeh.util.SharedPreferencesManager

/**
 * Created by nikitagordia on 5/25/18.
 */

class MainPresenter : MainPresenterInterface, ViewModel(), SubscribeLessonInterface {

    override val lessons = MutableLiveData<List<Lesson>>()

    private val remote: SourceLessonInterface = RetrofitImpl
    private val db: LocalSourceLessonInterface = RealmImpl

    private var id: Int = 0

    override fun initLocalDB(context: Context) {
        db.init(context)
        id = SharedPreferencesManager.getGroupId(context)
        db.subscribeOnLesson(this, id)
    }

    override fun onDataLesson(list: List<Lesson>?) {
        if (list == null) {
            remote.subscribeOnLesson(this, id)
            lessons.value = null
        } else lessons.value = list
    }

    override fun onCleared() {
        super.onCleared()
        db.cancelLesson(this)
        remote.cancelLesson(this)
    }
}