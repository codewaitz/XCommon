package android.lib.common.widget.picture;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Desc     ${图片相关工具类}
 */

public class PictureImageUtils {
    /**
     * 获取图片目录
     *
     * @return 图片目录（/storage/emulated/0/Pictures）
     */
    public static File getExtPicturesPath() {
        File extPicturesPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!extPicturesPath.exists()) {
            extPicturesPath.mkdir();
        }
        return extPicturesPath;
    }

    /**
     * 获取图片路径
     *
     * @param context Context
     * @param uri     图片 Uri
     * @return 图片路径
     */
    public static String getImagePath(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}