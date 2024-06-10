package com.lambui.demomodular.ui.main

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lambui.demomodular.R
import com.lambui.demomodular.data.ScheduleItem
import com.lambui.demomodular.utils.dp
import org.joda.time.LocalDate
import org.joda.time.chrono.ISOChronology
import java.util.Locale

class ScheduleAdapter : ListAdapter<ScheduleItem, ScheduleViewHolder>(
    DIFF_CALLBACK
) {

    private val assignmentIds = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ScheduleViewHolder(
            layoutInflater.inflate(
                R.layout.item_list_calendar,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bindData(getItem(position), assignmentIds)
        holder.onExerciseItemSelect = {
            setSelect(it)
        }
    }

    private fun setSelect(id: String) {
        if (assignmentIds.contains(id)) {
            assignmentIds.remove(id)
        } else {
            assignmentIds.add(id)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ScheduleItem>() {

            override fun areItemsTheSame(
                oldItem: ScheduleItem,
                newItem: ScheduleItem
            ): Boolean {
                return oldItem.localDate == newItem.localDate
            }

            override fun areContentsTheSame(
                oldItem: ScheduleItem,
                newItem: ScheduleItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}


class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var tvDayOfWeek: TextView
    private var tvDayOfMonth: TextView
    private var rvItems: RecyclerView

    var onExerciseItemSelect: ((String) -> Unit)? = null

    init {
        tvDayOfWeek = itemView.findViewById(R.id.tvDayOfWeek)
        tvDayOfMonth = itemView.findViewById(R.id.tvDayOfMonth)
        rvItems = itemView.findViewById<RecyclerView?>(R.id.rvItems).apply {
            isNestedScrollingEnabled = false
            adapter = ExerciseAdapter().apply {
                this.onItemSelect = onExerciseItemSelect
            }
            itemAnimator = null
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val holder = parent.getChildViewHolder(view)
                    val position = holder.adapterPosition
                    if (position < ((parent.adapter?.itemCount ?: 0) - 1)) {
                        outRect.bottom = 8.dp
                    }
                }
            })
        }
    }

    fun bindData(scheduleItem: ScheduleItem, idsSelected: List<String>) {
        val localDate = scheduleItem.localDate
        val dayOfWeek = ISOChronology.getInstance().dayOfWeek()
        val isToday = localDate == LocalDate.now()
        itemView.isSelected = isToday
        tvDayOfWeek.text = dayOfWeek.getAsShortText(localDate.dayOfWeek, Locale.ENGLISH)
        tvDayOfMonth.text = localDate.dayOfMonth.toString()
        (rvItems.adapter as ExerciseAdapter).submit(
            scheduleItem.workout?.assignments ?: emptyList(),
            idsSelected
        )
    }
}