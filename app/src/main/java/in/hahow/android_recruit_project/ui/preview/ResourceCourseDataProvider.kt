package `in`.hahow.android_recruit_project.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import `in`.hahow.android_recruit_project.data.model.CourseItem
import `in`.hahow.android_recruit_project.data.model.SuccessCriteria

// 單純為了用來看 preview 所做的 mock data (官方推薦用來提供 preview data 的模式)
class CourseDataProvider : PreviewParameterProvider<CourseItem>{
    override val values = sequenceOf(
        CourseItem(
            successCriteria = SuccessCriteria(numSoldTickets = 30),
            numSoldTickets = 0,
            status = "INCUBATING",
            proposalDueTime = "2023-11-01T16:00:00.000Z",
            coverImageUrl = "https://images.api.hahow.in/images/614eca15a39712000619b672",
            title = "學習 AI 一把抓：點亮人工智慧技能樹"
        )
    )
}
