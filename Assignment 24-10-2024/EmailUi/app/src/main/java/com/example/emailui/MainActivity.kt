package com.example.emailui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emailAdapter: EmailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Dữ liệu mẫu
        val emailList = listOf(
            Email("alice@example.com", "Meeting Reminder", "Don't forget our meeting at 3 PM."),
            Email("bob@example.com", "Project Update", "The project is on track for completion."),
            Email("charliae@example.com", "Newsletter", "Check out our latest updates."),
            Email("charlibe@example.com", "Newsletter", "Check out our latest updates."),
            Email("charliec@example.com", "Newsletter", "Check out our latest updates."),
            Email("charlied@example.com", "Newsletter", "Check out our latest updates."),
            Email("charliee@example.com", "Newsletter", "Check out our latest updates."),
            Email("charlieg@example.com", "Newsletter", "Check out our latest updates."),
            Email("charliey@example.com", "Newsletter", "Check out our latest updates."),
            Email("charlieyuki@example.com", "Newsletter", "Check out our latest updates."),
            // Thêm nhiều email hơn ở đây
        )

        emailAdapter = EmailAdapter(emailList)
        recyclerView.adapter = emailAdapter
    }
}
