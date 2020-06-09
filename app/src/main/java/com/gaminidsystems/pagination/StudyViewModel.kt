package com.gaminidsystems.pagination

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class StudyViewModel(application: Application): AndroidViewModel(application) {

    private val studyRepository: StudyRepository
    val studies: LiveData<List<Study>>
    private var job = Job()
    private val coroutine: CoroutineContext get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutine)

    init {
        val studyDao = StudyRoomDatabase.getDatabase(application).studyDao()
        studyRepository = StudyRepository(studyDao)
        studies = studyRepository.studies
    }

    fun insert(study: Study) = scope.launch(Dispatchers.IO){
        studyRepository.insert(study)
    }
    fun delete(study: Study) = scope.launch(Dispatchers.IO){
        studyRepository.delete(study)
    }
    fun getStudyById(id: String) : LiveData<List<Study>>{
        return studyRepository.getStudyById(id)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}