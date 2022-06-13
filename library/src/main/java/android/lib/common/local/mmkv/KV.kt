package android.lib.common.local.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * @author: yangkui
 * @Date: 2022/4/8
 * @Description: MMKV
 */
object KV {
    private var mmkv: MMKV? = null

    init {
        mmkv = MMKV.defaultMMKV()
    }

    fun encode(key: String, value: Any?) {
        when (value) {
            is String -> mmkv?.encode(key, value)
            is Float -> mmkv?.encode(key, value)
            is Boolean -> mmkv?.encode(key, value)
            is Int -> mmkv?.encode(key, value)
            is Long -> mmkv?.encode(key, value)
            is Double -> mmkv?.encode(key, value)
            is ByteArray -> mmkv?.encode(key, value)
            is Nothing -> return
        }
    }

    fun encodeParcelable(key: String?, obj: Parcelable) {
        mmkv?.encode(key, obj)
    }

    fun <T : Parcelable> decodeParcelable(key: String?, tClass: Class<T>): Parcelable? {
        return mmkv?.decodeParcelable(key, tClass)
    }

    fun decodeInt(key: String): Int? {
        return mmkv?.decodeInt(key, 0)
    }

    fun decodeDouble(key: String): Double? {
        return mmkv?.decodeDouble(key, 0.00)
    }

    fun decodeLong(key: String): Long? {
        return mmkv?.decodeLong(key, 0L)!!
    }

    fun decodeBoolean(key: String): Boolean? {
        return mmkv?.decodeBool(key, false)
    }

    fun decodeBooleanTrue(key: String): Boolean? {
        return mmkv?.decodeBool(key, true)
    }

    fun decodeFloat(key: String): Float? {
        return mmkv?.decodeFloat(key, 0F)
    }

    fun decodeByteArray(key: String): ByteArray? {
        return mmkv?.decodeBytes(key)
    }

    fun decodeString(key: String): String? {
        return mmkv?.decodeString(key, "")
    }

    fun decodeStringDef(key: String, def: String): String? {
        return mmkv?.decodeString(key, def)
    }

    fun removeKey(key: String) {
        mmkv?.removeValueForKey(key)
    }

    fun clearAll() {
        mmkv?.clearAll()
    }
}