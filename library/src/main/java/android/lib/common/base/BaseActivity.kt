package android.lib.common.base

import android.lib.common.R
import android.lib.common.manager.LoadingDialogManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author: yangkui
 * @Date: 2022/4/8
 * @Description: Activity基类
 */
abstract class BaseActivity<VB : ViewBinding>(val inflater: (inflater: LayoutInflater) -> VB) :
    BaseFragmentActivity() {
    protected lateinit var vb: VB // view binding
    private var vm: BaseViewModel? = null // view model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareCreate() // prepare create
        setSystemBar() // status bar
        vb = inflater(layoutInflater)
        setContentView(vb.root)
        vm = getViewModel()
        registerLoadingDialog() // 注册弹框
        onCreate() //create
    }

    // Create prepare
    protected open fun prepareCreate() {}

    // Create
    protected abstract fun onCreate()

    // get view model
    protected open fun getViewModel(): BaseViewModel? {
        return null
    }

    /**
     * 状态栏导航栏初始化
     */
    protected open fun setSystemBar() {
        immersionBar {
            transparentStatusBar()
            navigationBarColor(R.color.white)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarDarkIcon(true)
        }
    }

    // 注册loading框
    private fun registerLoadingDialog() {
        vm?.isLoading?.observe(this) {
            if (it) LoadingDialogManager.show(this)
            else LoadingDialogManager.dismiss()
        }
    }

    override fun onDestroy() {
        if (vm != null) vm?.disposable()
        vm = null
        super.onDestroy()
    }
}