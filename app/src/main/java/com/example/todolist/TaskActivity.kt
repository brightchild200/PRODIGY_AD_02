package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TaskActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val taskCol get() = db.collection("tasks")

    private lateinit var etTaskInput: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

    private var taskId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_layout)

        etTaskInput = findViewById(R.id.etTaskInput)
        btnAdd = findViewById(R.id.btnAdd)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)

        // Back button click listener
        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            goToMain()
        }


        taskId = intent.getStringExtra("taskId")
        val desc = intent.getStringExtra("desc") ?: ""
        etTaskInput.setText(desc)

        if (taskId == null) {
            btnEdit.isEnabled = false
            btnDelete.isEnabled = false
        } else {
            btnAdd.isEnabled = false
        }

        btnAdd.setOnClickListener {
            val newTask = hashMapOf(
                "description" to etTaskInput.text.toString(),
                "completed" to false
            )
            taskCol.add(newTask).addOnSuccessListener {
                goToMain()
            }
        }

        btnEdit.setOnClickListener {
            taskId?.let {
                taskCol.document(it).update("description", etTaskInput.text.toString())
                    .addOnSuccessListener {
                        goToMain()
                    }
            }
        }

        btnDelete.setOnClickListener {
            taskId?.let {
                taskCol.document(it).delete().addOnSuccessListener {
                    goToMain()
                }
            }
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
