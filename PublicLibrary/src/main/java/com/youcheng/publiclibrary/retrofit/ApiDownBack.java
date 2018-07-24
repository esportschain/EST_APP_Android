package com.youcheng.publiclibrary.retrofit;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * 下载
 * Created by wangtong on 2017/1/13.
 */

public class ApiDownBack {
    /**
     * 写入文件
     *
     * @param body
     * @param articleId
     * @return
     */
    public static boolean writeResponseBodyToDisk(ResponseBody body, String articleId, String suffix) {
        try {
            // todo change the file location/name according to your needs
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + articleId + "." + suffix;

            File futureStudioIconFile = new File(path);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                futureStudioIconFile.delete();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + articleId + "." + suffix;
            File futureStudioIconFile = new File(path);
            futureStudioIconFile.delete();
            return false;
        }
    }
}
