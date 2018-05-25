package com.nikitagordia.politeh.module.main.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

/**
 * Created by maxim on 2/16/18.
 */
class Lesson {

    @SerializedName("lesson_id")
    @Expose
    var lessonId: String? = null

    @SerializedName("group_id")
    @Expose
    var groupId: String? = null

    @SerializedName("day_number")
    @Expose
    var dayNumber: String? = null

    @SerializedName("day_name")
    @Expose
    var dayName: String? = null

    @SerializedName("lesson_name")
    @Expose
    var lessonName: String? = null

    @SerializedName("lesson_full_name")
    @Expose
    var lessonFullName: String? = null

    @SerializedName("lesson_number")
    @Expose
    var lessonNumber: String? = null

    @SerializedName("lesson_room")
    @Expose
    var lessonRoom: String? = null

    @SerializedName("lesson_type")
    @Expose
    var lessonType: String? = null

    @SerializedName("teacher_name")
    @Expose
    var teacherName: String? = null

    @SerializedName("lesson_week")
    @Expose
    var lessonWeek: String? = null

    @SerializedName("time_start")
    @Expose
    var timeStart: String? = null

    @SerializedName("time_end")
    @Expose
    private var timeEnd: String? = null

    @SerializedName("rate")
    @Expose
    var rate: String? = null

    @SerializedName("teachers")
    @Expose
    var teachers: List<Teacher>? = null

    @SerializedName("rooms")
    @Expose
    var rooms: List<Room>? = null

    var isHideUnderline = false

    var tmStart: Long = 0
    var tmStop: Long = 0

    val isDayDelimiter: Boolean
        get() = timeEnd == "*"

    val thirdLine: String
        get() = if (lessonType == null || lessonType!!.isEmpty()) "" else "$lessonType в $lessonRoom"

    init {
        teachers = ArrayList()
        rooms = ArrayList()
    }

    fun getTimeEnd(): String {
        return timeEnd!!.substring(0, 5)
    }

    fun setTimeEnd(timeEnd: String) {
        this.timeEnd = timeEnd
    }
}
