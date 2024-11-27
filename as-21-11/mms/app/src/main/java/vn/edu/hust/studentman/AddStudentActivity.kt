package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        // Implement logic to add a new student
        // Khai báo các thành phần giao diện
        val etStudentName = findViewById<EditText>(R.id.et_student_name)
        val etStudentId = findViewById<EditText>(R.id.et_student_id)
        val btnSave = findViewById<Button>(R.id.btn_save)

        // Xử lý sự kiện nhấn nút Save
        btnSave.setOnClickListener {
            val studentName = etStudentName.text.toString().trim()
            val studentId = etStudentId.text.toString().trim()

            // Kiểm tra nếu các trường không trống
            if (studentName.isNotEmpty() && studentId.isNotEmpty()) {
                // Tạo đối tượng StudentModel mới
                val newStudent = StudentModel(studentName, studentId)

                // Trả kết quả về MainActivity
                val resultIntent = Intent()
                resultIntent.putExtra("new_student", newStudent) // Gửi thông tin sinh viên mới
                setResult(RESULT_OK, resultIntent)

                // Đóng AddStudentActivity và quay lại MainActivity
                finish()
            } else {
                // Hiển thị thông báo nếu các trường không hợp lệ
                if (studentName.isEmpty()) {
                    etStudentName.error = "Please enter a valid name"
                }
                if (studentId.isEmpty()) {
                    etStudentId.error = "Please enter a valid student ID"
                }
            }
        }
    }
}