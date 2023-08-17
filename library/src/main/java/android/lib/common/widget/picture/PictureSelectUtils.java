package android.lib.common.widget.picture;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.FileProvider;

import java.io.File;


/**
 * Desc	        ${选择图片工具类}
 * 使用方法：
 * 1. 调用getByCamera()、getByAlbum()可通过拍照或相册获取图片
 * 2. 在onActivityResult中调用本工具类的onActivityResult方法处理通过相册或拍照获取的图片
 */
public class PictureSelectUtils {
    private static Uri takePictureUri;//拍照图片uri
    private static File takePictureFile;//拍照图片File
    private static Uri cropPictureTempUri;//裁剪图片uri
    private static int w = 200;
    private static int h = 200;
    private static int aspectX = 1;
    private static int aspectY = 1;

    /**
     * 通过相册获取图片
     */
    public static void getByAlbum(ActivityResultLauncher launch) {
        getByAlbum(launch, false);
    }

    /**
     * 通过相册获取图片
     */
    public static void getByAlbum(ActivityResultLauncher launch, Boolean isMoreType) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        String type = "image/*";
        if (isMoreType) {
            type += ";video/*";
        }
        intent.setType(type);
        launch.launch(intent);
    }

    /**
     * 通过拍照获取图片
     */
    public static void getByCamera(Activity activity, ActivityResultLauncher launch) {
        takePictureUri = createImagePathUri(activity);
        if (takePictureUri != null) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, takePictureUri);//输出路径（拍照后的保存路径）
            launch.launch(i);
        } else {
            Toast.makeText(activity, "open camera failed", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 处理拍照或相册获取的图片
     */
    public static void onActivityResult(Activity activity, Intent data, int type, ActivityResultLauncher cropLaunch) {
        Uri uri;
        switch (type) {
            case 0:
                uri = takePictureUri;
                cropLaunch.launch(crop(activity, uri, w, h, aspectX, aspectY));
                /*Android Q 以下发送广播通知图库更新，Android Q 以上使用 insert 的方式则会自动更新*/
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(takePictureFile)));
                }
                break;
            case 1:
                uri = data.getData();
                cropLaunch.launch(crop(activity, uri, w, h, aspectX, aspectY));
                break;
        }
    }

    // 获取拍照uri
    public static Uri getCameraUri() {
        return takePictureUri;
    }

    /**
     * 裁剪，例如：输出100*100大小的图片，宽高比例是1:1
     *
     * @param activity Activity
     * @param uri      图片的uri
     * @param w        输出宽
     * @param h        输出高
     * @param aspectX  宽比例
     * @param aspectY  高比例
     */
    public static Intent crop(Activity activity, Uri uri, int w, int h, int aspectX, int aspectY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        if (aspectX != 0 && aspectX == aspectY) {
            /*宽高比例相同时，华为设备的系统默认裁剪框是圆形的，这里统一改成方形的*/
            if (Build.MANUFACTURER.equals("HUAWEI")) {
                aspectX = 9998;
                aspectY = 9999;
            }
        }
        if (w != 0 && h != 0) {
            intent.putExtra("outputX", w);
            intent.putExtra("outputY", h);
        }
        if (aspectX != 0 || aspectY != 0) {
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
        }

        /*解决图片有黑边问题*/
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);

        /*解决跳转到裁剪提示“图片加载失败”问题*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        cropPictureTempUri = createImagePathUri(activity);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropPictureTempUri);//输出路径(裁剪后的保存路径)
        // 输出格式
        intent.putExtra("outputFormat", "JPEG");
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", true);
        //是否将数据保留在Bitmap中返回
        intent.putExtra("return-data", false);
        return intent;
    }

    // 获取裁剪图片
    public static String getCropFilePath(Activity activity) {
        return PictureImageUtils.getImagePath(activity, cropPictureTempUri);
    }


    /**
     * 创建一个图片地址uri,用于保存拍照后的照片
     *
     * @param activity
     * @return 图片的uri
     */
    public static Uri createImagePathUri(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { //适配 Android Q
            String displayName = String.valueOf(System.currentTimeMillis());
            ContentValues values = new ContentValues(2);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { //SD 卡是否可用，可用则用 SD 卡，否则用内部存储
                takePictureUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                takePictureUri = activity.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
            }
        } else {
            String pathName = new StringBuffer().append(PictureImageUtils.getExtPicturesPath()).append(File.separator)
                    .append(System.currentTimeMillis()).append(".jpg").toString();
            takePictureFile = new File(pathName);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //解决Android 7.0 拍照出现FileUriExposedException的问题
                String authority = activity.getPackageName() + ".fileProvider";
                takePictureUri = FileProvider.getUriForFile(activity, authority, takePictureFile);
            } else {
                takePictureUri = Uri.fromFile(takePictureFile);
            }
        }
        return takePictureUri;
    }

    // 删除图片
    public static void deleteCropImage(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //适配 Android Q
                activity.getContentResolver().delete(cropPictureTempUri, null);
            } else {
                File file = new File(getCropFilePath(activity));
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception exception) {
        }
    }
}