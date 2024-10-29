package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var isEditingEdit1 = false
    private var isEditingEdit2 = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val itemCurrency1: TextView = findViewById(R.id.textView)
        val itemCurrency2: TextView = findViewById(R.id.textView2)
        val selectCurrency: Spinner = findViewById(R.id.selectCurrency)
        val selectCurrency2: Spinner = findViewById(R.id.selectCurrency2)
        val edit1: EditText = findViewById(R.id.editTextText)
        val edit2: EditText = findViewById(R.id.editTextText2)
        edit1.setText("10000.00")
        edit2.setText("10000")

        val changeArray = arrayOf(
            doubleArrayOf(1.0, 0.00003939, 0.0002804),
            doubleArrayOf(25385.0, 1.0, 944.9),
            doubleArrayOf(3.566411, 0.1405, 1.0)
        )
        var type1: Int = 0
        var type2: Int = 0

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.currency,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectCurrency.adapter = adapter
        selectCurrency2.adapter = adapter

        selectCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                type1 = when (selectedItem) {
                    "VietNam-dong" -> {
                        itemCurrency1.text = "d"
                        0
                    }
                    "USA-dola" -> {
                        itemCurrency1.text = "$"
                        1
                    }
                    "China-NNT" -> {
                        itemCurrency1.text = "NDT"
                        2
                    }
                    else -> 0
                }
                if (!isEditingEdit2) {
                    if (!edit1.text.isNullOrEmpty()) {
                        isEditingEdit1 = true
                        try {
                            val number = edit1.text.toString().toDouble()
                            val result = number * changeArray[type1][type2]
                            edit2.setText(String.format(Locale.US,"%.2f", result))
                        } catch (e: NumberFormatException) {
                            edit2.setText("error")
                        } finally {
                        }
                        isEditingEdit1 = false
                    } else {
                        isEditingEdit1 = true
                        try {
                            edit2.setText("")
                        } catch (e: NumberFormatException) {
                            edit2.setText("error")
                        } finally {

                        }
                        isEditingEdit1 = false
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        selectCurrency2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                type2 = when (selectedItem) {
                    "VietNam-dong" -> {
                        itemCurrency2.text = "d"
                        0
                    }
                    "USA-dola" -> {
                        itemCurrency2.text = "$"
                        1
                    }
                    "China-NNT" -> {
                        itemCurrency2.text = "NDT"
                        2
                    }
                    else -> 0
                }
                if (!isEditingEdit1) {
                    isEditingEdit2 = true
                    if (!edit2.text.isNullOrEmpty()) {
                        try {
                            val number = edit2.text.toString().toDouble()
                            val result = number * changeArray[type2][type1]

                            edit1.setText(String.format(Locale.US,"%.2f", result))

                        } catch (e: NumberFormatException) {
                            edit1.setText("error1")
                        } finally {


                        }

                    } else {

                        try {
                            edit1.setText("")
                        } catch (e: NumberFormatException) {
                            edit1.setText("error2")
                        } finally {


                        }

                    }
                    isEditingEdit2 = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        edit1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isEditingEdit2) {
                    isEditingEdit1 = true
                    if (!s.isNullOrEmpty()) {

                        try {

                            val number = s.toString().toDouble()
                            val result = number * changeArray[type1][type2]
                            edit2.setText(String.format(Locale.US,"%.2f", result))

                        } catch (e: NumberFormatException) {
                            edit2.setText("error")
                        } finally {


                        }

                    } else {

                        try {
                            edit2.setText("")
                        } catch (e: NumberFormatException) {
                            edit2.setText("error")
                        } finally {

                        }

                    }
                    isEditingEdit1 = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        edit2.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isEditingEdit1) {
                    isEditingEdit2 = true
                    if (!s.isNullOrEmpty()) {
                        try {

                            val number = s.toString().toDouble()
                            val result = number * changeArray[type2][type1]

                            edit1.setText(String.format(Locale.US,"%.2f", result))
                            isEditingEdit2 = false
                        } catch (e: NumberFormatException) {
                            edit1.setText("error")
                        } finally {


                        }
                        isEditingEdit2 = false
                    } else {
                        isEditingEdit2 = false
                        try {


                            edit1.setText("")
                        } catch (e: NumberFormatException) {
                            edit1.setText("error")
                        } finally {


                        }
                        isEditingEdit2 = false
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
