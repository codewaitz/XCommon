package android.lib.common.exception

/**
 * @author: yangkui
 * @Date: 2022/4/8
 * @Description: NetworkException
 */
class NetworkException private constructor(val code: String, message: String) : RuntimeException(message) {

    override fun toString(): String {
        return "exception code is $code msg is $message"
    }

    companion object {
        fun of(code: String, message: String) = NetworkException(code, message)
    }

}