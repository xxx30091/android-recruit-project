package `in`.hahow.android_recruit_project.ui.screen.main

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hahow.android_recruit_project.ThisApp
import `in`.hahow.android_recruit_project.data.model.CourseItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()
//    private val _state = mutableStateOf(MainScreenState())
//    val state: State<MainScreenState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val isInit = mutableStateOf(true)

    init {
        getCourseList()
    }

    private fun getCourseList(filter: String = "") {

        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true)
            val result = ThisApp.instance.courseRepository.loadAllCourses()

            _state.value = _state.value.copy(
                isLoading = false,
                data = if (filter.isEmpty()) result.data.toList()
                else result.data.filter { it.status == filter },
                filter = filter
            )
            Log.i("Arthur", "MainViewModel.getCourseList: ${_state.value.data}")
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SetFilter -> {
                getCourseList(event.filter)
                Log.i("Arthur", "SetFilter: filter=${_state.value.filter}, event.filter=${event.filter}")
            }
            MainEvent.ReloadData -> {
                isInit.value = false
                getCourseList(_state.value.filter)
            }
        }
    }

    fun showCourseTitle(title: String) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.OnCourseClick(title))
        }
    }

    fun goToVideo(videoUrl: String) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.OnCourseImageClick(videoUrl))
        }
    }

    // 可以用於設定畫面跳轉或 UI 顯示改變的一些邏輯
    sealed class UiEvent {
        class OnCourseClick(val title: String) : UiEvent()
        class OnCourseImageClick(val videoUrl: String) : UiEvent()
    }
}

sealed class MainEvent {
    class SetFilter(val filter: String) : MainEvent()
    object ReloadData : MainEvent()
}

data class MainScreenState(
    val isLoading: Boolean = false,
    val data: List<CourseItem> = listOf(),
    val filter: String = ""
)