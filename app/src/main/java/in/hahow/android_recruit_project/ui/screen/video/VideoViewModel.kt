package `in`.hahow.android_recruit_project.ui.screen.video

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// video is from https://gist.github.com/deepakpk009/99fd994da714996b296f11c3c371d5ee
@HiltViewModel
class VideoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {
    val videoUrl = checkNotNull(savedStateHandle.get<String>("videoUrl"))

    init {
        Log.i("Arthur", videoUrl)
    }


}