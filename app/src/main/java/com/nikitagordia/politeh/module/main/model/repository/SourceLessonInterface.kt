package com.nikitagordia.politeh.module.main.model.repository

/**
 * Created by nikitagordia on 5/25/18.
 */

interface SourceLessonInterface {

    fun subscribeOnLesson(sub: SubscribeLessonInterface, id: Int)

    fun cancelLesson(sub: SubscribeLessonInterface)
}