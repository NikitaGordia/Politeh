package com.nikitagordia.politeh.repository.remote.apirozkladorgua.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.nikitagordia.politeh.repository.remote.model.Group

/**
 * Created by nikitagordia on 2/13/18.
 */

class GroupResponse {

    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("timeStamp")
    @Expose
    var timeStamp: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("debugInfo")
    @Expose
    var debugInfo: Any? = null
    @SerializedName("meta")
    @Expose
    var meta: Meta? = null
    @SerializedName("data")
    @Expose
    var data: List<Group>? = null

}