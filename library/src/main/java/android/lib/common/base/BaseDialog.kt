package android.lib.common.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface

/**
 * @author: yangkui
 * @Date: 2022/4/27
 * @Description:
 */
abstract class BaseDialog : Dialog {
    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)
}