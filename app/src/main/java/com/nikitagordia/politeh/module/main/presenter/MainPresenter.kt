package com.nikitagordia.politeh.module.main.presenter

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.nikitagordia.politeh.module.main.model.data.remote.Lesson
import com.nikitagordia.politeh.module.main.model.repository.LocalSourceLessonInterface
import com.nikitagordia.politeh.module.main.model.repository.SourceLessonInterface
import com.nikitagordia.politeh.module.main.model.repository.SubscribeLessonInterface
import com.nikitagordia.politeh.repository.db.realm.RealmImpl
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.RetrofitImpl

/**
 * Created by nikitagordia on 5/25/18.
 */

class MainPresenter : MainPresenterInterface, ViewModel(), SubscribeLessonInterface {

    override val lessons = MutableLiveData<LessonsHolder>()

    private val remote: SourceLessonInterface = RetrofitImpl
    private val db: LocalSourceLessonInterface = RealmImpl

    private var id: Int = 0
    private var dbUsed = false

    override fun subscribeOnLesson(id: Int) {
        this.id = id
        dbUsed = true
        lessons.value = LessonsHolder(null, false)
        db.subscribeOnLesson(this, id)
    }

    override fun onDataLesson(list: List<Lesson>?) {
        if (list != null) {
            db.cancelLesson(this)
            remote.cancelLesson(this)
            lessons.value = LessonsHolder(list, false)
            if (!dbUsed) db.updateData(list)
            return
        }
        if (dbUsed) {
            lessons.value = LessonsHolder(null, true)
            dbUsed = false
            db.cancelLesson(this)
            remote.subscribeOnLesson(this, id)
        } else {
            remote.cancelLesson(this)
            Log.d("mytg", "ERROR")
            lessons.value = LessonsHolder(mutableListOf(), true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        db.cancelLesson(this)
        remote.cancelLesson(this)
    }
}