package `in`.hahow.android_recruit_project.widget

import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun rememberBottomSheetNavigator(skipHalfExpanded: Boolean): BottomSheetNavigator {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = SwipeableDefaults.AnimationSpec,
        skipHalfExpanded = skipHalfExpanded,
        confirmStateChange = { true }
    )
    return remember(sheetState) {
        BottomSheetNavigator(sheetState = sheetState)
    }
}