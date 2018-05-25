package com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.service

import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.response.GroupResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GroupService {

    @GET("/v2/groups")
    fun getGroups(@Query("filter") filter : String) : Call<GroupResponse>
}