package `in`.hahow.android_recruit_project

import android.app.Application
import `in`.hahow.android_recruit_project.data.loader.CourseLocalDataSource
import `in`.hahow.android_recruit_project.data.loader.CourseRepository
import kotlin.properties.Delegates

class ThisApp : Application() {

    val courseRepository: CourseRepository
        get() = CourseLocalDataSource(this)

    companion object {
        var instance: ThisApp by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}