package android.lib.common.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils


/**
 * @author: yangkui
 * @Date: 2022/4/15
 * @Description: 系统
 */
object SystemUtil {
    // 去应用详情页
    fun gotoAppDetailIntent(activity: Activity) {
        JumpPermissionManagement.GoToSetting(activity)
    }

    // 复制到剪贴板
    fun copy2Board(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(
            ClipData.newPlainText(
                null,
                text
            )
        ) //参数一：标签，可为空，参数二：要复制到剪贴板的文本
        if (clipboard.hasPrimaryClip()) {
            clipboard.primaryClip!!.getItemAt(0).text
        }
    }

    fun isLocationEnabled(context: Context): Boolean {
        var locationMode: Int
        val locationProviders: String
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            locationMode = try {
                Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                return false
            }
            locationMode != Settings.Secure.LOCATION_MODE_OFF
        } else {
            locationProviders = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED
            )
            !TextUtils.isEmpty(locationProviders)
        }
    }

    // 分享
    fun gotoShare(context: Context) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, "This is text to send")
        intent.type = "text/plain"
        context.startActivity(Intent.createChooser(intent, "share"))
    }

    // 拨打电话
    fun gotoCall(context: Context, phone: String) {
        if (VerificationUtil.isMobile(phone)) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
            context.startActivity(intent)
        }
    }

    // 获取应用名
    fun getAppName(context: Context): String {
        var appName = ""
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                context.packageName, 0
            )
            val labelRes = packageInfo.applicationInfo.labelRes
            appName = context.resources.getString(labelRes)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return appName
    }
}