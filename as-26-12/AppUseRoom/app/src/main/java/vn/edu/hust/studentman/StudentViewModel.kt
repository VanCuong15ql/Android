package vn.edu.hust.studentman

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentViewModel(private val studentDao: StudentDAO) : ViewModel() {
    private val _students = MutableStateFlow<List<StudentModel>>(emptyList())
    val students: StateFlow<List<StudentModel>> get() = _students

    init {
        loadStudents()
    }

    // Tải danh sách sinh viên từ cơ sở dữ liệu
    private fun loadStudents() {
        viewModelScope.launch {
            _students.value = studentDao.getAll().map {
                StudentModel(it.name, it.mssv)
            }
        }
    }

    // Thêm sinh viên mới
    fun addStudent(name: String, mssv: String) {
        viewModelScope.launch {
            val student = Student(name = name, mssv = mssv)
            studentDao.insert(student)
            loadStudents() // Tải lại danh sách
        }
    }

    // Sửa thông tin sinh viên
    fun editStudent(name: String, newMssv: String, oldMssv: String) {
        viewModelScope.launch {
            val rowsUpdated = studentDao.updateByMssv(name, newMssv, oldMssv)
            if (rowsUpdated > 0) {
                loadStudents() // Tải lại danh sách sau khi cập nhật
            } else {
                Log.d("TAG", "No student found with MSSV: $oldMssv")
            }
        }
    }

    // Xóa sinh viên
    fun removeStudent(mssv: String) {
        viewModelScope.launch {
            studentDao.deleteByMssv(mssv)
            loadStudents() // Tải lại danh sách
        }
    }
}
