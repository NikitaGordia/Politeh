package com.nikitagordia.politeh.module.main.model.data.db

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by maxim on 2/16/18.
 */

class LessonDB() : RealmObject() {

    @PrimaryKey
    var lessonId: String? = null

    var groupId: String? = null

    var dayNumber: String? = null

    var dayName: String? = null

    var lessonName: String? = null

    var lessonFullName: String? = null

    var lessonNumber: String? = null

    var lessonRoom: String? = null

    var lessonType: String? = null

    var teacherName: String? = null

    var lessonWeek: String? = null

    var timeStart: String? = null

    var timeEnd: String? = null

    var rate: String? = null

    var teachers: RealmList<TeacherDB>? = null

    var rooms: RealmList<RoomDB>? = null

}
