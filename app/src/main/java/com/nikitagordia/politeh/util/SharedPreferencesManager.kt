package com.nikitagordia.politeh.util

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by nikitagordia on 03.03.18.
 */

object SharedPreferencesManager {

    private val GROUP_ID = "groupId"

    fun getGroupId(context: Context): Int {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(GROUP_ID, -1)
    }

    fun setGroupId(id: Int, context: Context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(GROUP_ID, id)
                .apply()
    }
}
