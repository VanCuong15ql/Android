package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)
        // Implement logic to edit student
        val editName = findViewById<EditText>(R.id.ed_student_name)
        val editId = findViewById<EditText>(R.id.ed_student_id)
        val saveButton = findViewById<Button>(R.id.btn_c)

        // Lấy thông tin sinh viên từ Intent
        val studentName = intent.getStringExtra("student_name")
        val studentId = intent.getStringExtra("student_id")
        val position = intent.getIntExtra("position", -1)

        // Hiển thị thông tin sinh viên để chỉnh sửa
        editName.setText(studentName)
        editId.setText(studentId)

        // Xử lý khi người dùng nhấn nút Save
        saveButton.setOnClickListener {
            val updatedName = editName.text.toString().trim()
            val updatedId = editId.text.toString().trim()

            if (updatedName.isEmpty() || updatedId.isEmpty()) {
                Toast.makeText(this, "Name and ID cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Trả dữ liệu đã chỉnh sửa về MainActivity
                val resultIntent = intent
                resultIntent.putExtra("updated_name", updatedName)
                resultIntent.putExtra("updated_id", updatedId)
                resultIntent.putExtra("position", position)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}