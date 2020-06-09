package com.gaminidsystems.pagination

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudyDao {

    @Insert
    suspend fun insertStudy(study: Study)

    @Delete
    suspend fun deleteStudy(study: Study)

    @Query("SELECT * from study")
    fun getAllStudies() : LiveData<List<Study>>

    @Query("SELECT * FROM study WHERE brief_title LIKE '%' || :text  || '%' OR nct_id LIKE '%' || :text  || '%'")
    fun getStudyById(text: String) : LiveData<List<Study>>
}