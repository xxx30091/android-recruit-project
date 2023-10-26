package `in`.hahow.android_recruit_project.utils

import `in`.hahow.android_recruit_project.utils.DateTimeUtils.daysBetweenDateAndToday
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

object DateTimeUtils {

    fun daysBetweenDateAndToday(dateString: String): Int {
        try {
            // 1. 將日期字串解析為 Date 對象
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = dateFormat.parse(dateString)

            if (date != null) {
                // 2. 取得今天的日期
                val today = Date()

                // 3. 使用 Calendar 計算日期之間的差異
                val calendar1 = Calendar.getInstance()
                val calendar2 = Calendar.getInstance()
                calendar1.time = date
                calendar2.time = today

                // 計算相差的天數
                val diffMillis = calendar1.timeInMillis - calendar2.timeInMillis

                return (diffMillis / (1000 * 60 * 60 * 24)).toInt()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 如果解析或計算出現問題，傳回一個標誌值（例如 -1）或拋出異常，視情況而定
        return -1
    }

}

// 測試
fun main() {
    val dateString = "2023-11-01T16:00:00.000Z"
    val daysDifference = daysBetweenDateAndToday(dateString)
    if (daysDifference != -1) {
        println("距離今天的天數為: $daysDifference 天")
    } else {
        println("日期解析或計算出現問題")
    }
}