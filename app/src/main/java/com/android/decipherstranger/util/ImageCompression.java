package com.android.decipherstranger.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Feng on 2015/5/11.
 * ImageCompress Util Just little Used
 * Test Result
 * Function compressSimplify() -> Some Effect
 * Function comp() -> img.size() < 1M no Effect
 */
public class ImageCompression {
    public static Bitmap compressSimplify(Bitmap image, float scale){
        int imgW = image.getWidth();
        int imgH = image.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        Bitmap sImage = Bitmap.createBitmap(image, 0, 0, imgW, imgH, matrix, true);
        return sImage;
    }

    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, os);
        int options = 100;
        while ( os.toByteArray().length / 1024>100) {
            os.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.PNG, options, os);
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, null);
        return bitmap;
    }

    private static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;
        float ww = 480f;
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);
    }

    public static Bitmap comp(Bitmap image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, os);
        if( os.toByteArray().length / 1024>1024) {
            os.reset();
            image.compress(Bitmap.CompressFormat.PNG, 50, os);
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 40f;
        float ww = 40f;
        int mark = 1;
        if (w > h && w > ww) {
            mark = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            mark = (int) (newOpts.outHeight / hh);
        }
        if (mark <= 0)
            mark = 1;
        newOpts.inSampleSize = mark;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        return compressImage(bitmap);
    }
}
