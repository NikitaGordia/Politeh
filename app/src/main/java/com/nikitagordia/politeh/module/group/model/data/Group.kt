package com.nikitagordia.politeh.module.group.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Group {

    @SerializedName("group_id")
    @Expose
    var groupId: Int? = null
    @SerializedName("group_full_name")
    @Expose
    var groupFullName: String? = null
    @SerializedName("group_prefix")
    @Expose
    var groupPrefix: String? = null
    @SerializedName("group_okr")
    @Expose
    var groupOkr: String? = null
    @SerializedName("group_type")
    @Expose
    var groupType: String? = null
    @SerializedName("group_url")
    @Expose
    var groupUrl: String? = null
}