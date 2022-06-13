package android.lib.common.manager

import android.app.Activity
import android.app.Application
import android.lib.common.base.BaseActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import java.util.*

/**
 * @author: yangkui
 * @Date: 2022/4/8
 * @Description: Activity 管理
 */
object AllActivityManager {
    private val activityStack = LinkedList<Activity>()

    /**
     * 初始化
     */
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activityStack.add(activity)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                activityStack.remove(activity)
            }
        })
    }

    fun getTopActivity(): Activity? {
        return if (activityStack.isNotEmpty()) activityStack[activityStack.size - 1] else null
    }

    fun getTopAppCompatActivity(): AppCompatActivity? {
        return getTopActivity() as? AppCompatActivity
    }

    fun getTopSelfActivity(): BaseActivity<*>? {
        for (i in activityStack.size - 1 downTo 0) {
            val activity = activityStack[i]
            if (activity is BaseActivity<*>) {
                return activity
            }
        }
        return null
    }

    fun getTopActivityLifecycleScope(): LifecycleCoroutineScope? {
        return getTopAppCompatActivity()?.lifecycleScope
    }

    fun finishAll() {
        for (i in activityStack.size - 1 downTo 0) {
            activityStack[i].finish()
        }
    }
}