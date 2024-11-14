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
            val nameInput = binding.nameTextInput.editText?.text.toString().trim()
            val ageText = binding.ageTextInput.editText?.text.toString().trim()

            if (nameInput.isEmpty()) {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val age = ageText.toIntOrNull()
            if (age == null || age <= 0) {
                Toast.makeText(
                    this,
                    "La edad debe ser un número válido y mayor a cero",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val name = "Nombre: " + nameInput.toLowerCase().capitalize()
            val ageShow = "Edad: $age años"
            val courseString = binding.courseSelectionSpinner.selectedItem.toString()
            val course = when (courseString) {
                Course.SMX2.displayName -> Course.SMX2
                Course.DAM1.displayName -> Course.DAM1
                Course.DAM2.displayName -> Course.DAM2
                else -> Course.SMX1
            }

            DataSource.addStudent(name, ageShow, course.displayName)

            binding.nameTextInput.editText?.text = null
            binding.ageTextInput.editText?.text = null

            binding.nameTextInput.editText?.clearFocus()
            binding.ageTextInput.editText?.clearFocus()

            Toast.makeText(this, "Alumno añadido correctamente", Toast.LENGTH_SHORT).show()
        }

        binding.listStudentsButton.setOnClickListener {
            val showStudentsIntent = Intent(this, ShowStudents::class.java)
            startActivity(showStudentsIntent)
        }

    }
}