package `in`.hahow.android_recruit_project

import android.R
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import `in`.hahow.android_recruit_project.model.CourseData
import `in`.hahow.android_recruit_project.ui.screen.main.MainCourseItem
import `in`.hahow.android_recruit_project.ui.screen.main.testCourse
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
//                Greeting("Android")
                MainCourseItem(data = testCourse)
            }
        }

        loadJsonFile()
    }

    fun loadJsonFile() {
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