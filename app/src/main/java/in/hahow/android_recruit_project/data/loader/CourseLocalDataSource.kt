package `in`.hahow.android_recruit_project.data.loader

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.data.model.CourseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CourseLocalDataSource(private val context: Context) : CourseRepository {

    override suspend fun loadAllCourses(): CourseData {
        val inputStream = context.resources.openRawResource(R.raw.data)

        val size = withContext(Dispatchers.IO) {
            inputStream.available()
        }
        val buffer = ByteArray(size)
        withContext(Dispatchers.IO) {
            inputStream.read(buffer)
            inputStream.close()
        }

        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        val listType = object : TypeToken<CourseData>() {}.type

        return gson.fromJson(json, listType)
    }
}
