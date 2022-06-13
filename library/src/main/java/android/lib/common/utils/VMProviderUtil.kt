package android.lib.common.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner


/**
 * @author: yangkui
 * @Date: 2022/4/15
 * @Description:ViewModelProviderUtil
 */
class VMProviderUtil {
    companion object {
        fun <T : ViewModel?> Provider(
            owner: ViewModelStoreOwner,
            viewModelClass: Class<T>
        ): T {
            return ViewModelProvider(owner).get(viewModelClass)
        }
    }
}