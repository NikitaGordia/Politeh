package com.nikitagordia.politeh.module.group.view.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.repository.remote.model.Group
import com.nikitagordia.politeh.util.HashColor
import kotlinx.android.synthetic.main.activity_group.view.*

class GroupAdapter(private val cont : Context) : RecyclerView.Adapter<GroupAdapter.GroupHolder>(){

    private val list = mutableListOf<Group>()
    private var filter = mutableListOf<Group>()

    var query: String = ""

    fun add(nw: List<Group>) {
        list.addAll(nw)
        refreshFilter()
    }

    private fun refreshFilter() {
        filter.clear()
        for (i in list) if (valid(i.groupFullName)) filter.add(i)
        filter.sortWith(object : Comparator<Group> {
            override fun compare(p0: Group?, p1: Group?): Int {
                val s1 = p0?.groupFullName
                val s2 = p1?.groupFullName
                if (s1 == null || s2 == null) return -1
                if (s1.first().isDigit() && s2.first().isDigit()) return s1.first().compareTo(s2.first())
                if (s1.first().isDigit()) return 1
                if (s2.first().isDigit()) return -1
                return s1.compareTo(s2)
            }
        })
        notifyDataSetChanged()
    }

    fun updateQuery(nw: String) {
        query = nw
        refreshFilter()
    }

    private fun valid(str: String?): Boolean {
        if (str == null) return false
        if (query.isEmpty()) return true
        return str.toLowerCase().contains(query)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = parent?.run { GroupHolder(LayoutInflater.from(cont).inflate(R.layout.item_group, this, false)) }

    override fun getItemCount() = filter.size

    override fun onBindViewHolder(holder: GroupHolder?, position: Int) = holder?.run { holder.bind(filter[position]) } ?: Unit

    class GroupHolder(private val v : View) : RecyclerView.ViewHolder(v) {

        fun bind(g : Group) {
            g.groupFullName?.apply {
                v.findViewById<View>(R.id.circle).background = HashColor.getShapeDrawable(this)
                v.findViewById<TextView>(R.id.name).text = this
            }
        }
    }
}