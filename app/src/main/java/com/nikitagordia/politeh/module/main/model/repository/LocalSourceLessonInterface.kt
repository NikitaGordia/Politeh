package com.nikitagordia.politeh.module.main.model.repository

import android.content.Context
import com.nikitagordia.politeh.module.main.model.data.remote.Lesson

/**
 * Created by nikitagordia on 5/25/18.
 */

interface LocalSourceLessonInterface : SourceLessonInterface{

    fun updateData(list: List<Lesson>)
}