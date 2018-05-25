package com.nikitagordia.politeh.module.main.model.repository

import com.nikitagordia.politeh.module.main.model.data.remote.Lesson

/**
 * Created by nikitagordia on 5/25/18.
 */

interface MutableSourceLessonInterface : SourceLessonInterface{

    fun updateData(list: List<Lesson>)
}