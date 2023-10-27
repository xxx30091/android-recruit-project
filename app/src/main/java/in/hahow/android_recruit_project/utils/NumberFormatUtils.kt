package `in`.hahow.android_recruit_project.utils

import kotlin.math.roundToInt

/**
 * 因為感覺假設專案要繼續做下去，會有很多重費的數字轉換，
 * 所以直接寫成 Util 方便重複使用
 */
object NumberFormatUtils {
    fun formatTargetPercentString(current: Int, target: Int): String {
        return if (current >= target) "100%"
        else ((current.toDouble() / target.toDouble()) * 100).roundToInt().toString() + "%"
    }

    fun formatIncubatingTargetString(current: Int, target: Int): String {
        return if (current > target) "$target / $target 人"
        else "$current / $target 人"
    }

    fun formatTargetSuccessString(current: Int, target: Int): String {
        return "達標 ${((current.toDouble() / target.toDouble()) * 100).roundToInt()}%"
    }
}

// 測試
fun main() {
//    println(NumberFormatUtils.formatTargetPercentString(8, 30))
    println(NumberFormatUtils.formatIncubatingTargetString(8, 30))
    println(NumberFormatUtils.formatTargetSuccessString(95, 30))
}