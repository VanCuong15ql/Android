package vn.edu.hust.studentman

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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        students = studentViewModel.students


        studentAdapter = StudentAdapter(requireContext(), students)

        val listview = view.findViewById<ListView>(R.id.lv_fr)
        listview.adapter = studentAdapter

        // Đăng ký context menu cho RecyclerView
        registerForContextMenu(listview)

        return view
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    //
    // Xử lý chọn mục trong ContextMenu
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
                students.removeAt(position)
                studentAdapter.notifyDataSetChanged() // Cập nhật lại ListView
                Toast.makeText(requireContext(), "Student removed", Toast.LENGTH_SHORT).show()

                true
            }

            else -> super.onContextItemSelected(item)
        }
    }
    fun editStudent(name: String, id: String, position: Int) {
        students[position].studentName = name
        students[position].studentId = id
        studentAdapter.notifyDataSetChanged()
    }
    fun addStudent(name: String, id: String) {
        val student = StudentModel(name, id)
        students.add(student)  // Thêm sinh viên vào danh sách
        studentAdapter.notifyDataSetChanged()
        Log.v("TAG", "add begin in ListFragment")
    }
}