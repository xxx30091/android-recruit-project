package `in`.hahow.android_recruit_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import dagger.hilt.android.AndroidEntryPoint
import `in`.hahow.android_recruit_project.ui.nav.SCREEN_MAIN
import `in`.hahow.android_recruit_project.ui.screen.main.main
import `in`.hahow.android_recruit_project.ui.screen.purchase.purchase
import `in`.hahow.android_recruit_project.widget.PurchaseHelper
import `in`.hahow.android_recruit_project.widget.rememberBottomSheetNavigator

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
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
                }
            }
        }
    }
}
