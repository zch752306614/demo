package com.alice.novel.module.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Alice
 * @version 1.0
 * @date 2020/8/15 15:35
 */
public class FileReadAndWrite {

    public static void fileWrite(String url, String text) throws IOException {
        File file = new File(url);
        FileWriter fw = new FileWriter(file);
        int len = 0;
        int maxLen = text.length();
        while (len <= maxLen) {
            fw.write(text.substring(len, (len += 50) <= maxLen ? len : maxLen));
        }
        fw.flush();
        fw.close();
    }

    public static void fileRead(String url) {

    }

}
