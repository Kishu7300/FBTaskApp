package com.task.fbtaskapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.task.fbtaskapp.databinding.ActivityAddTaskBinding
import com.task.fbtaskapp.models.UserTask
import com.task.fbtaskapp.viewModels.TaskViewModels
import java.util.*
import kotlin.time.Duration.Companion.seconds

class AddTaskActivity : AppCompatActivity() {


    private val viewModel: TaskViewModels by lazy { ViewModelProvider(this)[TaskViewModels::class.java] }

    private val binding: ActivityAddTaskBinding by lazy {
        ActivityAddTaskBinding.inflate(
            layoutInflater
        )
    }


    companion object {
        fun openActivity(activity: Activity) {
            val intent = Intent(activity, AddTaskActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        listeners()
    }

    private fun initView() {

    }

    private fun listeners() {

        binding.btnAdd.setOnClickListener {
            if (binding.tvTitle.text.isNotEmpty() || binding.tvDesc.text.isNotEmpty()) {
                val userTask = UserTask()
                userTask.stricked = false
                userTask.taskId = Date().time.seconds.inWholeSeconds.toString()
                userTask.taskName = binding.tvTitle.text.toString()
                if(!viewModel.addNewTask(userTask)){
                    Toast.makeText(this@AddTaskActivity, "New Task Added", Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this@AddTaskActivity, "New Task Failed to Add", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}