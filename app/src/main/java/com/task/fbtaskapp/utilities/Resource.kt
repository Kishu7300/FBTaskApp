package com.task.fbtaskapp.utilities

import com.task.fbtaskapp.models.UserTask
import java.util.ArrayList

sealed class Resource<T : Any> {

    class Success<T : Any>(val data: java.util.ArrayList<UserTask>?) : Resource<T>()
    class Error<T : Any>(val code: Int, val message: String?) : Resource<T>()
}