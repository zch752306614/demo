package com.alice.zhang.demoapi.demo.module.until;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

public class Base64ToImageUtil {

    /**
     * base64字符串转化成图片
     * @param imgStr base64字符串
     * @param imageName 图片名称
     * @return
     * @throws FileNotFoundException
     */
    public static boolean GenerateImage(String imgStr,String imageName,String uploadPath) throws FileNotFoundException {
        File path = new File(ResourceUtils.getURL("classpath:static").getPath());
        if(!path.exists()) path = new File("");
        File upload = new File(path.getAbsolutePath(),uploadPath);
        if(!upload.exists()) upload.mkdirs();
        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        try
        {
            //Base64解码
            byte[] b = Base64.getDecoder().decode(imgStr.replace("\r\n", ""));
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(upload+"/"+imageName);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}

