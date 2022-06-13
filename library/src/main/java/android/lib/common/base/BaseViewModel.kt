package android.lib.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel
 */
abstract class BaseViewModel : ViewModel {
    private var baseModel: BaseModel
    var isLoading = MutableLiveData<Boolean>()

    constructor() : super() {
        baseModel = createModel()
    }

    abstract fun createModel(): BaseModel

    // disposable
    fun disposable() {
        baseModel.dispose()
    }
}