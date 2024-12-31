package vn.edu.hust.studentman

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val name: String,
    val mssv: String,
)
