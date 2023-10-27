package `in`.hahow.android_recruit_project.ui.screen.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import `in`.hahow.android_recruit_project.ui.theme.GreenFilterItemBackground
import `in`.hahow.android_recruit_project.ui.theme.GreenPublished
import `in`.hahow.android_recruit_project.ui.theme.OrangeIncubating
import `in`.hahow.android_recruit_project.utils.DateTimeUtils
import `in`.hahow.android_recruit_project.utils.NumberFormatUtils

// 單純為了用來看 preview 所做的 mock data
val testCourse = CourseItem(
    SuccessCriteria(numSoldTickets = 30),
    numSoldTickets = 0,
    status = "INCUBATING",
    proposalDueTime = "2023-11-01T16:00:00.000Z",
    coverImageUrl = "https://images.api.hahow.in/images/614eca15a39712000619b672",
    title = "學習 AI 一把抓：點亮人工智慧技能樹"
)

//@Preview
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val filter = remember { mutableStateOf("") }
    val data = if (filter.value.isEmpty()) {
        viewModel.courseList?.data?.toList()
    } else {
        viewModel.courseList?.data?.filter {
            it.status == filter.value
        }
    }
    
    Log.i("Arthur", "filter:${filter.value}")
    Log.i("Arthur", "main screen data:${data}")

    Column() {
        CourseStatusFilter(
            currentValue = filter.value,
            onIncubatingSelected = {
                if (filter.value == "INCUBATING") filter.value = "" else filter.value = "INCUBATING"
            },
            onPublishedSelected = {
                if (filter.value == "PUBLISHED") filter.value = "" else filter.value = "PUBLISHED"
            },
            onSuccessSelected = {
                if (filter.value == "SUCCESS") filter.value = "" else filter.value = "SUCCESS"
            }
        )
        LazyColumn(
            modifier = Modifier.background(GrayRecyclerViewBackground),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            data?.let { data ->
                items(data) {
                    MainCourseItem(data = it)
                }
            }
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
            .clickable { }
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
                color = GrayNormalText,
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
                    text = "倒數" + DateTimeUtils.daysBetweenDateAndToday(proposalDueTime)
                        .toString()
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
fun CourseStatusFilter(
    currentValue: String,
    onIncubatingSelected: () -> Unit = {},
    onSuccessSelected: () -> Unit = {},
    onPublishedSelected: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(GrayRecyclerViewBackground)
            .padding(start = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterItem(
            title = "已開課", isSelected = currentValue == "PUBLISHED",
            onClick = { onPublishedSelected() }
        )
        FilterItem(
            title = "募資中", isSelected = currentValue == "INCUBATING",
            onClick = { onIncubatingSelected() }
        )
        FilterItem(
            title = "募資成功", isSelected = currentValue == "SUCCESS",
            onClick = { onSuccessSelected() }
        )
    }
}

@Composable
fun FilterItem(
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val corner = 15.dp
    Text(
        text = title,
        modifier = Modifier
            .padding(start = 8.dp)
            .clip(RoundedCornerShape(corner))
            .background(
                color = if (isSelected) GreenFilterItemBackground else Color.White
            )
            .border(
                width = 1.dp,
                color = if (isSelected) GreenPublished else Color.White,
                shape = RoundedCornerShape(corner)
            )
            .clickable { onClick() }
            .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
        color = GreenPublished,
        fontSize = 15.sp,
        textAlign = TextAlign.Center
    )
}

//@Preview
@Composable
fun PreviewMainCourseItem() {
    MainCourseItem(testCourse)
}

