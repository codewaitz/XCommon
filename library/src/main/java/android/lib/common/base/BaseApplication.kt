package android.lib.common.base

import android.app.ActivityManager
import android.app.Application
import android.lib.common.exception.CrashHandler
import android.lib.common.manager.AllActivityManager
import android.os.Process
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV

/**
 * @author: yangkui
 * @Date: 2022/4/8
 * @Description: Application 基类
 */
open class BaseApplication : Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        initInMainProcess {
            instance = this
            MMKV.initialize(this)
            AllActivityManager.init(this)
            // live bus
            LiveEventBus.config().autoClear(true).lifecycleObserverAlwaysActive(true)
            lifecycleApp() // lifecycle
            Thread.setDefaultUncaughtExceptionHandler(CrashHandler()) // crash
        }
    }

    /**
     * 主进程初始化
     */
    private fun initInMainProcess(block: () -> Unit) {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val myPId = Process.myPid()
        val mainProcessName = packageName
        activityManager.runningAppProcesses.forEach {
            if (it.pid == myPId && it.processName == mainProcessName) {
                block()
            }
        }
    }

    /**
     * 应用生命周期
     */
    private fun lifecycleApp() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                // 应用启动埋点
                // reportAppStart(0)
                // 应用返回前台，记录时间戳
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                // 应用进入后台埋点
                // reportAppStayTime(applicationStartTime, System.currentTimeMillis())
            }
        })
    }

}