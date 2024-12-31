package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class ast : Fragment() {
    private val studentViewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ast, container, false)

        val etStudentName = view.findViewById<EditText>(R.id.et_student_name)
        val etStudentId = view.findViewById<EditText>(R.id.et_student_id)
        val btnSave = view.findViewById<Button>(R.id.btn_save)

        // Clear fields on initialization
        etStudentName.setText("")
        etStudentId.setText("")

        btnSave.setOnClickListener {
            val name = etStudentName.text.toString().trim()
            val studentId = etStudentId.text.toString().trim()

            if (name.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Thêm sinh viên vào ViewModel
                studentViewModel.addStudent(name, studentId)

                Toast.makeText(requireContext(), "Student added successfully", Toast.LENGTH_SHORT).show()

                // Quay lại danh sách sinh viên
                parentFragmentManager.popBackStack()
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = ast()
    }
}
