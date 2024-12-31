package vn.edu.hust.studentman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels

class EditForm : Fragment() {
    private var studentName: String? = null
    private var studentId: String? = null
    private var position: Int = -1

    private val studentViewModel: StudentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            studentName = it.getString("student_name")
            studentId = it.getString("student_id")
            position = it.getInt("position", -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_form, container, false)
        val editName = view.findViewById<EditText>(R.id.ed_student_name)
        val editId = view.findViewById<EditText>(R.id.ed_student_id)
        val saveButton = view.findViewById<Button>(R.id.btn_c)

        // Hiển thị thông tin sinh viên cũ
        editName.setText(studentName)
        editId.setText(studentId)

        saveButton.setOnClickListener {
            val updatedName = editName.text.toString().trim()
            val updatedId = editId.text.toString().trim()

            if (updatedName.isEmpty() || updatedId.isEmpty()) {
                Toast.makeText(requireContext(), "Name and ID cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Cập nhật dữ liệu thông qua ViewModel
                studentViewModel.editStudent(updatedName, updatedId, studentId!!)
                if (activity is EditListener) {
                    (activity as EditListener).edit(updatedName, updatedId, position)
                }
                // Quay lại danh sách sinh viên
                parentFragmentManager.popBackStack()
            }
        }
        return view
    }

    companion object {
        fun newInstance(name: String, id: String, position: Int) = EditForm().apply {
            arguments = Bundle().apply {
                putString("student_name", name)
                putString("student_id", id)
                putInt("position", position)
            }
        }
    }
}
