package android.lib.common.base

import android.content.Context
import android.lib.common.widget.dialog.CommonLoadingDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * @author: yangkui
 * @Date: 2022/4/8
 * @Description: Fragment基类
 */
abstract class BaseFragment<VB : ViewBinding>(val inflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VB) :
    Fragment() {
    lateinit var vb: VB
    private var vm: BaseViewModel? = null // view model
    lateinit var act: BaseFragmentActivity
    private var loadingDialog: CommonLoadingDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        var requireActivity = requireActivity()
        if (requireActivity is BaseFragmentActivity) {
            act = requireActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = inflater(inflater, container, false)
        vm = getViewModel()
        onCreateView()
        registerLoadingDialog() // 注册弹框
        return vb.root
    }

    // Create
    protected abstract fun onCreateView()

    // get view model
    protected open fun getViewModel(): BaseViewModel? {
        return null
    }

    // 注册loading框
    private fun registerLoadingDialog() {
        vm?.isLoading?.observe(act) {
            if (it) showLoadingDialog()
            else hideLoadingDialog()
        }
    }

    private fun showLoadingDialog() {
        loadingDialog = CommonLoadingDialog.newInstance()
        loadingDialog!!.showDialog(act.supportFragmentManager)
    }

    private fun hideLoadingDialog() {
        if (loadingDialog != null) loadingDialog?.dismissDialog()
        loadingDialog = null
    }

    override fun onDestroy() {
        if (vm != null) vm?.disposable()
        vm = null
        super.onDestroy()
    }
}