package vn.edu.hust.studentman

import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    val students: MutableList<StudentModel> = mutableListOf()

}
