package `in`.hahow.android_recruit_project.data.loader

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.data.model.CourseData

class JsonCourseDataLoader(private val context: Context) : CourseDataLoader {
    override fun loadAllCourses(): CourseData {
        val inputStream = context.resources.openRawResource(R.raw.data)

        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)

        val gson = Gson()
        val listType = object : TypeToken<CourseData>() {}.type
        val myDataList: CourseData = gson.fromJson(json, listType)

        Log.i("Arthur", "$myDataList")
        Log.i("Arthur", "title:${myDataList.data?.get(1)?.title}")

        return myDataList
    }
}

//        學長 不好意思
//        我這邊有幾個問題想請教一下
//        1. 作業裡面關於資料的需求：
//        請設計一個的 Data Loader 的抽象層來提供課程資料。
//        請用專案中提供的 json file 實作上述 Data Loader 的一個實例。
//
//        請問這是要做類似 repository pattern 的東西嗎?
//        因為之前沒碰過人家說 Data Loader 這樣子的東西，所以有點疑惑
//
//        2. 專案中的 json file 我可以移動它的位置嗎?
//        例如說移到 resource 的 raw 資料夾中






