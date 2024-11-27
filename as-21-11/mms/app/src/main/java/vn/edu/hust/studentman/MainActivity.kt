package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private lateinit var students: MutableList<StudentModel>

  private val addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    if (result.resultCode == RESULT_OK) {
      val newStudent = result.data?.getSerializableExtra("new_student") as? StudentModel
      newStudent?.let {
        students.add(it)
          studentAdapter.notifyDataSetChanged()
      }
    }
  }
    private val editStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedName = result.data?.getStringExtra("updated_name")
            val updatedId = result.data?.getStringExtra("updated_id")
            val position = result.data?.getIntExtra("position", -1)

            if (position != null && position >= 0 && updatedName != null && updatedId != null) {
                students[position].studentName = updatedName
                students[position].studentId = updatedId
                studentAdapter.notifyDataSetChanged() // Cập nhật ListView
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar) // Thiết lập Toolbar làm ActionBar

    students = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "S V002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    )

    studentAdapter = StudentAdapter(this, students)

    val listview = findViewById<ListView>(R.id.list_view_students)
    listview.adapter = studentAdapter

    // Đăng ký context menu cho RecyclerView
    registerForContextMenu(listview)
  }
  // Tạo OptionMenu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  // Xử lý chọn mục trong OptionMenu
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_add_new -> {
        val intent = Intent(this, AddStudentActivity::class.java)
        addStudentLauncher.launch(intent)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  // Tạo ContextMenu
  override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
  }

  // Xử lý chọn mục trong ContextMenu
  override fun onContextItemSelected(item: MenuItem): Boolean {
      val info = item.menuInfo as? AdapterView.AdapterContextMenuInfo
      val position = info?.position ?: return super.onContextItemSelected(item)

      return when (item.itemId) {
          R.id.context_edit -> {
              val intent = Intent(this, EditStudentActivity::class.java)
              intent.putExtra("student_name", students[position].studentName)
              intent.putExtra("student_id", students[position].studentId)
              intent.putExtra("position", position)
              editStudentLauncher.launch(intent)
              true
          }
          R.id.context_remove -> {
              students.removeAt(position)
              studentAdapter.notifyDataSetChanged() // Cập nhật lại ListView
              Toast.makeText(this, "Student removed", Toast.LENGTH_SHORT).show()
              true
          }
          else -> super.onContextItemSelected(item)
      }
  }
}