package com.nikitagordia.politeh.module.group.view.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nikitagordia.politeh.R
import com.nikitagordia.politeh.module.group.model.data.Group
import com.nikitagordia.politeh.module.main.view.MainActivity
import com.nikitagordia.politeh.util.HashColor
import com.nikitagordia.politeh.util.SharedPreferencesManager

class GroupAdapter(private val cont : Activity, private val coordinator: CoordinatorLayout, private val resources: Resources) : RecyclerView.Adapter<GroupAdapter.GroupHolder>(){

    private val EXTRA_SELECTED_ID = "com.nikitagordia.politeh.module.group.view.list.GroupAdapter.selectedId"
    private val EXTRA_SELECTED_NAME = "com.nikitagordia.politeh.module.group.view.list.GroupAdapter.selectedName"

    val list: MutableList<Group> = mutableListOf()
    private var filter = mutableListOf<Group>()

    private var query = ""
    private var selectedId = -1
    private var selectedPos = -1

    fun add(nw: List<Group>) {
        val sz = list.size
        list.addAll(nw)
        refreshFilter()
        notifyItemRangeInserted(sz, nw.size)
    }

    private fun refreshFilter() {
        filter.clear()
        for (i in list) if (valid(i.groupFullName)) filter.add(i)
        try {
            filter.sortWith(Comparator<Group> { p0, p1 ->
                val s1 = p0?.groupFullName ?: return@Comparator -1
                val s2 = p1?.groupFullName ?: return@Comparator 1
                if (s1.first().isDigit() && s2.first().isDigit()) return@Comparator s1.first().compareTo(s2.first())
                if (s1.first().isDigit()) return@Comparator 1
                if (s2.first().isDigit()) return@Comparator -1
                s1.compareTo(s2)
            })
        } catch (e: Exception) {
            Log.e("mytg", "Sorting tim\n${e.message}")
        }
        filter.forEachIndexed { index: Int, group: Group ->  if (group.groupId == selectedId) {
            selectedPos = index
            return@forEachIndexed
        } }
        notifyDataSetChanged()
    }

    fun updateQuery(nw: String) {
        query = nw
        refreshFilter()
    }

    fun clear() {
        list.clear()
        filter.clear()
        selectedPos = -1
        notifyDataSetChanged()
    }

    private fun valid(str: String?): Boolean {
        if (str == null) return false
        if (query.isEmpty()) return true
        return str.toLowerCase().contains(query)
    }

    fun onSaveInstance(b: Bundle) {
        if (selectedId != -1) {
            b.putInt(EXTRA_SELECTED_ID, selectedId)
            if (selectedPos != -1) b.putString(EXTRA_SELECTED_NAME, filter[selectedPos].groupFullName)
        }
    }

    fun onRestoreInstance(b: Bundle) {
        val id = b.get(EXTRA_SELECTED_ID) as Int?
        val name = b.get(EXTRA_SELECTED_NAME) as String?
        if (id != null) {
            selectedId = id
            if (name != null) Snackbar.make(coordinator, resources.getString(R.string.selected, name), Snackbar.LENGTH_INDEFINITE).setAction(R.string.done, {  }).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = parent?.run { GroupHolder(LayoutInflater.from(cont).inflate(R.layout.item_group, this, false)) }

    override fun getItemCount() = filter.size

    override fun onBindViewHolder(holder: GroupHolder?, position: Int) = holder?.run { holder.bind(filter[position], position) } ?: Unit

    inner class GroupHolder(private val v : View) : RecyclerView.ViewHolder(v) {

        val background = v.findViewById<View>(R.id.circle)
        val tv = v.findViewById<TextView>(R.id.name)
        val selected = v.findViewById<ImageView>(R.id.selected)

        fun bind(g : Group, pos: Int) {
            g.groupFullName?.apply {
                background.background = HashColor.getShapeDrawable(this)
                tv.text = this
            }
            g.groupId?.apply {
                if (this == selectedId) selected.visibility = View.VISIBLE else selected.visibility = View.INVISIBLE
                v.setOnClickListener {
                    g.groupId?.apply {
                        selectedId = this
                        if (selectedPos != -1) notifyItemChanged(selectedPos)
                        selectedPos = pos
                        notifyItemChanged(pos)
                        Snackbar.make(coordinator, resources.getString(R.string.selected, g.groupFullName), Snackbar.LENGTH_INDEFINITE).setAction(R.string.done, {
                            SharedPreferencesManager.setGroupId(selectedId, cont)
                            cont.finish()
                            cont.startActivity(Intent(cont, MainActivity::class.java))
                        }).show()
                    }
                }
            }
        }
    }
}