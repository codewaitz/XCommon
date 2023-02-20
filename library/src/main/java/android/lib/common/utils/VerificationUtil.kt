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
        return mobile.isNotEmpty()
    }

    // 校验验证码
    fun isCode(code: String): Boolean {
        return code.isNotEmpty()
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