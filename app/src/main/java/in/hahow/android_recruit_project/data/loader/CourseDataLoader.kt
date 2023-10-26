package `in`.hahow.android_recruit_project.data.loader

import `in`.hahow.android_recruit_project.data.model.CourseData

interface CourseDataLoader {
    fun loadAllCourses(): CourseData
}