package android.lib.common.manager

import android.annotation.SuppressLint
import android.content.Context
import android.lib.common.base.BaseApplication
import android.lib.common.utils.StringUtil
import android.lib.common.utils.SystemUtil
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Bundle
import android.os.CountDownTimer

/**
 * @author: yangkui
 * @Date: 2022/4/29
 * @Description: 定位管理
 */
object LocationManager {
    // 定位配置
    private var locationManager: LocationManager? = null // 定位管理
    private var locationListener: LocationListener? = null // 定位监听
    private var enableProviders: MutableList<String>? = null // 系统提供可用Provider
    private const val locationMis: Long = 5 * 1000 // 定位间隔
    private const val locationMinDistance = 1f // 定位最小距离

    // 数据
    private var lastLocation: Location? = null // 上次定位数据
    private var lastProvider = NETWORK_PROVIDER //上次定位Provider
    private var onLocationListenerList = mutableListOf<OnLocationListener>() // 定位监听器列表
    private var isLocationRunning = false // 定位中
    private var oneLocationTimer: CountDownTimer? = null // 单次定位超时定时器
    private var oneLocationProviders = mutableListOf<String>() // 单次定位使用的Provider

    // 设置优先匹配Provider
    fun setPriorProvider(provider: String) {
        this.lastProvider = provider
    }

    // 定位，如果已经定位过，则获取上次数据
    fun quickLocation(onLocationListener: OnLocationListener?) {
        if (lastLocation != null) {
            onLocationListener?.onLocation(lastLocation)
        } else {
            startLocation(onLocationListener)
        }
    }

    // 开始定位
    fun startLocation(onLocationListener: OnLocationListener?) {
        synchronized(this) {
            try {
                // 添加定位监听
                addOnLocationListener(onLocationListener)
                // 定位设置是否开启
                if (!SystemUtil.isLocationEnabled(BaseApplication.instance)) {
                    locationFailedCallback()
                    return
                }
                // 定位管理
                if (locationManager == null) locationManager =
                    BaseApplication.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val locManager = locationManager
                if (locManager == null) {
                    locationFailedCallback()
                    return
                }
                // 获取Provider
                if (enableProviders == null) enableProviders = locManager.getProviders(true)
                if (enableProviders == null) {
                    locationFailedCallback()
                    return
                }
                locationRun(false) // 开始定位
            } catch (ex: Exception) {
                locationFailedCallback()
            }
        }
    }

    // 运行定位
    @SuppressLint("MissingPermission")
    private fun locationRun(isChangeProvider: Boolean) {
        // 如果在定位中，不再继续执行一次
        if (isLocationRunning && !isChangeProvider) {
            return
        }
        // 开始定位
        isLocationRunning = true
        // 匹配最佳provider
        lastProvider = matchBastProvider(enableProviders)
        if (StringUtil.isEmpty(lastProvider)) {
            locationFailedCallback()
            oneLocationProviders.clear() // 单次匹配全部provider均不可用
            return
        }
        // 定位监听
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // 定位成功， 如果没有监听者，则关闭持续监听
                lastLocation = location
                cancelTimer()
                if (isLocationRunning) locationCallback(location)
                if (onLocationListenerList.size < 1) {
                    releaseLocationListener()
                    isLocationRunning = false
                }
            }

            override fun onProviderDisabled(provider: String) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }
        }
        // 定位超时监听 1个provider执行10秒
        oneLocationTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(mis: Long) {
            }

            override fun onFinish() {
                // 已经执行超时
                releaseLocationListener()
                lastProvider = ""
                locationRun(true) // 再次匹配provider定位
            }
        }
        oneLocationTimer?.start()
        // 开始定位
        val locListener = locationListener
        if (locListener != null) {
            oneLocationProviders.add(lastProvider)
            locationManager?.requestLocationUpdates(
                lastProvider,
                locationMis,
                locationMinDistance,
                locListener
            )
        }
    }

    // 匹配最佳Provider
    private fun matchBastProvider(enableProviderList: MutableList<String>?): String {
        try {  // 匹配最佳的Provider
            if (enableProviderList != null && enableProviderList.size > 0) {
                if (!StringUtil.isEmpty(lastProvider) && enableProviderList.contains(lastProvider) && !oneLocationProviders.contains(
                        lastProvider
                    )
                ) {
                    return lastProvider
                }
                for (provider in enableProviderList) {
                    if (!oneLocationProviders.contains(provider)) {
                        return provider
                    }
                }
            }
        } catch (ex: Exception) {
        }
        return ""
    }

    //定位失败回调
    private fun locationFailedCallback() {
        locationCallback(null)
        isLocationRunning = false
    }

    // 释放定位资源
    @SuppressLint("MissingPermission")
    private fun releaseLocationListener() {
        val locListener = locationListener
        if (locListener != null) locationManager?.removeUpdates(locListener)
        locationListener = null
    }

    // 定位结果回调
    private fun locationCallback(location: Location?) {
        val tempLocListener = mutableListOf<OnLocationListener>()
        for (locListener in onLocationListenerList) {
            locListener.onLocation(location)
            // 如果是单次定位，定位后直接删除callback
            if (locListener.isSingle) tempLocListener.add(locListener)
        }
        if (tempLocListener.size > 0) onLocationListenerList.removeAll(tempLocListener)
    }

    // 移除监听器
    fun removeOnLocationListener(onLocationListener: OnLocationListener?) {
        if (onLocationListenerList.contains(onLocationListener)) {
            onLocationListenerList.remove(onLocationListener)
        }
    }

    // 添加监听
    private fun addOnLocationListener(onLocationListener: OnLocationListener?) {
        if (onLocationListener != null && !onLocationListenerList.contains(onLocationListener)) {
            onLocationListenerList.add(onLocationListener)
        }
    }

    // 取消超时器
    private fun cancelTimer() {
        oneLocationTimer?.cancel()
        oneLocationTimer = null
    }

    // 定位监听器
    abstract class OnLocationListener {
        var isSingle = true // 单次

        // 构造器
        constructor()
        constructor(isSingle: Boolean) {
            this.isSingle = isSingle
        }

        // 定位返回
        abstract fun onLocation(location: Location?)
    }
}