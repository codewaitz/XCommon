package android.lib.common.widget.wheel;

/**
 * @author: yangkui
 * @Date: 2022/5/6
 * @Description:OnWheelChangedListener
 */
public interface OnWheelChangedListener {
    void onChanged(WheelView view, int oldIndex, int newIndex);
}