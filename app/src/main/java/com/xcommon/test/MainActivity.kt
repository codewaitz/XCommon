package com.xcommon.test

import android.lib.common.base.BaseActivity
import android.lib.common.base.BaseViewModel
import android.lib.common.utils.VMProviderUtil
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
        setContentView(R.layout.activity_main)
    }

    override fun onCreate() {
        viewModel.request()
    }
}