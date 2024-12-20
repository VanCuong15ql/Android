package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class DirectoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FileAdapter
    fun getFilesAndFolders(path: String): List<File> {
        val directory = File(path)
        return directory.listFiles()?.toList() ?: emptyList()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)

        val path = intent.getStringExtra("path") ?: return
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val files = getFilesAndFolders(path)
        val namepath = findViewById<TextView>(R.id.namepath)
        namepath.text = files[0].parent
        adapter = FileAdapter(files) { file ->
            if (file.isDirectory) {
                // Mở nội dung thư mục
                val intent = Intent(this, DirectoryActivity::class.java)
                intent.putExtra("path", file.absolutePath)
                startActivity(intent)
            } else if (file.isFile && file.extension == "txt") {
                // Mở nội dung file .txt
                val intent = Intent(this, FileViewerActivity::class.java)
                intent.putExtra("filePath", file.absolutePath)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Không thể mở file này", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter
    }
}
