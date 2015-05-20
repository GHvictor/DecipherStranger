package com.android.decipherstranger.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.channels.Channels;

/**
 * Created by Feng on 2015/4/12.
 */
/*
* Example：
* Bitmap bmp = BitmapFactory.decodeFile(sdcardTempFile.getAbsolutePath());
* ChangeUtils change = new ChangeUtils();
* String s = change.toBinary(bmp);
* Bitmap result = change.toBitmap(s);
* showImage.setImageBitmap(result);
* //注释代表两种写法。
* */
public class ChangeUtils {
    private byte[] buffer;

    /*
        * toBinary 将Bitmap转为String
        * @param bitmap 要转换的
        * */
    public static String toBinary(Bitmap bitmap) {
        if (bitmap == null) {
            return "False";
        }
        String s = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] Buffer = os.toByteArray();
        //s = new String(Buffer);
        s = Base64.encodeToString(Buffer, Base64.DEFAULT);
        try {
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    /*
    * toBinary 将File转为String
    * @param file 要转换的
    * */
    public static String toBinary(File file) {
        String s = null;
        int len = 0;
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] Buffer = new byte[1024];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            while ((len = inputStream.read(Buffer)) >= 0) {
                os.write(Buffer, 0, len);
            }
            //s = new String(Buffer);
            s = Base64.encodeToString(Buffer, Base64.DEFAULT);
            os.flush();
            os.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String readStream(File file){
        //File file = new File(path);
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            while ((len = inStream.read(buffer)) != -1)
            {
                outStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = outStream.toByteArray();
        String mImage = new String(Base64.encode(data,Base64.DEFAULT));
        try {
            outStream.close();
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mImage;
    }

    /*
    * toBitmap 将String转为Bitmap
    * @param 转换的String
    * */
    public static Bitmap toBitmap(String s) {
        //byte[] Buffer = s.getBytes();
        byte[] buffer = Base64.decode(s, Base64.DEFAULT);
        //ByteArrayInputStream inputStream = new ByteArrayInputStream(Buffer);
        //Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length, null);
        return bitmap;
    }

    /*
    * toFile 将String转为File
    * @param 转换的String
    * */
    public static File toFile(String s,String dir,String fileName) {
        //byte[] Buffer = s.getBytes();
        byte[] Buffer = Base64.decode(s, Base64.DEFAULT);
        File file =new File(dir,fileName);
        //ByteArrayInputStream is = new ByteArrayInputStream(Buffer);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(Buffer);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File stringFile(String res, String filePath)
    {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        File distFile = new File(filePath);
        try
        {

            if (!distFile.getParentFile().exists())
                distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024]; // 字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1)
            {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            flag = false;
            return distFile;
        }
        finally
        {
            if (bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return distFile;
    }
}
