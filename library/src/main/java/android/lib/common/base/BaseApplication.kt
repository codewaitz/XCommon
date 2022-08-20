package android.lib.common.base

import android.app.Application
import android.lib.common.exception.CrashHandler
import android.lib.common.manager.AllActivityManager
import android.lib.common.utils.LogUtil
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV

/**
 * @author: yangkui
 * @Date: 2022/4/8
 * @Description: Application 基类
 */
open class BaseApplication : Application() {

    companion object { // 实例
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this // 实例赋值
        var isDebug = isDebugMode()
        LogUtil.setMode(isDebug) // 日志模式
        AllActivityManager.init(this) // activity manager
        MMKV.initialize(this) // kv init
        LiveEventBus.config().autoClear(true).lifecycleObserverAlwaysActive(true)// live bus
        if (!isDebug) Thread.setDefaultUncaughtExceptionHandler(CrashHandler()) // crash
    }

    // 获取APP 模式
    open fun isDebugMode(): Boolean {
        return false
    }
}