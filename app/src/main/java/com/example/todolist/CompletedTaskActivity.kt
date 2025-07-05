package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CompletedTaskActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val taskCol get() = db.collection("tasks")

    private lateinit var taskContainer: LinearLayout
    private lateinit var tvGoBack: TextView
    private lateinit var btnAddTask: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.completed_task)

        taskContainer = findViewById(R.id.llCompletedTaskContainer)
        tvGoBack = findViewById(R.id.tvGoBack)
        btnAddTask = findViewById(R.id.btnAddCompleted)

        // Open MainActivity with slide-down transition
        tvGoBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.slide_down)
        }

        // Open TaskActivity for adding a new task
        btnAddTask.setOnClickListener {
            startActivity(Intent(this, TaskActivity::class.java))
        }

        loadCompletedTasks()
    }

    private fun loadCompletedTasks() {
        taskCol.whereEqualTo("completed", true)
            .addSnapshotListener { snap, _ ->
                snap ?: return@addSnapshotListener
                taskContainer.removeAllViews()

                val inflater = LayoutInflater.from(this)
                for (doc in snap.documents) {
                    val task = doc.toObject(Task::class.java) ?: continue
                    task.id = doc.id
                    taskContainer.addView(createCompletedTaskView(inflater, task))
                }
            }
    }

    private fun createCompletedTaskView(inflater: LayoutInflater, task: Task): LinearLayout {
//        val row = inflater.inflate(R.layout.completed_task, taskContainer, false) as LinearLayout
        val row = inflater.inflate(R.layout.item_completed_task, taskContainer, false) as LinearLayout


        val cb = row.findViewById<CheckBox>(R.id.cbCompletedTask)
        val tv = row.findViewById<TextView>(R.id.tvCompletedTaskText)

        cb.isChecked = true
        tv.text = task.description

        cb.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                taskCol.document(task.id).update("completed", false)
            }
        }

        return row
    }
}
