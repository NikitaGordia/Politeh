package com.nikitagordia.politeh.repository.remote.apirozkladorgua

import com.google.gson.Gson
import com.nikitagordia.politeh.module.group.model.repository.SourceGroupInterface
import com.nikitagordia.politeh.module.group.model.repository.SubscriberGroupInterface
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.GroupResponse
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.GroupService
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.LinkedHashMap

/**
 * Created by nikitagordia on 5/9/18.
 */

object RetrofitImpl : SourceGroupInterface {

    val MAIN_URL = "https://api.rozklad.org.ua/"
    val GROUP_PACKAGE_LIMIT = 100

    val retrofit = Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val groupService = retrofit.create(GroupService::class.java)

    var subscriber: SubscriberGroupInterface? = null

    override fun subscribeOnGroup(sub: SubscriberGroupInterface) {
        subscriber = sub
        launch (CommonPool) {
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
                return@launch
            }
        }
    }

    fun send(resp: GroupResponse?, offset: Int, all: Int) {
        val percent = if (offset > all) 100 else ((offset.toFloat() / all.toFloat()) * 100F).toInt()
        async(UI) {
            resp?.data?.apply {  subscriber?.onDataGroup(this, percent) }
        }
    }

    override fun cancel(sub: SubscriberGroupInterface) {
        if (sub == subscriber) subscriber = null
    }
}

fun createInterval(limit: Int, offset: Int): String {
    val map = LinkedHashMap<String, Int>()
    map["limit"] = limit
    map["offset"] = offset
    return Gson().toJson(map)
}