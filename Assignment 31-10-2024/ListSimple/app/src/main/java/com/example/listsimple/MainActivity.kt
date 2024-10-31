package com.example.listsimple

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các thành phần trong giao diện
        val editTextNumber = findViewById<EditText>(R.id.editTextNumber)
        val radioEven = findViewById<RadioButton>(R.id.radioEven)
        val radioOdd = findViewById<RadioButton>(R.id.radioOdd)
        val radioSquare = findViewById<RadioButton>(R.id.radioSquare)
        val buttonShow = findViewById<Button>(R.id.buttonShow)
        val listViewResult = findViewById<ListView>(R.id.listViewResult)
        val textViewError = findViewById<TextView>(R.id.textViewError)

        // Lắng nghe sự kiện nhấn nút Show
        buttonShow.setOnClickListener {
            // Lấy số nguyên dương n từ EditText
            val inputText = editTextNumber.text.toString()
            if (inputText.isEmpty()) {
                textViewError.text = "Vui lòng nhập số nguyên dương"
                return@setOnClickListener
            }

            val n = inputText.toIntOrNull()
            if (n == null || n <= 0) {
                textViewError.text = "Dữ liệu không hợp lệ. Nhập số nguyên dương."
                return@setOnClickListener
            }

            val resultList = mutableListOf<Int>()

            when {
                radioEven.isChecked -> {
                    // Số chẵn từ 0 đến n
                    for (i in 0..n step 2) resultList.add(i)
                }
                radioOdd.isChecked -> {
                    // Số lẻ từ 1 đến n
                    for (i in 1..n step 2) resultList.add(i)
                }
                radioSquare.isChecked -> {
                    // Số chính phương từ 0 đến n
                    var i = 0
                    while (i * i <= n) {
                        resultList.add(i * i)
                        i++
                    }
                }
                else -> {
                    // Nếu không chọn loại số nào
                    textViewError.text = "Vui lòng chọn loại số"
                    return@setOnClickListener
                }
            }

            // Kiểm tra nếu có kết quả để hiển thị
            if (resultList.isNotEmpty()) {
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList)
                listViewResult.adapter = adapter
                textViewError.text = ""  // Xóa thông báo lỗi
            } else {
                textViewError.text = "Không có kết quả thỏa mãn"
            }
        }
    }
}
