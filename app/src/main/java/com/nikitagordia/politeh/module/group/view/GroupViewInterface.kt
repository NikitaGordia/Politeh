package com.nikitagordia.politeh.module.group.view

import com.nikitagordia.politeh.repository.remote.model.Group

/**
 * Created by nikitagordia on 5/9/18.
 */

interface GroupViewInterface {

    fun onDataGroup(list: List<Group>)
}