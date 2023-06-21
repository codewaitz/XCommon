package android.lib.common.base

import android.app.Application
import android.content.Context
import android.lib.common.exception.CrashHandler
import android.lib.common.manager.AllActivityManager
import android.lib.common.utils.LogUtil
import android.lib.common.widget.dialog.loading.LoadingStyle
import android.lib.common.language.MultiLanguages
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV

/**
 * @author: yangkui
 * @Date: 2022/4/8
 * @Description: Application 基类
 */
open class BaseApplication : Application() {

    companion object {
        // application instance
        lateinit var instance: BaseApplication

        // loading style
        lateinit var loadingStyle: LoadingStyle
    }

    override fun onCreate() {
        super.onCreate()
        instance = this // 实例赋值
        var isDebug = isDebugMode()
        loadingStyle = loadingStyle()
        LogUtil.setMode(isDebug) // 日志模式
        if (isMultiLanguage()) MultiLanguages.init(this)
        AllActivityManager.init(this) // activity manager
        MMKV.initialize(this) // kv init
        LiveEventBus.config().autoClear(true).lifecycleObserverAlwaysActive(true)// live bus
        if (!isDebug) Thread.setDefaultUncaughtExceptionHandler(CrashHandler()) // crash
    }

    override fun attachBaseContext(base: Context?) {
        if (isMultiLanguage())
            super.attachBaseContext(MultiLanguages.attach(base))
        else super.attachBaseContext(base)
    }

    // 获取APP 模式
    open fun isDebugMode(): Boolean {
        return false
    }

    // 加载框样式
    open fun loadingStyle(): LoadingStyle {
        return LoadingStyle.PROGRESS
    }

    // 设置多语言
    open fun isMultiLanguage(): Boolean {
        return false
    }
}