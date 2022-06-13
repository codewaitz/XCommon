package android.lib.common.utils

import java.util.regex.Pattern

/**
 * @author: yangkui
 * @Date: 2022/4/15
 * @Description: 校验
 */
object VerificationUtil {
    // 校验用户名
    fun isUsername(str: String): Boolean {
        var regex = "[a-zA-Z]+"
        var p = Pattern.compile(regex)
        var m = p.matcher(str)
        return str.isNotEmpty() && str.length <= 30 && m.matches()
    }

    // 校验手机号
    fun isMobile(mobile: String): Boolean {
        var regex =
            "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$";
        var p = Pattern.compile(regex);
        var m = p.matcher(mobile);
        return mobile.isNotEmpty() && mobile.length == 11 && m.matches()
    }

    // 校验验证码
    fun isCode(code: String): Boolean {
        return code.isNotEmpty() && code.length == 4
    }

    // 校验密码
    fun isPassword(password: String): Boolean {
        var regex = "^[0-9]*$"
        var p = Pattern.compile(regex)
        var m = p.matcher(password)
        if (!m.matches()) {
            regex = "^[A-Za-z]+$"
            p = Pattern.compile(regex)
            m = p.matcher(password)
            if (!m.matches()) {
                return true
            }
        }
        return false
    }
}