package `in`.hahow.android_recruit_project.ui.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope

@Composable
fun UiEventHandler(block: suspend CoroutineScope.(Context) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        block(context)
    }
}