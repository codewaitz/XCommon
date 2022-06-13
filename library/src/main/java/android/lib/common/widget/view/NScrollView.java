package android.lib.common.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author: yangkui
 * @Date: 2022/4/27
 * @Description: 禁止滚动
 */
public class NScrollView extends ScrollView {
    public NScrollView(Context context) {
        super(context);
    }

    public NScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
