package android.lib.common.widget.shape;

import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * @author: yangkui
 * @Date: 2022/4/25
 * @Description:CustomShape
 */
public class CustomShape extends ShapeDrawable {
    public CustomShape(int stokeColor, int strokeWidth, int radius) {
        super(
                new RoundRectShape(
                        new float[]{radius, radius, radius, radius, radius, radius, radius, radius},
                        new RectF(strokeWidth, strokeWidth, strokeWidth, strokeWidth),
                        new float[]{radius - 2, radius - 2, radius - 2, radius - 2, radius - 2, radius - 2, radius - 2, radius - 2}));
        Paint paint = getPaint();
        paint.setColor(stokeColor);
        paint.setStyle(Paint.Style.FILL);
    }
}