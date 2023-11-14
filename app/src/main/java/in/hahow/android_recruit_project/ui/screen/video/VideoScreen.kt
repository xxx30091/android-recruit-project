package `in`.hahow.android_recruit_project.ui.screen.video

import android.util.Log
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import `in`.hahow.android_recruit_project.ui.nav.SCREEN_PURCHASE
import `in`.hahow.android_recruit_project.ui.nav.SCREEN_VIDEO
import `in`.hahow.android_recruit_project.ui.screen.main.popUpToMain
import `in`.hahow.android_recruit_project.widget.navArgs

@Composable
fun VideoScreen(
    navController: NavHostController,
    viewModel: VideoViewModel = hiltViewModel()
) {
    val videoUrl = viewModel.videoUrl
    val context = LocalContext.current
    val player = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            // 抓播放錯誤
            addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)
                    Log.d("Arthur", "error = ${error.errorCode}")
                }
            })
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ExoVideo(exoPlayer = player, videoUrl = videoUrl)
        IconButton(
            onClick = {
                navController.popBackStack()
                player.release() // 要想一下怎樣更好的控制
            },
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .size(30.dp)
                .align(Alignment.TopStart),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
            )
        }
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ExoVideo(exoPlayer: Player, videoUrl: String) {
    val context = LocalContext.current

    AndroidView( // 要使用 Compose 尚未提供的 UI 元素
        factory = {
            PlayerView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                useController = true
                useArtwork = false
                player = exoPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    LaunchedEffect(key1 = true) {
        exoPlayer.setMediaItem(MediaItem.fromUri(videoUrl))
        exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ALL
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        Log.d("Arthur", videoUrl)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.video(navController: NavHostController,) {
    composable(
        route = "${SCREEN_VIDEO}/{videoUrl}",
        arguments = listOf(
            navArgument("videoUrl") {
                type = NavType.StringType
                defaultValue = ""
            }
        ),
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { it } }
    ) {
        VideoScreen(navController)
    }
}

fun NavHostController.navToVideo(videoUrl: String) {
    navigate(route = SCREEN_VIDEO.navArgs(videoUrl)) {
        launchSingleTop = true
    }
}