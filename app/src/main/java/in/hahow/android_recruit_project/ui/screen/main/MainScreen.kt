package `in`.hahow.android_recruit_project.ui.screen.main

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Restore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
import `in`.hahow.android_recruit_project.ui.theme.GraySoft
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
            onItemSelected = { if (filter.value == it) filter.value = "" else filter.value = it }
        )

        if (viewModel.loadingState) {
            // 讓使用者在 loading 時不會因為一片空白而不知道是不是已經 load 完
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayRecyclerViewBackground),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(8) {
                    MainCoursePlaceHolder()
                }
            }
        } else {
            if (data.isNullOrEmpty()) {
                // 讓使用者在選擇的類別沒資料，時不會因為一片空白而不知道是不是已經 load 完
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.EventNote,
                        contentDescription = "",
                        modifier = Modifier.size(72.dp),
                        tint = GreenPublished
                    )
                    Text(
                        text = "目前沒有任何課程",
                        modifier = Modifier.padding(4.dp),
                        color = GreenPublished,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "您選的類別目前沒有任何課程",
                        modifier = Modifier.padding(4.dp),
                        color = GrayNormalText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(GrayRecyclerViewBackground),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(data) {
                        MainCourseItem(data = it)
                    }
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
                modifier = Modifier.align(Alignment.Center),
                error = ColorPainter(Color.Gray),
                placeholder = ColorPainter(Color.Gray)
            )
            Text(
                text = when (data.status) {
                    CourseStatus.INCUBATING.value -> "募資中"
                    CourseStatus.PUBLISHED.value -> "已開課"
                    CourseStatus.SUCCESS.value -> "募資成功"
                    else -> "募資中"
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(
                        color = if (data.status == CourseStatus.SUCCESS.value) GreenPublished
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

@Preview
@Composable
fun MainCoursePlaceHolder() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp)
            .background(Color.White)
            .clickable { }
            .padding(12.dp)
    ) {
        Spacer(
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
                .background(rememberShimmerEffect(), RoundedCornerShape(6.dp)),
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .fillMaxHeight(),
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(rememberShimmerEffect(), RoundedCornerShape(8.dp))
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .padding(top = 4.dp)
                    .height(20.dp)
                    .background(rememberShimmerEffect(), RoundedCornerShape(8.dp))
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column() {
                    Spacer(
                        modifier = Modifier
                            .size(width = 54.dp, height = 12.dp)
                            .background(rememberShimmerEffect(), RoundedCornerShape(8.dp))
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(width = 72.dp, height = 6.dp)
                            .background(rememberShimmerEffect(), RoundedCornerShape(8.dp))
                    )
                }
                Spacer(
                    modifier = Modifier
                        .size(width = 54.dp, height = 12.dp)
                        .background(rememberShimmerEffect(), RoundedCornerShape(8.dp))
                )
            }
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
                    CourseStatus.INCUBATING.value -> NumberFormatUtils.formatIncubatingTargetString(
                        current, target
                    )
                    CourseStatus.PUBLISHED.value -> NumberFormatUtils.formatTargetPercentString(
                        current, target
                    )
                    CourseStatus.SUCCESS.value -> NumberFormatUtils.formatTargetSuccessString(
                        current, target
                    )
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
                color = if (status == CourseStatus.INCUBATING.value || status == CourseStatus.SUCCESS.value) OrangeIncubating
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
                    text = "倒數${DateTimeUtils.daysBetweenDateAndToday(proposalDueTime)}天",
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
    onItemSelected: (String) -> Unit
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
            title = "已開課", isSelected = currentValue == CourseStatus.PUBLISHED.value,
            onClick = { onItemSelected(CourseStatus.PUBLISHED.value) }
        )
        FilterItem(
            title = "募資中", isSelected = currentValue == CourseStatus.INCUBATING.value,
            onClick = { onItemSelected(CourseStatus.INCUBATING.value) }
        )
        FilterItem(
            title = "募資成功", isSelected = currentValue == CourseStatus.SUCCESS.value,
            onClick = { onItemSelected(CourseStatus.SUCCESS.value) }
        )
        FilterItem(
            title = "沒東東", isSelected = currentValue == CourseStatus.NOTHING.value,
            onClick = { onItemSelected(CourseStatus.NOTHING.value) }
        )
    }
}

@Composable
fun FilterItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
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

@Composable
fun rememberShimmerEffect(): Brush {
    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateColor(
        initialValue = GraySoft,
        targetValue = Color.Gray,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    return SolidColor(translateAnimation.value)
}