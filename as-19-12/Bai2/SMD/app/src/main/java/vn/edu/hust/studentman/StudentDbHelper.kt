package vn.edu.hust.studentman

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.util.Log

class StudentDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "mydb.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.v("TAG", "Creating table students")
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS students (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                mssv TEXT NOT NULL
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.v("TAG", "Upgrading database from version $oldVersion to $newVersion")
        db.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }

    // Thêm một sinh viên vào bảng "students"
    fun addStudent(student: StudentModel) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.studentName)
            put("mssv", student.studentId)
        }
        db.insert("students", null, values) // Thêm dữ liệu
        Log.v("TAG", "Student added: ${student.studentName} - ${student.studentId}")
    }

    // Cập nhật thông tin sinh viên
    fun updateStudent(student: StudentModel, oldId: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.studentName)
            put("mssv", student.studentId)
        }
        val whereClause = "mssv = ?"
        val whereArgs = arrayOf(oldId)
        db.update("students", values, whereClause, whereArgs)
        Log.v("TAG", "Student updated: ${student.studentName} - ${student.studentId}")
    }

    // Xóa sinh viên khỏi bảng "students"
    fun removeStudent(id: String) {
        val db = writableDatabase
        val whereClause = "mssv = ?"
        val whereArgs = arrayOf(id)
        db.delete("students", whereClause, whereArgs)
        Log.v("TAG", "Student removed with ID: $id")
    }
}