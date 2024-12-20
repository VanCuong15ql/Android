package vn.edu.hust.studentman


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log

import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity(), SaveListener, EditListener {
    public lateinit var dbHelper: StudentDbHelper

    val LF = ListFragment()
    val AST = ast()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        Log.v("TAG","call dbHelper")
        dbHelper = StudentDbHelper(this)
        dbHelper.writableDatabase
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar) // Thiết lập Toolbar làm ActionBar
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fr_c, LF)
        fragmentTransaction.commit()

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
//                findNavController(R.id.fr_c).navigate(R.id.action_listFragment_to_ast)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fr_c, AST, "TAG")
                    .addToBackStack("TAG")
                    .commit()

//        addStudentLauncher.launch(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun edit(name: String, id: String, position: Int) {
        LF.editStudent(name, id, position)
        Log.v("TAG", "edit begin in MainActivity")
    }
    override fun add(name: String,id: String){
        LF.addStudent(name,id)
        Log.v("TAG", "add begin in MainActivity")
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}