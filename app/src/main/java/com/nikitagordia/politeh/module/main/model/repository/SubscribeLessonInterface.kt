package com.nikitagordia.politeh.module.main.model.repository

import com.nikitagordia.politeh.module.main.model.data.Lesson

/**
 * Created by nikitagordia on 5/25/18.
 */

interface SubscribeLessonInterface {

    fun onDataLesson(list: List<Lesson>?)
}