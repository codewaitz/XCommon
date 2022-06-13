package android.lib.common.widget.dialog

import android.lib.common.base.BaseViewModel
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 * @author: yangkui
 * @Date: 2022/4/27
 * @Description:BaseBottomDialog
 */
abstract class CommonBottomVMDialog : CommonBottomDialog {
    private var vm: BaseViewModel? = null // view model
    protected var activity: FragmentActivity
    private var loadingDialog: CommonProgressDialog? = null

    constructor(activity: FragmentActivity) : super(activity) {
        this.activity = activity
    }

    constructor(activity: FragmentActivity, title: String?) : super(activity, title) {
        this.activity = activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        vm = getViewModel()
        registerLoadingDialog()
        super.onCreate(savedInstanceState)
    }

    // get view model
    protected open fun getViewModel(): BaseViewModel? {
        return null
    }

    // 注册loading框
    private fun registerLoadingDialog() {
        vm?.isLoading?.observe(activity) {
            if (it) showLoadingDialog()
            else hideLoadingDialog()
        }
    }

    private fun showLoadingDialog() {
        loadingDialog = CommonProgressDialog.newInstance()
        loadingDialog!!.showDialog(activity.supportFragmentManager)
    }

    private fun hideLoadingDialog() {
        if (loadingDialog != null) loadingDialog?.dismissDialog()
        loadingDialog = null
    }
}