package com.lambui.demomodular.ui.main

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.inSpans
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.lambui.demomodular.R
import com.lambui.demomodular.data.Assignment

class ExerciseAdapter : RecyclerView.Adapter<ExerciseViewHolder>() {
    private var items: List<Assignment> = emptyList()

    private var selected = mutableListOf<String>()

    var onItemSelect: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseViewHolder(
            layoutInflater.inflate(
                R.layout.item_list_exercise,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = items[position]
        val isSelected = selected.contains(item.id)
        holder.bindData(item, isSelected)
        holder.itemView.setOnClickListener {
            setSelect(holder.adapterPosition, item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submit(assignments: List<Assignment>, selected: List<String>) {
        items = assignments
        this.selected.clear()
        this.selected.addAll(selected)
        notifyDataSetChanged()
    }

    private fun setSelect(position: Int, item: Assignment) {
        if (selected.contains(item.id)) {
            selected.remove(item.id)
        } else {
            selected.add(item.id)
        }
        notifyItemChanged(position)
        onItemSelect?.invoke(item.id)
    }
}

class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var tvTitle: TextView
    private var tvDescription: TextView
    private var ivTick: ImageView

    init {
        tvTitle = itemView.findViewById(R.id.tvTitle)
        tvDescription = itemView.findViewById(R.id.tvDesc)
        ivTick = itemView.findViewById(R.id.ivTick)
    }

    fun bindData(item: Assignment, isSelected: Boolean) {
        tvTitle.text = item.title
        itemView.isSelected = isSelected
        ivTick.isGone = !isSelected
        getDescription(item)
        val alpha = if (item.isFuture && !isSelected) 0.5f else 1f
        itemView.alpha = alpha
    }

    private fun getDescription(item: Assignment) {
        val context = itemView.context
        when {
            item.isCompleted -> {
                tvDescription.setText(R.string.completed)
            }

            item.isMissed -> {
                val red = ContextCompat.getColor(context, R.color.red)
                val spannable = SpannableStringBuilder()
                    .inSpans(
                        ForegroundColorSpan(red),
                    ) {
                        append(context.getString(R.string.missed))
                    }.append(" • ")
                    .append(context.getString(R.string.x_exercises, item.exercisesCount ?: 0))
                tvDescription.text = spannable
            }

            else -> {
                tvDescription.text = context.getString(
                    R.string.x_exercises,
                    item.exercisesCount ?: 0
                )
            }
        }
    }
}