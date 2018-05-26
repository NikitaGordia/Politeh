package com.nikitagordia.politeh.module.main.model.data.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by nikitagordia on 2/23/18.
 */

open class TeacherDB : RealmObject() {

    @PrimaryKey
    var teacherId: String? = null

    var teacherName: String? = null

    var teacherFullName: String? = null

    var teacherShortName: String? = null

    var teacherUrl: String? = null

    var teacherRating: String? = null

}
