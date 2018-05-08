package com.nikitagordia.politeh.module.group.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.module.group.model.Group
import kotlinx.android.synthetic.main.item_group.view.*

/**
 * Created by nikitagordia on 5/8/18.
 */

class GroupAdapter(val cont : Context) : RecyclerView.Adapter<GroupAdapter.GroupHolder>(){

    private val list = mutableListOf<Group>()

    fun add(g : Group) {
        list.add(g)
        notifyItemInserted(list.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = parent?.run { GroupHolder(LayoutInflater.from(cont).inflate(R.layout.item_group, this, false)) }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: GroupHolder?, position: Int) = holder?.run { holder.bind(list[position]) } ?: Unit

    class GroupHolder(v : View) : RecyclerView.ViewHolder(v) {

        fun bind(g : Group) {

        }
    }
}