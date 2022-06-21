package android.lib.common.utils.glide

import android.R.attr.radius
import android.content.Context
import android.lib.common.utils.StringUtil
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


/**
 * @author: yangkui
 * @Date: 2022/4/15
 * @Description:GlideUtil
 */
class GlideUtil {
    companion object {
        fun load(context: Context, url: String?, imageView: ImageView) {
            load(context, url, imageView, 0)
        }

        fun load(context: Context, url: String?, imageView: ImageView, defaultRes: Int) {
            if (!StringUtil.isEmpty(url)) {
                var builder = Glide.with(context).load(url)
                if (defaultRes != 0) {
                    builder.placeholder(defaultRes).error(defaultRes)
                }
                builder.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(imageView)
            }
        }

        fun loadTopRound(context: Context, url: String?, imageView: ImageView) {
            if (!StringUtil.isEmpty(url)) Glide.with(context).load(url)
                .transform(GlideTopRoundTransform()).diskCacheStrategy(
                    DiskCacheStrategy.NONE
                ).dontAnimate()
                .into(imageView)
        }

        fun loadRound(context: Context, url: String?, imageView: ImageView) {
            if (!StringUtil.isEmpty(url)) Glide.with(context).load(url)
                .transform(GlideRoundTransform()).diskCacheStrategy(
                    DiskCacheStrategy.NONE
                ).dontAnimate()
                .into(imageView)
        }

        fun loadRound(context: Context, url: String?, imageView: ImageView, round: Int) {
            if (!StringUtil.isEmpty(url)) Glide.with(context).load(url)
                .transform(GlideRoundTransform(round)).diskCacheStrategy(
                    DiskCacheStrategy.NONE
                ).dontAnimate()
                .into(imageView)
        }

        fun loadRoundBox(
            context: Context,
            url: String?,
            imageView: ImageView,
            width: Int,
            color: Int
        ) {
            if (!StringUtil.isEmpty(url)) {
                val requestOptions: RequestOptions = RequestOptions()
                    .transform(GlideRoundBoxTransform(context, radius, color, width))
                    .dontAnimate()
                Glide.with(context).load(url).apply(requestOptions).into(imageView)
            }
        }

        fun loadCircle(context: Context, url: String?, imageView: ImageView) {
            if (!StringUtil.isEmpty(url)) Glide.with(context).load(url)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(imageView);
        }

        fun clearCache(context: Context) {
            Glide.get(context).clearDiskCache()
        }
    }
}