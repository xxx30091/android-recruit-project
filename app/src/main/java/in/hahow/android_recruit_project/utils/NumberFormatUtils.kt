package `in`.hahow.android_recruit_project.utils

import kotlin.math.roundToInt

/**
 * 因為感覺假設專案要繼續做下去，會有很多重費的數字轉換，
 * 所以直接寫成 Util 方便重複使用
 */
object NumberFormatUtils {
    // 將現有數對目標數的占比轉為文字形式的百分比，設有上限 100%
    fun formatTargetPercentString(current: Int, target: Int): String {
        return if (current >= target) "100%"
        else ((current.toDouble() / target.toDouble()) * 100).roundToInt().toString() + "%"
    }

    // 將現有人數比上目標人數，現有數顯示上限為目標數
    fun formatIncubatingTargetString(current: Int, target: Int): String {
        return if (current > target) "$target / $target 人"
        else "$current / $target 人"
    }

    // 將現有數對目標數的占比轉為達標百分比，沒設上限
    fun formatTargetSuccessString(current: Int, target: Int): String {
        return "達標 ${((current.toDouble() / target.toDouble()) * 100).roundToInt()}%"
    }
}

// 測試
fun main() {
//    println(NumberFormatUtils.formatTargetPercentString(8, 30))
//    println(NumberFormatUtils.formatIncubatingTargetString(8, 30))
    println(NumberFormatUtils.formatTargetSuccessString(95, 30))
}