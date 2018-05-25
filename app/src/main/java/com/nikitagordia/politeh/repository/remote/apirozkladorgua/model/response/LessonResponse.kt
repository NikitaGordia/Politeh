package com.nikitagordia.politeh.repository.remote.apirozkladorgua.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.nikitagordia.politeh.module.main.model.data.remote.Lesson

/**
 * Created by maxim on 2/16/18.
 */

class LessonResponse {

    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null
    @SerializedName("timeStamp")
    @Expose
    var timeStamp: Long = 0
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("debugInfo")
    @Expose
    var debugInfo: String? = null
    @SerializedName("meta")
    @Expose
    var meta: Any? = null
    @SerializedName("data")
    @Expose
    var data: List<Lesson>? = null
}