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
    private var loadingDialog: BaseLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        prepareCreate()
        super.onCreate(savedInstanceState)
        initSystemBar() // status bar
        vb = inflater(layoutInflater)
        setContentView(vb.root)
        vm = getViewModel()
        onCreate() //create
        registerLoadingDialog() // 注册弹框
    }

    // Create prepare
    protected open fun prepareCreate() {}

    // Create
    protected abstract fun onCreate()

    // get view model
    protected open fun getViewModel(): BaseViewModel? {
        return null
    }


    // status bar color
    protected open fun getStatusBarColor(): Int {
        return -1
    }

    /**
     * 状态栏导航栏初始化
     */
    private fun initSystemBar() {
        immersionBar {
            if (getStatusBarColor() == -1) transparentStatusBar()
            else statusBarColor(getStatusBarColor())
            statusBarDarkFont(true)
            fitsSystemWindows(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    // 注册loading框
    private fun registerLoadingDialog() {
        vm?.isLoading?.observe(this) {
            if (it) showLoadingDialog()
            else hideLoadingDialog()
        }
    }

    private fun showLoadingDialog() {
        if (loadingDialog == null) loadingDialog =
            LoadingDialogManager.createInstance(BaseApplication.loadingStyle)
        if (loadingDialog?.isVisible == false) loadingDialog?.showDialog(supportFragmentManager)
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismissDialog()
    }

    override fun onDestroy() {
        if (vm != null) vm?.disposable()
        vm = null
        super.onDestroy()
    }
}