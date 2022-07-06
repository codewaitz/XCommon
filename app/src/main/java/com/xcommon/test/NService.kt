package com.xcommon.test

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * @author: yangkui
 * @Date: 2022/7/6
 * @Description: NService
 */
class NService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}