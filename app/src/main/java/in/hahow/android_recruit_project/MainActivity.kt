package `in`.hahow.android_recruit_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import `in`.hahow.android_recruit_project.ui.nav.SCREEN_MAIN
import `in`.hahow.android_recruit_project.ui.screen.main.main
import `in`.hahow.android_recruit_project.ui.screen.purchase.purchase
import `in`.hahow.android_recruit_project.ui.screen.video.video
import `in`.hahow.android_recruit_project.widget.PurchaseHelper
import `in`.hahow.android_recruit_project.widget.rememberBottomSheetNavigator

@OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalAnimationApi::class
)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navigator = rememberBottomSheetNavigator(true)
                val navController = rememberAnimatedNavController(navigator)
                val purchaseHelper = PurchaseHelper(this)
                purchaseHelper.billingSetup()

                // 用來控制畫面跳轉動畫
                AnimatedNavHost(
                    navController = navController,
                    startDestination = SCREEN_MAIN
                ) {
                    main(navController)
                    purchase(navController, purchaseHelper)
                    video(navController)
                }
            }
        }
    }
}
