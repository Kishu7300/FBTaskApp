package com.task.fbtaskapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.fbtaskapp.databinding.CardItemLayoputBinding
import com.task.fbtaskapp.models.UserTask

class TaskAdapter(private val mContext: Context, private val onItemCheck: (task: UserTask) -> Any,  private val onItemClickForDetails: (task: UserTask) -> Any) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList: ArrayList<UserTask> = ArrayList()

    inner class TaskViewHolder(private val binding: CardItemLayoputBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userTask: UserTask, position: Int) {

            if(userTask.stricked){
                binding.tvTitle.visibility = View.GONE
            }else
                binding.tvTitle.visibility = View.VISIBLE

            binding.tvTitle.text = userTask.taskName

            binding.ckTask.setOnClickListener {
                userTask.stricked = binding.ckTask.isChecked
                onItemCheck(userTask)
            }

            binding.llRoot.setOnClickListener {
                onItemClickForDetails(userTask)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = CardItemLayoputBinding.inflate(LayoutInflater.from(parent.context))
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position], position)
    }


    fun setUserTaskList(list: java.util.ArrayList<UserTask>) {
        this.taskList.clear()
        this.taskList.addAll(list)
        this.notifyDataSetChanged()
    }
}