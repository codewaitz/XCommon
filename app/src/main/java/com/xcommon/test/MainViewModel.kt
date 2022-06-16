package com.xcommon.test

import android.lib.common.base.BaseModel
import android.lib.common.base.BaseViewModel

/**
 * @author: yangkui
 * @Date: 2022/6/15
 * @Description:
 */
class MainViewModel : BaseViewModel() {
    override fun createModel(): BaseModel {
        return MyModel()
    }

    fun request() {

    }

    class MyModel : BaseModel() {
        fun request() {
            print("request ...")
        }
    }
}