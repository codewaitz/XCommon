package android.lib.common.bean

import java.util.*

/**
 * @author: yangkui
 * @Date: 2023/4/25
 * @Description: 语言
 */
open class Language {
    var key: String = ""
    var locale: Locale? = null
    var name: String = ""

    constructor(key: String, locale: Locale?, name: String) {
        this.key = key
        this.locale = locale
        this.name = name
    }
}