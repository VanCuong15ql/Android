package com.example.myapplication

import android.content.Intent
import android.media.audiofx.EnvironmentalReverb
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FileAdapter

    fun getFilesAndFolders(path: String): List<File> {
        val directory = File(path)
        return directory.listFiles()?.toList() ?: emptyList()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        }
        val path = Environment.getExternalStorageDirectory().absolutePath
        val files = getFilesAndFolders(path)
        adapter = FileAdapter(files) { file ->
            if (file.isDirectory) {
                val intent = Intent(this, DirectoryActivity::class.java)
                intent.putExtra("path", file.absolutePath)
                startActivity(intent)
            } else {
                val intent = Intent(this, FileViewerActivity::class.java)
                intent.putExtra("filePath", file.absolutePath)
                startActivity(intent)
            }
        }
        recyclerView.adapter = adapter
    }
}
