package com.nikitagordia.politeh.repository.remote

import com.nikitagordia.politeh.repository.remote.model.Group


/**
 * Created by nikitagordia on 5/9/18.
 */

interface SubscriberInterface {

    fun onDataGroup(list: List<Group>)
}