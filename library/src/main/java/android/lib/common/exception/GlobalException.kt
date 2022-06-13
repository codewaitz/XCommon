package android.lib.common.exception

/**
 * @author: yangkui
 * @Date: 2022/4/8
 * @Description: 异常
 */
class GlobalException private constructor(message: String) : RuntimeException(message) {
    companion object {
        fun of(message: String) = GlobalException(message)
    }
}