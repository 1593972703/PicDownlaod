package com.xiaoxiao.picdownlaod;

import android.telephony.mbms.FileInfo;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

/****
 *
 *
 * 文件浏览器相关类
 *
 *
 *
 */
public class FileUtil {


    /**
     * 删除文件或文件夹
     **/
    public static void deleteFile(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; ++i) {
                    deleteFile(files[i]);
                }
            }
        }
        f.delete();
    }

    /**
     * 删除文件夹下的所有内容
     **/
    public static void clearFileDir(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; ++i) {
                    deleteFile(files[i]);
                }
            }
        }
    }

    /**
     * 获取文件MIME类型
     **/
    public static String getMIMEType(String name) {
        String type = "";
        String end = name.substring(name.lastIndexOf(".") + 1, name.length())
                .toLowerCase();
        if (end.equals("apk")) {
            return "application/vnd.android.package-archive";
        } else if (end.equals("mp4") || end.equals("avi") || end.equals("3gp")
                || end.equals("rmvb") || end.equals("mkv") || end.equals("flv")
                || end.equals("f4v") || end.equals("swf")) {
            type = "video";
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("aac")
                || end.equals("amr") || end.equals("ogg") || end.equals("wav")
                || end.equals("wma")) {
            type = "audio";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {
            type = "image";
        } else if (end.equals("txt") || end.equals("log")) {
            type = "text";
        } else {
            type = "*";
        }
        type += "/*";
        return type;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
               boolean isExists =  file.mkdirs();
                Log.e("file:", isExists + "");
            }



        } catch (Exception e) {
            Log.e("error:", e + "");
        }
    }


}
