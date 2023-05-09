package com.task.fbtaskapp.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.task.fbtaskapp.constants.AppConst
import com.task.fbtaskapp.models.UserTask

class TaskRepository {

    private var database: FirebaseDatabase = Firebase.database
    private var databaseReference: DatabaseReference = database.getReference(AppConst.USER_TASKS)

    companion object{
        private var taskRepository : TaskRepository? = null

        fun getInstance() : TaskRepository?{
            if(taskRepository == null){
                taskRepository = TaskRepository()
            }
            return taskRepository
        }
    }


    fun addTask(userTask: UserTask) : Boolean{
        var status = false
        databaseReference.child(userTask.taskId).setValue(userTask).addOnSuccessListener {
            status = true
        }.addOnFailureListener {
            status = false
        }
        return status
    }


    public fun getTaskList() : java.util.ArrayList<UserTask>{
        val taskArrayList = java.util.ArrayList<UserTask>()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (postSnapshot in dataSnapshot.children) {
                    val task: UserTask? = postSnapshot.getValue(UserTask::class.java)
                    taskArrayList.add(task!!)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        return taskArrayList
    }

}