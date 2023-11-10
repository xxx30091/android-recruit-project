package `in`.hahow.android_recruit_project

import android.app.Application
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import dagger.hilt.android.HiltAndroidApp
import `in`.hahow.android_recruit_project.data.loader.CourseLocalDataSource
import `in`.hahow.android_recruit_project.data.loader.CourseRepository
import `in`.hahow.android_recruit_project.widget.BillingClientWrapper
import kotlin.properties.Delegates

@HiltAndroidApp
class ThisApp : Application() {

    val courseRepository: CourseRepository
        get() = CourseLocalDataSource(this)

    val googleBillingWrapper: BillingClientWrapper
        get() = BillingClientWrapper(this)

    companion object {
        var instance: ThisApp by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}