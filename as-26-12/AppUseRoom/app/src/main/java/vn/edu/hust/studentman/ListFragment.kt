package vn.edu.hust.studentman

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private val studentViewModel: StudentViewModel by activityViewModels()
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var students: MutableList<StudentModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        students = mutableListOf()
        studentAdapter = StudentAdapter(requireContext(), students)
        val listView = view.findViewById<ListView>(R.id.lv_fr)
        listView.adapter = studentAdapter

        // Đăng ký context menu cho ListView
        registerForContextMenu(listView)

        // Quan sát danh sách sinh viên từ ViewModel
        observeStudents()

        return view
    }

    private fun observeStudents() {
        lifecycleScope.launch {
            studentViewModel.students.collect { updatedStudents ->
                students.clear()
                students.addAll(updatedStudents)
                studentAdapter.notifyDataSetChanged()
                Log.d("TAG", "Updated student list: ${updatedStudents.size}")
            }
        }
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
                val student = students[position]
                studentViewModel.removeStudent(student.studentId)
                Toast.makeText(requireContext(), "Student removed", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun editStudent(name: String, id: String, position: Int) {
        val oldMssv = students[position].studentId
        studentViewModel.editStudent(name, id, oldMssv)
        Log.v("TAG", "Edited student at position $position")
    }

    fun addStudent(name: String, id: String) {
        studentViewModel.addStudent(name, id)
        Log.v("TAG", "Added student: $name - $id")
    }
}
