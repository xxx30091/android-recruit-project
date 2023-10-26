package `in`.hahow.android_recruit_project.data.model

data class CourseItem(
    val successCriteria: SuccessCriteria,
    val coverImageUrl: String = "",
    val proposalDueTime: String? = "",
    val numSoldTickets: Int = 0,
    val title: String = "",
    val status: String = ""
)