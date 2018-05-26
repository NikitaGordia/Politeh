package com.nikitagordia.politeh.repository.remote.apirozkladorgua

import android.util.Log
import com.google.gson.Gson
import com.nikitagordia.politeh.module.group.model.repository.SourceGroupInterface
import com.nikitagordia.politeh.module.group.model.repository.SubscriberGroupInterface
import com.nikitagordia.politeh.module.main.model.repository.SourceLessonInterface
import com.nikitagordia.politeh.module.main.model.repository.SubscribeLessonInterface
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.response.GroupResponse
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.service.GroupService
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.response.LessonResponse
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.service.LessonService
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitImpl : SourceGroupInterface, SourceLessonInterface {

    private const val MAIN_URL = "https://api.rozklad.org.ua/"
    private const val GROUP_PACKAGE_LIMIT = 100

    private val retrofit = Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val groupService = retrofit.create(GroupService::class.java)
    private val lessonService = retrofit.create(LessonService::class.java)

    private var groupSub: SubscriberGroupInterface? = null
    private var lessonSub: SubscribeLessonInterface? = null
    private var job: Job? = null

    override fun subscribeOnGroup(sub: SubscriberGroupInterface) {
        checkJob()
        groupSub = sub
        job = launch (CommonPool) {
            try {
                var resp = groupService.getGroups(createInterval(GROUP_PACKAGE_LIMIT, 0)).execute().body()
                resp?.meta?.limit = GROUP_PACKAGE_LIMIT
                var offset = GROUP_PACKAGE_LIMIT
                val all = resp?.meta?.totalCount?.toInt() ?: 0
                sendGroups(resp, offset, all)
                while (offset < all) {
                    if (groupSub == null) return@launch
                    yield()
                    resp = groupService.getGroups(createInterval(GROUP_PACKAGE_LIMIT, offset)).execute().body()
                    offset += GROUP_PACKAGE_LIMIT
                    sendGroups(resp, offset, all)
                }
            } catch (e: IOException) {
                sendGroups(null, -1, -1)
            }
        }
    }

    override fun cancelGroup(sub: SubscriberGroupInterface) {
        if (sub == groupSub) {
            groupSub = null
            if (job?.isActive ?: false) job?.cancel()
        }
    }

    override fun subscribeOnLesson(sub: SubscribeLessonInterface, id: Int) {
        checkJob()
        lessonSub = sub
        job = launch(CommonPool) {
            try {
                var resp = lessonService.getTimeTableOfGroup(id).execute()
                if (resp.code() == 200) {
                    sendLesson(resp.body())
                } else throw IOException()
            } catch (e: IOException) {
                sendLesson(null)
            }
        }
    }

    override fun cancelLesson(sub: SubscribeLessonInterface) {
        if (sub == lessonSub) {
            lessonSub = null
            if (job?.isActive ?: false) job?.cancel()
        }
    }

    private fun checkJob() {
        if (job?.isActive ?: false) job?.cancel()
    }

    private fun sendLesson(resp: LessonResponse?) = launch(UI) { lessonSub?.onDataLesson(resp?.data); lessonSub = null }

    private fun sendGroups(resp: GroupResponse?, offset: Int, all: Int) {
        launch(UI) {
            resp?.data?.apply {
                val percent = if (offset > all) 100 else ((offset.toFloat() / all.toFloat()) * 100F).toInt()
                groupSub?.onDataGroup(this, percent)
            } ?: groupSub?.onDataGroup(listOf(), -1)
        }
    }
}

fun createInterval(limit: Int, offset: Int): String {
    val map = LinkedHashMap<String, Int>()
    map["limit"] = limit
    map["offset"] = offset
    return Gson().toJson(map)
}