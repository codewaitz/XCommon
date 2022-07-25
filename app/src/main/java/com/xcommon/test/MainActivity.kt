package com.xcommon.test

import android.lib.common.base.BaseActivity
import android.lib.common.base.BaseViewModel
import android.lib.common.utils.VMProviderUtil
import android.lib.common.widget.toast.XToast
import android.os.Bundle
import com.xcommon.test.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var viewModel: MainViewModel

    override fun getViewModel(): BaseViewModel? {
        viewModel = VMProviderUtil.Provider(this, MainViewModel::class.java)
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb.btn.setOnClickListener {
            XToast.showSuccess("Test is Success")
        }
    }

    override fun onCreate() {
        viewModel.request()
    }
}