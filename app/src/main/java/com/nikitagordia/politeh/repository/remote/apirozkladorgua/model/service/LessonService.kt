package com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.service

import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.response.LessonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by maxim on 2/16/18.
 */

interface LessonService {

    @GET("/v2/groups/{id}/lessons")
    fun getTimeTableOfGroup(@Path("id") id: Int): Call<LessonResponse>
}
