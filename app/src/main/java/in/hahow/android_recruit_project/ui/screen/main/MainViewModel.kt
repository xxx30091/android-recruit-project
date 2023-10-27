package `in`.hahow.android_recruit_project.ui.screen.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hahow.android_recruit_project.ThisApp
import `in`.hahow.android_recruit_project.data.loader.CourseRepository
import `in`.hahow.android_recruit_project.data.model.CourseData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var courseList by mutableStateOf<CourseData?>(null)

    init {
        getCourseList()
    }

    private fun getCourseList() {
        viewModelScope.launch {

            val result = ThisApp.instance.courseRepository.loadAllCourses()

            courseList = result

            Log.i("Arthur", "MainViewModel.getCourseList: $courseList")

        }
    }

}