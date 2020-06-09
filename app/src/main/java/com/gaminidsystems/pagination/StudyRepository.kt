package com.gaminidsystems.pagination

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class StudyRepository(private val studyDao: StudyDao) {

    val studies: LiveData<List<Study>> = studyDao.getAllStudies()

    @WorkerThread
    fun getStudyById(id: String) : LiveData<List<Study>>{
       return studyDao.getStudyById(id)
    }

    @WorkerThread
    suspend fun insert(study: Study){
        studyDao.insertStudy(study)
    }
    @WorkerThread
    suspend fun delete(study: Study){
        studyDao.deleteStudy(study)
    }
}