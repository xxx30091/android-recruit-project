package `in`.hahow.android_recruit_project.ui.screen.main

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import `in`.hahow.android_recruit_project.model.CourseItem
import `in`.hahow.android_recruit_project.model.SuccessCriteria
import `in`.hahow.android_recruit_project.ui.theme.GrayNormalText
import `in`.hahow.android_recruit_project.ui.theme.GreenPublished
import `in`.hahow.android_recruit_project.ui.theme.OrangeIncubating

val testCourse = CourseItem(
    SuccessCriteria(numSoldTickets = 30),
    numSoldTickets = 0,
    status = "INCUBATING",
    proposalDueTime = "2022-01-06T16:00:00.000Z",
    coverImageUrl = "https://images.api.hahow.in/images/614eca15a39712000619b672",
    title = "學習 AI 一把抓：點亮人工智慧技能樹"
)

@Composable
fun MainCourseItem(data: CourseItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.White)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .width(96.dp)
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

        Box(
            modifier = Modifier
                .padding(start = 12.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = data.title + "測試溢位/測試溢位/測試溢位/測試溢位/測試溢位/測試溢位/測試溢位/測試溢位/測試溢位/測試溢位/測試溢位/測試溢位/測試溢位/測試溢位/",
                modifier = Modifier.align(Alignment.TopStart),
                color = Color.Black,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            // 因為覺得這個可能有機會在其他地方重用，加上這個 function 已經有點長了，所以分開來寫
            CourseStatusBar(
                modifier = Modifier.align(Alignment.BottomStart),
                status = data.status,
                target = 30,
                current = 10
            )
            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom
            ) {
//                Icon(painter = Icons.Default.Restore, contentDescription = "")
                Icon(
                    imageVector = Icons.Default.Restore,
                    contentDescription = "",
                    modifier = Modifier.size(15.dp),
                    tint = GrayNormalText
                )
                Text(
                    text = "倒數13天",
                    modifier = Modifier
                        .padding(start = 3.dp),
                    fontSize = 9.sp,
                    color = GrayNormalText
                )
            }
//            Icons.Default.Restore
        }
    }
}

@Composable
fun CourseStatusBar(
    modifier: Modifier,
    status: String,
    target: Int,
    current: Int
) {

    Column(
        modifier = Modifier
    ) {
        Text(
            text = ""
        )


    }

}

@Preview
@Composable
fun PreviewMainCourseItem() {
    MainCourseItem(testCourse)
}

