package com.eduni.highschoolapp

enum class Course(val displayName: String) {
    SMX1("SMX 1"),
    SMX2("SMX 2"),
    DAM1("DAM 1"),
    DAM2("DAM 2");

    override fun toString(): String {
        return displayName
    }
}

object DataSource {

    val studentsList = ArrayList<Student>()

    fun addStudent(name: String, age: String, course: String) {
        studentsList.add(Student(name, age, course))
    }
}

class Student(
    val name: String,
    val age: String,
    val course: String
)