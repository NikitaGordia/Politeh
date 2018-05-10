package com.nikitagordia.politeh.repository.remote.apirozkladorgua.model

import com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.GroupResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by nikitagordia on 5/9/18.
 */

interface GroupService {

    @GET("/v2/groups")
    fun getGroups(@Query("filter") filter : String) : Call<GroupResponse>
}