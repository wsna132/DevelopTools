package yang.developtools.toollibrary.imageloader.glide;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import yang.developtools.toollibrary.imageloader.consts.ImageLoadConsts;

/**
 * Created by yangjh on 2016/12/6 0006.
 * 这个类用于用Glide加载图片
 *
 * Glide用到的加载的类库有：
 *      compile 'com.github.bumptech.glide:glide:3.7.0'
 *
 */
public class GlideImageLoader {

    /**
     * 显示图片,通过网址加载
     * @param context
     * @param internetUrl
     * @param targetImageView
     */
    public void showImage(Activity context, String internetUrl, ImageView targetImageView){
        Glide.with(context).
              load(internetUrl).
              placeholder(ImageLoadConsts.IMG_LOADING). // 加载中显示
              error(ImageLoadConsts.IMG_LOAD_ERROR). // 加载失败时现实
//              dontAnimate().//去除图片加载时的淡入淡出效果
              diskCacheStrategy(DiskCacheStrategy.RESULT).//仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
              into(targetImageView);
    }

    /**
     * 显示图片，通过图片id加载
     * @param context
     * @param imageId
     * @param targetImageView
     */
    public void showImage(Activity context, int imageId, ImageView targetImageView){
        Glide.with(context).
              load(android.R.mipmap.sym_def_app_icon).
              placeholder(ImageLoadConsts.IMG_LOADING). // 加载中显示
              error(ImageLoadConsts.IMG_LOAD_ERROR). // 加载失败时现实
//                dontAnimate().//去除图片加载时的淡入淡出效果
              into(targetImageView);
    }


    /**
     * 用于展示有哪些适配方法的demo
     * @param context
     * @param imageId
     * @param targetImageView
     */
    public void showImageDemo(Activity context, int imageId, ImageView targetImageView){
        Glide.with(context).
                load(android.R.mipmap.sym_def_app_icon).
//                load( Uri.fromFile( new File( filePath ) ) ).//如果想显示视频这样既可，但这仅仅对于本地视频起作用。如果没有存储在该设备上的视频（如一个网络 URL 的视频），它是不工作的！如果你想显示视频从网络 URL，去看看 VideoView。
                asGif().//如果你期望这个URL是一个 Gif，Glide 不会自动检查是否是 Gif。因此他们引入了一个额外的防区强制 Glide变成一个 Gif asGif(),如果不是gif则运行error
//                asBitmap().  如果你仅仅想要显示 Gif 的第一帧，你可以调用 asBitmap() 去保证其作为一个常规的图片显示，即使这个 URL 是一个 Gif
                placeholder(ImageLoadConsts.IMG_LOADING). // 加载中显示
                error(ImageLoadConsts.IMG_LOAD_ERROR). // 加载失败时现实
                dontAnimate().//去除图片加载时的淡入淡出效果
                override(600, 200). // resizes the image to these dimensions (in pixel). does not respect aspect rati   单位为像素
                centerCrop().//即缩放图像让它填充到 ImageView 界限内并且裁剪额外的部分。ImageView 可能会完全填充，但图像可能不会完整显示
                fitCenter(). //是裁剪技术，即缩放图像让图像都测量出来等于或小于 ImageView 的边界范围。该图像将会完全显示，但可能不会填满整个 ImageView
                skipMemoryCache( true ).//禁用内存缓存
                diskCacheStrategy( DiskCacheStrategy.NONE ).//禁用磁盘缓存
                priority( Priority.HIGH ).//设置加载的优先级，四个等级LOW、NORMAL、HIGH、IMMEDIATE
                thumbnail( 0.1f ).//传了一个 0.1f 作为参数，Glide 将会显示原始图像的10%的大小。如果原始图像有 1000x1000 像素，那么缩略图将会有 100x100 像素
                into(targetImageView);
    }
//    关于diskCacheStrategy()方法传入的值得说明
//    diskCacheStrategy() 方法来说不同的枚举参数的意义：
//    DiskCacheStrategy.NONE 什么都不缓存，就像刚讨论的那样
//    DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像。在我们上面的例子中，将会只有一个 1000x1000 像素的图片
//    DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
//    DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）


    /**
     * 显示图片，通过图片文件路径加载
     * @param context
     * @param file
     * @param targetImageView
     */
    public void showImage(Activity context, File file, ImageView targetImageView){
        Glide.with(context).
                load(file).
                placeholder(ImageLoadConsts.IMG_LOADING). // 加载中显示
                error(ImageLoadConsts.IMG_LOAD_ERROR). // 加载失败时现实
//                dontAnimate().//去除图片加载时的淡入淡出效果
                into(targetImageView);
    }


    /**
     * 显示图片，通过图片的Uri加载
     * 下面有生成Uri的小例子
     * @param context
     * @param imageUri
     * @param targetImageView
     */
    public void showImage(Activity context, Uri imageUri, ImageView targetImageView){
        Glide.with(context).
                load(imageUri).
                placeholder(ImageLoadConsts.IMG_LOADING). // 加载中显示
                error(ImageLoadConsts.IMG_LOAD_ERROR). // 加载失败时现实
//                dontAnimate().//去除图片加载时的淡入淡出效果
                into(targetImageView);
    }

    /********************************这里只是一个小例子，用于说明如何用resourceId来生成一个Uri****************************************/
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }


}
