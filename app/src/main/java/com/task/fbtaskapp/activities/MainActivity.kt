package com.task.fbtaskapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.task.fbtaskapp.adapters.TaskAdapter
import com.task.fbtaskapp.constants.AppConst
import com.task.fbtaskapp.databinding.ActivityMainBinding
import com.task.fbtaskapp.models.UserTask


class MainActivity : AppCompatActivity() {


    private var database: FirebaseDatabase = Firebase.database
    private var databaseReference: DatabaseReference = database.getReference(AppConst.USER_TASKS)

    private lateinit var taskAdapter: TaskAdapter
    private var taskArrayList: java.util.ArrayList<UserTask>? = null

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        listeners()
    }

    private fun initView() {

        taskArrayList = java.util.ArrayList()

        setUserTaskRecyclerView()

    }

    private fun setUserTaskRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        taskAdapter = TaskAdapter(this) { task: UserTask ->
            strickedTaskFromFirebaseList(task)
        }
        binding.rvTaskList.layoutManager = layoutManager
        binding.rvTaskList.adapter = taskAdapter
        taskAdapter.setUserTaskList(ArrayList())
    }

    private fun strickedTaskFromFirebaseList(task: UserTask) {

       /* databaseReference.child(task.taskId).removeValue().addOnSuccessListener {
            Toast.makeText(this@MainActivity, "One Task removed", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this@MainActivity, "Failed to remove Task", Toast.LENGTH_LONG).show()
        }*/

        databaseReference.child(task.taskId).setValue(task)
    }

    private fun listeners() {
        binding.tvAddTask.setOnClickListener {
            AddTaskActivity.openActivity(this@MainActivity)
        }
    }


    override fun onStart() {
        super.onStart()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                taskArrayList?.clear()

                for (postSnapshot in dataSnapshot.children) {
                    val task: UserTask? = postSnapshot.getValue(UserTask::class.java)
                    taskArrayList?.add(task!!)
                }
                taskArrayList?.reverse()
                taskAdapter.setUserTaskList(taskArrayList!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

}