package com.nikitagordia.politeh.repository.remote.apirozkladorgua

import com.google.gson.Gson
import com.nikitagordia.politeh.module.group.model.repository.SourceGroupInterface
import com.nikitagordia.politeh.module.group.model.repository.SubscriberGroupInterface
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.GroupResponse
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.GroupService
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitImpl : SourceGroupInterface {

    private const val MAIN_URL = "https://api.rozklad.org.ua/"
    private const val GROUP_PACKAGE_LIMIT = 100

    private val retrofit = Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val groupService = retrofit.create(GroupService::class.java)

    private var subscriber: SubscriberGroupInterface? = null
    private var job: Job? = null

    override fun subscribeOnGroup(sub: SubscriberGroupInterface) {
        subscriber = sub
        if (job?.isActive ?: false) job?.cancel()
        job = launch (CommonPool) {
            try {
                var resp = groupService.getGroups(createInterval(GROUP_PACKAGE_LIMIT, 0)).execute().body()
                resp?.meta?.limit = GROUP_PACKAGE_LIMIT
                var offset = GROUP_PACKAGE_LIMIT
                val all = resp?.meta?.totalCount?.toInt() ?: 0
                send(resp, offset, all)
                while (offset < all) {
                    if (subscriber == null) return@launch
                    resp = groupService.getGroups(createInterval(GROUP_PACKAGE_LIMIT, offset)).execute().body()
                    resp?.meta?.offset = (offset + GROUP_PACKAGE_LIMIT)
                    offset += GROUP_PACKAGE_LIMIT
                    send(resp, offset, all)
                }
            } catch (e: IOException) {
                send(null, -1, -1)
            }
        }
    }

    private fun send(resp: GroupResponse?, offset: Int, all: Int) {
        async(UI) {
            resp?.data?.apply {
                val percent = if (offset > all) 100 else ((offset.toFloat() / all.toFloat()) * 100F).toInt()
                subscriber?.onDataGroup(this, percent)
            } ?: subscriber?.onDataGroup(listOf(), -1)
        }
    }

    override fun cancel(sub: SubscriberGroupInterface) {
        if (sub == subscriber) {
            subscriber = null
            if (job?.isActive ?: false) job?.cancel()
        }
    }
}

fun createInterval(limit: Int, offset: Int): String {
    val map = LinkedHashMap<String, Int>()
    map["limit"] = limit
    map["offset"] = offset
    return Gson().toJson(map)
}