package android.lib.common.base

import io.reactivex.rxjava3.disposables.CompositeDisposable


/**
 * @author: yangkui
 * @Date: 2022/4/8
 * @Description: Model基类
 */
abstract class BaseModel {
    var compositeDisposable = CompositeDisposable()

    //主动解除所有订阅者
    fun dispose() {
        compositeDisposable.dispose()
        disposeDatabase()
    }

    open fun disposeDatabase() {}
}