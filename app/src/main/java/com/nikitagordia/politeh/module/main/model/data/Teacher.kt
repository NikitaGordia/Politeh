package com.nikitagordia.politeh.module.main.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by nikitagordia on 2/23/18.
 */

class Teacher {

    @SerializedName("teacher_id")
    @Expose
    var teacherId: String? = null

    @SerializedName("teacher_name")
    @Expose
    var teacherName: String? = null

    @SerializedName("teacher_full_name")
    @Expose
    var teacherFullName: String? = null

    @SerializedName("teacher_short_name")
    @Expose
    var teacherShortName: String? = null

    @SerializedName("teacher_url")
    @Expose
    var teacherUrl: String? = null

    @SerializedName("teacher_rating")
    @Expose
    var teacherRating: String? = null

}
