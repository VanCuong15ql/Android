package vn.edu.hust.studentman

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.activityViewModels

class ListFragment : Fragment() {
    private val studentViewModel: StudentViewModel by activityViewModels()
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var students: MutableList<StudentModel>
    private lateinit var dbHelper: StudentDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        dbHelper = (activity as MainActivity).dbHelper
        students = mutableListOf()

        // Load dữ liệu từ database
        loadStudentsFromDatabase()

        studentAdapter = StudentAdapter(requireContext(), students)
        val listView = view.findViewById<ListView>(R.id.lv_fr)
        listView.adapter = studentAdapter

        // Đăng ký context menu cho ListView
        registerForContextMenu(listView)

        return view
    }

    // Tải dữ liệu từ database
    private fun loadStudentsFromDatabase() {
        students.clear()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "students",
            arrayOf("id", "name", "mssv"),
            null, null, null, null, null
        )
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(0)
                    val name = it.getString(1)
                    val mssv = it.getString(2)
                    val student = StudentModel(name, mssv) // Tạo đối tượng StudentModel
                    students.add(student) // Thêm vào danh sách
                } while (it.moveToNext())
            }
        }
        Log.v("TAG", "Loaded ${students.size} students from database")
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as? AdapterView.AdapterContextMenuInfo
        val position = info?.position ?: return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.context_edit -> {
                val student = students[position]
                val editForm = EditForm.newInstance(student.studentName, student.studentId, position)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fr_c, editForm, "TAG")
                    .addToBackStack("TAG")
                    .commit()
                true
            }

            R.id.context_remove -> {
                removeStudentFromDatabase(position)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    // Xóa sinh viên khỏi database và danh sách
    private fun removeStudentFromDatabase(position: Int) {
        val studentId = students[position].studentId
        dbHelper.removeStudent(studentId)
        students.removeAt(position)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Student removed", Toast.LENGTH_SHORT).show()
        Log.v("TAG", "Removed student with ID: $studentId")
    }

    fun editStudent(name: String, id: String, position: Int) {
        students[position].studentName = name
        students[position].studentId = id

        studentAdapter.notifyDataSetChanged()
        Log.v("TAG", "Edited student at position $position")
    }

    fun addStudent(name: String, id: String) {
        val student = StudentModel(name, id)
        dbHelper.addStudent(student)
        students.add(student)
        studentAdapter.notifyDataSetChanged()
        Log.v("TAG", "Added student: $name - $id")
    }
}