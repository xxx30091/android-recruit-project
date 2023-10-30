package `in`.hahow.android_recruit_project.ui.screen.main

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hahow.android_recruit_project.ThisApp
import `in`.hahow.android_recruit_project.data.loader.CourseRepository
import `in`.hahow.android_recruit_project.data.model.CourseData
import `in`.hahow.android_recruit_project.data.model.CourseItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    val isInit = mutableStateOf(true)

    init {
        getCourseList()
    }

    private fun getCourseList() {

        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true)
            val result = ThisApp.instance.courseRepository.loadAllCourses()

            _state.value = _state.value.copy(
                isLoading = false,
                data = result.data.toList(),
            )
            Log.i("Arthur", "MainViewModel.getCourseList: ${_state.value.data}")

        }
    }

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.SetFilter -> {
                _state.value = _state.value.copy(filter = event.filter)
                Log.i("Arthur", "SetFilter: filter=${_state.value.filter}, event.filter=${event.filter}")
            }
            UiEvent.ReloadData -> {
                isInit.value = false
                getCourseList()
            }
            else -> {}
        }
    }

    sealed class UiEvent {
        class SetFilter(val filter: String) : UiEvent()
        object ReloadData :UiEvent()
    }
}

data class MainScreenState(
    val isLoading: Boolean = false,
    val data: List<CourseItem> = listOf(),
    val filter: String = ""
)