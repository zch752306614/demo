package com.alice.support.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Description 文件读写工具类
 * @DateTime 2023/12/22 17:55
 */
@Slf4j
public class MyFileUtils {

    public static void downloadFile(String fileUrl, String savePath) {
        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(savePath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            log.info(String.format("File downloaded successfully to: %s", savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
