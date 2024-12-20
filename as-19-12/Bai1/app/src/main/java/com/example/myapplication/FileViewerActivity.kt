package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class FileViewerActivity : AppCompatActivity() {
    fun readFileContent(file: File): String {
        return file.readText()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_viewer)

        val filePath = intent.getStringExtra("filePath") ?: return
        val file = File(filePath)

        val fileNameTextView = findViewById<TextView>(R.id.fileNameTextView)
        fileNameTextView.text = file.name

        val inputStream = file.inputStream()
        val reader = inputStream.reader()
        val content = reader.readText()

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = content
    }
}
