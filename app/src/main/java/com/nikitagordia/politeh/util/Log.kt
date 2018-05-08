package com.nikitagordia.politeh.util

import android.app.Activity
import android.util.Log

/**
 * Created by nikitagordia on 5/8/18.
 */

fun Activity.log(msg : String) = Log.d(Activity::class.java.name, msg)