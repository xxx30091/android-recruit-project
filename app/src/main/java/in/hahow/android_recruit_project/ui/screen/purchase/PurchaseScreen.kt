package `in`.hahow.android_recruit_project.ui.screen.purchase

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import `in`.hahow.android_recruit_project.MainActivity
import `in`.hahow.android_recruit_project.ui.nav.SCREEN_PURCHASE
import `in`.hahow.android_recruit_project.ui.screen.main.popUpToMain
import `in`.hahow.android_recruit_project.widget.PurchaseHelper

@Composable
fun PurchaseScreen(navController: NavHostController, purchaseHelper: PurchaseHelper) {

    val buyEnabled by purchaseHelper.buyEnabled.collectAsState(false)
    val consumeEnabled by purchaseHelper.consumeEnabled.collectAsState(false)
    val productName by purchaseHelper.productName.collectAsState("")
    val statusText by purchaseHelper.statusText.collectAsState("")

    Box(
        modifier = Modifier
            .background(Color.Yellow)
            .padding(20.dp)
            .fillMaxSize(),
    ) {
        IconButton(
            onClick = { navController.popUpToMain() },
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopStart),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = productName,
                modifier = Modifier
                    .padding(20.dp),
                fontSize = 30.sp,
            )

            Text(text = statusText)

            Row(modifier = Modifier.padding(20.dp)) {

                Button(
                    onClick = { purchaseHelper.makePurchase() },
                    modifier = Modifier.padding(20.dp),
                    enabled = buyEnabled
                ) {
                    Text(text = "Purchase")
                }

                Button(
                    onClick = { purchaseHelper.consumePurchase() },
                    modifier = Modifier.padding(20.dp),
                    enabled = consumeEnabled
                ) {
                    Text(text = "Consume")
                }
            }
        }


    }
}

@Preview
@Composable
fun PreviewPurchaseScreen() {
    PurchaseScreen(
        NavHostController(LocalContext.current),
        PurchaseHelper(MainActivity())
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.purchase(navController: NavHostController, purchaseHelper: PurchaseHelper) {
    composable(
        route = SCREEN_PURCHASE,
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { it } }
    ) {
        PurchaseScreen(navController, purchaseHelper)
    }
}

fun NavHostController.navToPurchase() {
    navigate(route = SCREEN_PURCHASE) {
        launchSingleTop = true
    }
}