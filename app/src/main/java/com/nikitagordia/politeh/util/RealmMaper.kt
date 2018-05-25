package com.nikitagordia.politeh.util

import com.nikitagordia.politeh.module.main.model.data.db.LessonDB
import com.nikitagordia.politeh.module.main.model.data.db.RoomDB
import com.nikitagordia.politeh.module.main.model.data.db.TeacherDB
import com.nikitagordia.politeh.module.main.model.data.remote.Lesson
import com.nikitagordia.politeh.module.main.model.data.remote.Room
import com.nikitagordia.politeh.module.main.model.data.remote.Teacher
import io.realm.RealmList

/**
 * Created by nikitagordia on 05.03.18.
 */

object RealmMaper {

    fun map(f: Lesson): LessonDB {
        val t = LessonDB()

        t.lessonId = f.lessonId
        t.groupId = f.groupId
        t.dayNumber = f.dayNumber
        t.dayName = f.dayName
        t.lessonName = f.lessonName
        t.lessonFullName = f.lessonFullName
        t.lessonNumber = f.lessonNumber
        t.lessonRoom = f.lessonRoom
        t.lessonType = f.lessonType
        t.teacherName = f.teacherName
        t.lessonWeek = f.lessonWeek
        t.timeStart = f.timeStart
        t.timeEnd = f.getTimeEnd()
        t.rate = f.rate

        if (f.teachers != null) {
            val res = mutableListOf<TeacherDB>()
            f.teachers?.forEach { res.add(map(it)) }
            t.teachers = RealmList(*res.toTypedArray())
        }

        if (f.rooms != null) {
            val res = mutableListOf<RoomDB>()
            f.rooms?.forEach { res.add(map(it)) }
            t.rooms = RealmList(*res.toTypedArray())
        }
        return t
    }

    fun map(f: Teacher): TeacherDB {
        val t = TeacherDB()

        t.teacherId = f.teacherId
        t.teacherName = f.teacherName
        t.teacherFullName = f.teacherFullName
        t.teacherShortName = f.teacherShortName
        t.teacherUrl = t.teacherUrl
        t.teacherRating = f.teacherRating

        return t
    }

    fun map(f: Room): RoomDB {
        val t = RoomDB()

        t.roomId = f.roomId
        t.roomName = f.roomName
        t.roomLatitude = f.roomLatitude
        t.roomLongitude = f.roomLongitude

        return t
    }

    fun map(t: LessonDB): Lesson {
        val r = Lesson()

        r.lessonId = t.lessonId
        r.groupId = t.groupId
        r.dayNumber = t.dayNumber
        r.dayName = t.dayName
        r.lessonName = t.lessonName
        r.lessonFullName = t.lessonFullName
        r.lessonNumber = t.lessonNumber
        r.lessonRoom = t.lessonRoom
        r.lessonType = t.lessonType
        r.teacherName = t.teacherName
        r.lessonWeek = t.lessonWeek
        r.timeStart = t.timeStart
        r.setTimeEnd(t.timeEnd!!)
        r.rate = t.rate

        if (t.teachers != null) {
            val res = mutableListOf<Teacher>()
            t.teachers?.forEach { res.add(map(it)) }
            r.teachers = res.toList()
        }

        if (t.rooms != null) {
            val res = mutableListOf<Room>()
            t.rooms?.forEach { res.add(map(it)) }
            r.rooms = res.toList()
        }

        return r
    }

    fun map(t: TeacherDB): Teacher {
        val r = Teacher()

        r.teacherId = t.teacherId
        r.teacherName = t.teacherName
        r.teacherFullName = t.teacherFullName
        r.teacherShortName = t.teacherShortName
        r.teacherUrl = r.teacherUrl
        r.teacherRating = t.teacherRating

        return r
    }

    fun map(t: RoomDB): Room {
        val r = Room()

        r.roomId = t.roomId
        r.roomName = t.roomName
        r.roomLatitude = t.roomLatitude
        r.roomLongitude = t.roomLongitude

        return r
    }
}
