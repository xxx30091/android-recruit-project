package `in`.hahow.android_recruit_project.ui.screen.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import `in`.hahow.android_recruit_project.data.model.CourseItem
import `in`.hahow.android_recruit_project.data.model.SuccessCriteria
import `in`.hahow.android_recruit_project.ui.theme.GrayNormalText
import `in`.hahow.android_recruit_project.ui.theme.GrayProgressBackground
import `in`.hahow.android_recruit_project.ui.theme.GrayRecyclerViewBackground
import `in`.hahow.android_recruit_project.ui.theme.GreenPublished
import `in`.hahow.android_recruit_project.ui.theme.OrangeIncubating
import `in`.hahow.android_recruit_project.utils.DateTimeUtils
import `in`.hahow.android_recruit_project.utils.NumberFormatUtils

// 單純為了用來看 preview
val testCourse = CourseItem(
    SuccessCriteria(numSoldTickets = 30),
    numSoldTickets = 0,
    status = "INCUBATING",
    proposalDueTime = "2023-11-01T16:00:00.000Z",
    coverImageUrl = "https://images.api.hahow.in/images/614eca15a39712000619b672",
    title = "學習 AI 一把抓：點亮人工智慧技能樹"
)

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val filter = remember { mutableStateOf("") }
//    val data = viewModel.courseList?.data


    val data = remember {
        mutableStateOf(
            if (filter.value.isEmpty()) { viewModel.courseList?.data?.toList() }
            else {
                viewModel.courseList?.data?.filter {
                    it.status == filter.toString()
                }
            }
        )
    }

    Log.i("Arthur", "filter: ${filter.value}")

//    val data = remember { mutableStateOf(viewModel.courseList?.data?.toList()) }
//    val data = remember {
//        mutableStateOf(
//            viewModel.courseList?.data?.filter {
//                it.status == filter.toString()
//            }
//        )
//    }

    Column() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(GreenPublished)
        ) {

        }
        LazyColumn(
            modifier = Modifier.background(GrayRecyclerViewBackground),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            data?.let { data ->
//                items(data) {
//                    MainCourseItem(data = it)
//                }
//            }

            data.value?.let { data ->
                items(data) {
                    MainCourseItem(data = it)
                }
            }

//            viewModel.courseList?.let {courseList ->
//                items(courseList.data) {
//                    MainCourseItem(data = it)
//                }
//            }
        }
    }
}

@Composable
fun MainCourseItem(data: CourseItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp)
            .background(Color.White)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp)),
        ) {
            AsyncImage(
                model = data.coverImageUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center),
                error = ColorPainter(Color.Gray),
                placeholder = ColorPainter(Color.Gray)
            )
            Text(
                text = when (data.status) {
                    "INCUBATING" -> "募資中"
                    "PUBLISHED" -> "已開課"
                    "SUCCESS" -> "募資成功"
                    else -> "募資中"
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(
                        color = if (data.status == "PUBLISHED") GreenPublished
                        else OrangeIncubating,
                        RoundedCornerShape(topStart = 6.dp, bottomEnd = 6.dp)
                    )
                    .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                fontSize = 9.sp
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.title,
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            // 因為覺得如果有要做其他頁面，這個可能有機會在其他地方重用，加上這個 function 已經有點長了，所以分開來寫
            CourseStatusBar(
                modifier = Modifier,
                status = data.status,
                target = data.successCriteria.numSoldTickets,
                current = data.numSoldTickets,
                proposalDueTime = data.proposalDueTime
            )
        }
    }
}

@Composable
fun CourseStatusBar(
    modifier: Modifier,
    status: String,
    target: Int,
    current: Int,
    proposalDueTime: String?
) {

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column()
        {
            Text(
                text = when (status) {
                    "INCUBATING" -> NumberFormatUtils.formatIncubatingTargetString(current, target)
                    "PUBLISHED" -> NumberFormatUtils.formatTargetPercentString(current, target)
                    "SUCCESS" -> NumberFormatUtils.formatTargetSuccessString(current, target)
                    else -> ""
                },
                color = GrayNormalText
            ,
                fontSize = 12.sp
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .width(51.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(5.dp)),
                progress = (current.toFloat() / target.toFloat()),
                backgroundColor = GrayProgressBackground,
                color = if (status == "INCUBATING" || status == "SUCCESS") OrangeIncubating
                else GreenPublished
            )
        }
        if (!proposalDueTime.isNullOrEmpty()
            && DateTimeUtils.daysBetweenDateAndToday(proposalDueTime) > 0
        ) {
            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(
                    imageVector = Icons.Default.Restore,
                    contentDescription = "",
                    modifier = Modifier.size(15.dp),
                    tint = GrayNormalText
                )
                Text(
                    text = "倒數" + DateTimeUtils.daysBetweenDateAndToday(proposalDueTime).toString()
                            + "天",
                    modifier = Modifier.padding(start = 3.dp),
                    fontSize = 12.sp,
                    color = GrayNormalText
                )
            }
        }
    }
}

@Composable
fun CourseFilter(
    onINCUBATINGClick: () -> Unit
) {
    Row(
        modifier = Modifier
    ) {

    }
}

@Preview
@Composable
fun PreviewMainCourseItem() {
    MainCourseItem(testCourse)
}

