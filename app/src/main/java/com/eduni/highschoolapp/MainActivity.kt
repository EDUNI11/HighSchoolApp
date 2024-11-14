package com.eduni.highschoolapp

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.eduni.highschoolapp.databinding.ActivityMainBinding
import android.content.Intent
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.courseSelectionSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.courses)
        )

        binding.addStudentButton.setOnClickListener {
            val name = "Nombre: " + binding.nameTextInput.editText?.text.toString()
            val age = "Edad: " + binding.ageTextInput.editText?.text.toString()
            val courseString = binding.courseSelectionSpinner.selectedItem.toString()

            var course = Course.SMX1

            when (courseString) {
                Course.SMX2.displayName -> {
                    course = Course.SMX2
                }
                Course.DAM1.displayName -> {
                    course = Course.DAM1
                }
                Course.DAM2.displayName -> {
                    course = Course.DAM2
                }
            }

            if (binding.nameTextInput.editText?.text.toString().isEmpty()) {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
            if (binding.ageTextInput.editText?.text.toString().isEmpty()) {
                Toast.makeText(this, "La edad no puede estar vacía", Toast.LENGTH_SHORT).show()
            }

            if (binding.nameTextInput.editText?.text != null && binding.ageTextInput.editText?.text != null) {
                DataSource.addStudent(name, age, course.displayName)
                Toast.makeText(this, "Alumno añadido correctamente", Toast.LENGTH_SHORT).show()
                binding.nameTextInput.editText?.text = null
                binding.ageTextInput.editText?.text = null
            }
        }

        binding.listStudentsButton.setOnClickListener {
            val showStudentsIntent = Intent(this, ShowStudents::class.java)
            startActivity(showStudentsIntent)
        }

    }
}