package com.example.myapplication

// MainActivity.kt


import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextNumber: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var buttonShow: Button
    private lateinit var listViewNumbers: ListView
    private lateinit var textViewError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNumber = findViewById(R.id.editTextNumber)
        radioGroup = findViewById(R.id.radioGroup)
        buttonShow = findViewById(R.id.buttonShow)
        listViewNumbers = findViewById(R.id.listViewNumbers)
        textViewError = findViewById(R.id.textViewError)

        buttonShow.setOnClickListener {
            showNumbers()
        }
    }

    private fun showNumbers() {
        val nStr = editTextNumber.text.toString()
        val n: Int

        if (nStr.isEmpty() || nStr.toInt() < 0) {
            textViewError.text = "Vui lòng nhập số nguyên dương n."
            return
        }

        n = nStr.toInt()
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        val numbersList = mutableListOf<String>()

        when (selectedRadioButtonId) {
            R.id.radioEven -> {
                for (i in 0..n step 2) {
                    numbersList.add(i.toString())
                }
            }
            R.id.radioOdd -> {
                for (i in 1..n step 2) {
                    numbersList.add(i.toString())
                }
            }
            R.id.radioSquare -> {
                for (i in 0..n) {
                    val square = i * i
                    if (square <= n) {
                        numbersList.add(square.toString())
                    }
                }
            }
            else -> {
                textViewError.text = "Vui lòng chọn một loại số."
                return
            }
        }

        if (numbersList.isEmpty()) {
            textViewError.text = "Không có số nào thỏa mãn."
        } else {
            textViewError.text = ""
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbersList)
        listViewNumbers.adapter = adapter
    }
}
