package com.nikitagordia.politeh.repository.db.realm

import android.content.Context
import android.util.Log
import com.nikitagordia.politeh.module.main.model.data.db.LessonDB
import com.nikitagordia.politeh.module.main.model.data.remote.Lesson
import com.nikitagordia.politeh.module.main.model.repository.LocalSourceLessonInterface
import com.nikitagordia.politeh.module.main.model.repository.SubscribeLessonInterface
import com.nikitagordia.politeh.util.RealmMaper
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

/**
 * Created by nikitagordia on 5/25/18.
 */

object RealmImpl : LocalSourceLessonInterface {

    private val realm = Realm.getInstance(RealmConfiguration.Builder().build())

    private var lessonSub: SubscribeLessonInterface? = null
    private var job: Job? = null

    override fun updateData(list: List<Lesson>) {
        realm.executeTransactionAsync { realm -> realm.deleteAll(); list.forEach { realm.insertOrUpdate(RealmMaper.map(it)) } }
    }

    override fun subscribeOnLesson(sub: SubscribeLessonInterface, id: Int) {
        checkJob()
        lessonSub = sub
        realm.executeTransactionAsync { realm ->
            val it = realm.where(LessonDB::class.java).equalTo("groupId", id.toString() + "").findAll().listIterator()
            if (!it.hasNext()) {
                sendLesson(null)
            } else {
                val res = mutableListOf<Lesson>()
                while (it.hasNext()) res.add(RealmMaper.map(it.next()))
                sendLesson(res.toList())
            }
        }
    }

    private fun sendLesson(list: List<Lesson>?) {
        launch (UI) { lessonSub?.onDataLesson(list) }
    }

    override fun cancelLesson(sub: SubscribeLessonInterface) {
        if (sub == lessonSub) {
            lessonSub = null
            checkJob()
        }
    }

    private fun checkJob() {
        if (job?.isActive ?: false) job?.cancel()
    }
}