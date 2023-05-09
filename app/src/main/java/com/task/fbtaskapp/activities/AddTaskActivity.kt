package com.task.fbtaskapp.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.task.fbtaskapp.databinding.ActivityAddTaskBinding
import com.task.fbtaskapp.models.UserTask
import com.task.fbtaskapp.viewModels.TaskViewModels
import java.util.*
import kotlin.time.Duration.Companion.seconds


class AddTaskActivity : AppCompatActivity() {


    private var userTask: UserTask? = null
    private val CAMERA_REQUEST: Int = 1
    private val MY_CAMERA_REQUEST_CODE: Int = 11

    private val viewModel: TaskViewModels by lazy { ViewModelProvider(this)[TaskViewModels::class.java] }

    private val binding: ActivityAddTaskBinding by lazy {
        ActivityAddTaskBinding.inflate(
            layoutInflater
        )
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val resultUri: Intent? = result.data
                val uri = resultUri?.data.toString()
                userTask?.imageUrl = uri
                Toast.makeText(this, "uri?.toString() $uri", Toast.LENGTH_LONG).show()
            }
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

        userTask = UserTask()
        val spanString = SpannableString(binding.tvDesc.text)
        spanString.setSpan(UnderlineSpan(), 0, spanString.length, 0)
        spanString.setSpan(StyleSpan(Typeface.BOLD), 0, spanString.length, 0)
        spanString.setSpan(StyleSpan(Typeface.ITALIC), 0, spanString.length, 0)
        binding.tvDesc.setText(Html.fromHtml("<a href=$spanString</a>"));
      //  binding.tvDesc.setText(spanString)
    }

    private fun listeners() {

        binding.btnAdd.setOnClickListener {
            if (binding.tvTitle.text.isNotEmpty() || binding.tvDesc.text.isNotEmpty()) {
                userTask?.stricked = false
                userTask?.desc = binding.tvDesc.text.toString()
                userTask?.taskId = Date().time.seconds.inWholeSeconds.toString()
                userTask?.taskName = binding.tvTitle.text.toString()
                if (!viewModel.addNewTask(userTask!!)) {
                    Toast.makeText(this@AddTaskActivity, "New Task Added", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@AddTaskActivity,
                        "New Task Failed to Add",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.btnPhoto.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    MY_CAMERA_REQUEST_CODE
                )
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                resultLauncher.launch(cameraIntent)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

}