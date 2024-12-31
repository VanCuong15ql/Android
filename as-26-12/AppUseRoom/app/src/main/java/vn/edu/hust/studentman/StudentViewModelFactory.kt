package vn.edu.hust.studentman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudentViewModelFactory(private val studentDao: StudentDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            return StudentViewModel(studentDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}