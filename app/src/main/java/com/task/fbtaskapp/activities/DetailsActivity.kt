package com.task.fbtaskapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.task.fbtaskapp.databinding.ActivityDetailsBinding
import com.task.fbtaskapp.models.UserTask
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    private val binding: ActivityDetailsBinding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }



    companion object {
        fun openActivity(activity: Activity, task: UserTask) {
            val intent = Intent(activity, DetailsActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("Task", task)
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

        val userTask = intent.extras?.getSerializable("Task") as UserTask

            userTask?.let {
                val pattern = "MM-dd-yyyy hh:mm aa"
                val simpleDateFormat = SimpleDateFormat(pattern)
               val date = simpleDateFormat.format(Date(userTask.taskId.toLong()))
            binding.tvTime.text = date
            binding.tvTitle.text = userTask.taskName
            binding.tvDesc.text = userTask.desc

        //    Glide.with(this).load("").into(binding.ivImage)
        }
    }
}