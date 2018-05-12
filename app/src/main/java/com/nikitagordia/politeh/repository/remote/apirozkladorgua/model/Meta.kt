package com.nikitagordia.politeh.repository.remote.apirozkladorgua.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Meta {

    @SerializedName("total_count")
    @Expose
    var totalCount: String? = null
    @SerializedName("offset")
    @Expose
    var offset: Int? = null
    @SerializedName("limit")
    @Expose
    var limit: Int? = null

}
