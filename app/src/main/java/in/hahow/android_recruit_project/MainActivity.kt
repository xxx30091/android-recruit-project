package `in`.hahow.android_recruit_project

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import `in`.hahow.android_recruit_project.data.model.CourseData
import `in`.hahow.android_recruit_project.ui.screen.main.MainCourseItem
import `in`.hahow.android_recruit_project.ui.screen.main.MainScreen
import `in`.hahow.android_recruit_project.ui.screen.main.testCourse

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val list = loadJsonFile(resources)

        setContent {
            MaterialTheme {
//                MainCourseItem(data = testCourse)
                MainScreen()
            }
        }

//        loadJsonFile(resources)
    }

    // 測試是不是能撈到資料
    fun loadJsonFile(resources: Resources): CourseData {
        val resourceId = resources.getIdentifier("data", "raw", packageName)
        val inputStream = resources.openRawResource(resourceId)

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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = Color.Black
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}