package com.nikitagordia.politeh.repository.remote.apirozkladorgua.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GroupService {

    @GET("/v2/groups")
    fun getGroups(@Query("filter") filter : String) : Call<GroupResponse>
}