package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditForm.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditForm : Fragment()  {
    // TODO: Rename and change types of parameters
    private var studentName: String? = null
    private var studentId: String? = null
    private var position: Int = -1

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
        // Inflate the layout for this fragment
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
                // Trả dữ liệu đã chỉnh sửa về ListFragment

                if (activity is EditListener)
                    (activity as EditListener).edit(updatedName, updatedId, position)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fr_c, ListFragment(), "TAG")
                    .addToBackStack("TAG")
                    .commit()
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