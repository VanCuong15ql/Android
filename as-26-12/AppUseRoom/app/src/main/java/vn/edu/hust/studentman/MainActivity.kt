package vn.edu.hust.studentman



import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.flow.collect


class MainActivity : AppCompatActivity(), SaveListener, EditListener {
    private lateinit var studentViewModel: StudentViewModel

    private val listFragment = ListFragment()
    private val addStudentFragment = ast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Kiểm tra quyền quản lý file trên Android 11+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            startActivity(intent)
        }
        // Khởi tạo StudentViewModel với ViewModelProvider
        Log.v("TAG", "start creat studentViewModel")
        val dao = StudentDatabase.getInstance(this).studentDao()
        Log.v("TAG", "stop here 1")
        val factory = StudentViewModelFactory(dao)
        Log.v("TAG", "stop here 2")
        studentViewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)
        Log.v("TAG", "stop here 3")
        Log.v("TAG", "end creat studentViewModel")
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Hiển thị ListFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fr_c, listFragment)
            .commit()

        // Quan sát thay đổi dữ liệu từ ViewModel
        lifecycleScope.launchWhenStarted {
            studentViewModel.students.collect { students ->
                Log.d("TAG", "Students loaded: ${students.size}")
            }
        }
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
                // Chuyển đến màn hình thêm sinh viên
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fr_c, addStudentFragment, "TAG")
                    .addToBackStack("TAG")
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun edit(name: String, id: String, position: Int) {
        // Lấy MSSV cũ từ danh sách hiện tại
        val oldMssv = studentViewModel.students.value[position].studentId

        // Cập nhật thông tin sinh viên
        studentViewModel.editStudent(name, id, oldMssv)
        Log.v("TAG", "edit begin in MainActivity")
    }

    override fun add(name: String, id: String) {
        // Thêm sinh viên mới
        studentViewModel.addStudent(name, id)
        Log.v("TAG", "add begin in MainActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}