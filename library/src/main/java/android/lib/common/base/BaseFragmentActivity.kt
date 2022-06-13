package android.lib.common.base

import androidx.appcompat.app.AppCompatActivity

/**
 * @author: yangkui
 * @Date: 2022/5/25
 * @Description: BaseFragmentActivity
 */
abstract class BaseFragmentActivity : AppCompatActivity() {
    open fun onFragmentCallback(any: Any?) {}
}