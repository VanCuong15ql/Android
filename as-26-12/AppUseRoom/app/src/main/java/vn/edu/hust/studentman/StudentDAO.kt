package vn.edu.hust.studentman


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDAO{
    @Query("SELECT * FROM students")
    suspend fun getAll(): List<Student>

    @Insert
    suspend fun insert(student: Student)

    //delete by mssv
    @Query("DELETE FROM students WHERE mssv = :mssv")
    suspend fun deleteByMssv(mssv: String):Int;

    //update by mssv
    @Query("UPDATE students SET name = :name, mssv=:mssv WHERE mssv = :oldmssv")
    suspend fun updateByMssv(name: String, mssv: String, oldmssv: String):Int;
}
