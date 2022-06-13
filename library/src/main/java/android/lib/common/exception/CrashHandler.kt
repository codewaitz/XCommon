package android.lib.common.exception

import android.lib.common.manager.AllActivityManager
import kotlin.system.exitProcess


/**
 * @author: yangkui
 * @Date: 2022/5/16
 * @Description:CrashHandler
 */
class CrashHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(p0: Thread, p1: Throwable) {
        // 结束所有activity
        AllActivityManager.finishAll()
        exitProcess(1)
    }
}