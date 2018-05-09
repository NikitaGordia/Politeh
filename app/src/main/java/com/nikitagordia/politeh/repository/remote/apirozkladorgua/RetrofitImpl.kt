package com.nikitagordia.politeh.repository.remote.apirozkladorgua

import android.util.Log
import com.google.gson.Gson
import com.nikitagordia.politeh.repository.remote.SourceInterface
import com.nikitagordia.politeh.repository.remote.SubscriberInterface
import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.GroupResponse
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

object RetrofitImpl : SourceInterface {

    val MAIN_URL = "https://api.rozklad.org.ua/"
    val GROUP_PACKAGE_LIMIT = 100

    val retrofit = Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val groupService = retrofit.create(GroupService::class.java)

    var subscriber: SubscriberInterface? = null

    override fun subscribeOnGroup(sub: SubscriberInterface) {
        subscriber = sub
        launch (CommonPool) {
            try {
                var resp = groupService.getGroups(createInterval(GROUP_PACKAGE_LIMIT, 0)).execute().body()
                resp?.meta?.limit = GROUP_PACKAGE_LIMIT
                send(resp)
                var offset = GROUP_PACKAGE_LIMIT
                val all = resp?.meta?.totalCount?.toInt() ?: 0
                while (offset < all) {
                    if (subscriber == null) return@launch
                    resp = groupService.getGroups(createInterval(GROUP_PACKAGE_LIMIT, offset)).execute().body()
                    resp?.meta?.offset = (offset + GROUP_PACKAGE_LIMIT)
                    send(resp)
                    offset += GROUP_PACKAGE_LIMIT
                }
            } catch (e: IOException) {
                return@launch
            }
        }
    }

    fun send(resp: GroupResponse?) {
        async(UI) {
            resp?.data?.apply {  subscriber?.onDataGroup(this) }
        }
    }

    override fun cancel(sub: SubscriberInterface) {
        if (sub == subscriber) subscriber = null
    }
}

fun createInterval(limit: Int, offset: Int): String {
    val map = LinkedHashMap<String, Int>()
    map["limit"] = limit
    map["offset"] = offset
    return Gson().toJson(map)
}