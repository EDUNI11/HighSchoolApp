package com.eduni.highschoolapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.eduni.highschoolapp.databinding.ActivityShowStudentsBinding
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShowStudents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding: ActivityShowStudentsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_show_students)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainStudents)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val studentsList = DataSource.studentsList
        val studentsListFiltered = ArrayList<Student>()
        var adapter = CustomAdapter(studentsList)

        fun getStudentsFiltered(courseFilter: Course) {
            studentsListFiltered.clear()
            for (student in studentsList) {
                val course = student.course
                if (course == courseFilter.displayName) {
                    studentsListFiltered.add(student)
                }
            }
            adapter = CustomAdapter(studentsListFiltered)
            recyclerview.adapter = adapter
            return

        }

        binding.courseSelectionSpinner2.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.courses)
        )

        getStudentsFiltered(Course.SMX1)

        binding.courseSelectionSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (binding.courseSelectionSpinner2.selectedItem) {
                    Course.SMX1.displayName -> {
                        getStudentsFiltered(Course.SMX1)
                    }
                    Course.SMX2.displayName -> {
                        getStudentsFiltered(Course.SMX2)
                    }
                    Course.DAM1.displayName -> {
                        getStudentsFiltered(Course.DAM1)
                    }
                    Course.DAM2.displayName -> {
                        getStudentsFiltered(Course.DAM2)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.returnButton.setOnClickListener {
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
    }

}