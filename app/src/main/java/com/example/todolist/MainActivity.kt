package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val taskCol get() = db.collection("tasks")

    private lateinit var taskContainer: LinearLayout
    private lateinit var btnAdd: ImageButton
    private lateinit var llCompletedBtn: LinearLayout
    private lateinit var tvCompletedText: TextView
    private lateinit var ivDropdown: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        taskContainer     = findViewById(R.id.llTaskContainer)
        btnAdd            = findViewById(R.id.btnAddMain)
        llCompletedBtn    = findViewById(R.id.llCompletedTasks)
        tvCompletedText   = findViewById(R.id.tvCompletedText)
        ivDropdown        = findViewById(R.id.ivDropdown)

        btnAdd.setOnClickListener { openTaskScreen(null) }

        val openCompleted = {
            val intent = Intent(this, CompletedTaskActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_up, R.anim.fade_out)
        }

        llCompletedBtn.setOnClickListener { openCompleted() }
        tvCompletedText.setOnClickListener { openCompleted() }
        ivDropdown.setOnClickListener { openCompleted() }

        listenForIncompleteTasks()
    }

    private fun listenForIncompleteTasks() {
        taskCol.whereEqualTo("completed", false)
            .addSnapshotListener { snap, _ ->
                snap ?: return@addSnapshotListener
                taskContainer.removeAllViews()
                val inflater = LayoutInflater.from(this)
                for (doc in snap.documents) {
                    val task = doc.toObject(Task::class.java) ?: continue
                    task.id = doc.id
                    taskContainer.addView(createTaskView(inflater, task))
                }
            }
    }

    private fun createTaskView(inflater: LayoutInflater, task: Task): View {
        val row = inflater.inflate(R.layout.item_task, taskContainer, false)

        val cb    = row.findViewById<CheckBox>(R.id.cbTask)
        val tv    = row.findViewById<TextView >(R.id.tvTaskText)
        val ivEd  = row.findViewById<ImageView>(R.id.ivEdit)
        val ivDel = row.findViewById<ImageView>(R.id.ivDelete)

        tv.text      = task.description
        cb.isChecked = false

        cb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) taskCol.document(task.id).update("completed", true)
        }

        ivEd.setOnClickListener { openTaskScreen(task) }
        ivDel.setOnClickListener { taskCol.document(task.id).delete() }

        return row
    }

    private fun openTaskScreen(task: Task?) {
        startActivity(Intent(this, TaskActivity::class.java).apply {
            putExtra("taskId", task?.id)
            putExtra("desc",   task?.description)
        })
    }
}
