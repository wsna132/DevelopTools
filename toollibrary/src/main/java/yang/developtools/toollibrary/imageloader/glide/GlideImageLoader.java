package yang.developtools.toollibrary.imageloader.glide;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

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
              into(targetImageView);
    }


    /**
     * 显示图片，通过图片文件路径加载
     * @param context
     * @param file
     * @param targetImageView
     */
    public void showImage(Activity context, File file, ImageView targetImageView){
        Glide.with(context).
                load(file).
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
                into(targetImageView);
    }

    /********************************这里只是一个小例子，用于说明如何用resourceId来生成一个Uri****************************************/
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }


}
