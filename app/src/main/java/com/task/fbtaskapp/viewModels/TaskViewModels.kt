package com.task.fbtaskapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.fbtaskapp.models.UserTask
import com.task.fbtaskapp.repository.TaskRepository
import com.task.fbtaskapp.utilities.Resource

class TaskViewModels(application: Application) : AndroidViewModel(application) {

    private var taskRepository: TaskRepository? = TaskRepository.getInstance()

    private var allTaskMutableLiveData: MutableLiveData<java.util.ArrayList<UserTask>> = MutableLiveData()
    var allTaskLiveData: LiveData<java.util.ArrayList<UserTask>> = allTaskMutableLiveData


    fun addNewTask(userTask: UserTask): Boolean {
        return taskRepository?.addTask(userTask) == true
    }


    fun getTaskList() {
        allTaskMutableLiveData.value = taskRepository?.getTaskList()!!
    }



    fun getTaskListData() : LiveData<java.util.ArrayList<UserTask>>{
        allTaskLiveData = allTaskMutableLiveData
        return allTaskLiveData
    }
}