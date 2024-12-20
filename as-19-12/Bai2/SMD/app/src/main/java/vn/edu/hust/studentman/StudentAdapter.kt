package vn.edu.hust.studentman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private val context: Context, private val students: List<StudentModel>) : BaseAdapter() {

  // Tạo ViewHolder để tối ưu hóa việc tái sử dụng view
  private class ViewHolder(view: View) {
    val studentName: TextView = view.findViewById(R.id.text_student_name)
    val studentId: TextView = view.findViewById(R.id.text_student_id)
  }

  override fun getCount(): Int = students.size

  override fun getItem(position: Int): Any = students[position]

  override fun getItemId(position: Int): Long = position.toLong()

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view: View
    val viewHolder: ViewHolder

    if (convertView == null) {
      // Inflate the item layout and create a new ViewHolder
      view = LayoutInflater.from(context).inflate(R.layout.layout_student_item, parent, false)
      viewHolder = ViewHolder(view)
      view.tag = viewHolder // Lưu ViewHolder vào view
    } else {
      // Nếu đã có convertView, lấy ViewHolder từ view đã có
      view = convertView
      viewHolder = view.tag as ViewHolder
    }

    val student = students[position]
    viewHolder.studentName.text = student.studentName
    viewHolder.studentId.text = student.studentId

    // Thiết lập long click listener để hiển thị ContextMenu
    view.setOnLongClickListener {
      // Hiển thị ContextMenu cho mục được nhấn giữ
      it.showContextMenu()
      true // Trả về true để chỉ rằng sự kiện đã được xử lý
    }

    return view
  }
}