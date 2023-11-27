package com.alice.support.module.demo.until;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageMarkLogoByIcon {
    public static void markImageByIcon(String iconPath, String srcImgPath, String targetPath, Integer degree, Integer x, Integer y) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            // 得到Image对象
            Image img = imgIcon.getImage();
            // 透明度
            float alpha = 1f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 表示水印图片的位置
            g.drawImage(img, x, y, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            os = new FileOutputStream(targetPath);
            // 生成图片
            ImageIO.write(buffImg, "JPG", os);
            System.out.println("水印添加成功...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.gc();
        }
    }

    public static void markImageByIcon(String iconPath, String srcImgPath, String targetPath, Integer x, Integer y) {
        markImageByIcon(iconPath, srcImgPath, targetPath, null, x, y);
    }

    public static void main(String[] args) {
        String srcImgPath = "content.jpg";
        String iconPath = "icon.jpg";
        String targetPath = "img_mark_icon.jpg";
        String targetPath2 = "img_mark_icon_rotate.jpg";
        //给图片添加水印
        ImageMarkLogoByIcon.markImageByIcon(iconPath, srcImgPath, targetPath, 10, 10);
        //给图片添加水印,水印旋转-45
        ImageMarkLogoByIcon.markImageByIcon(iconPath, srcImgPath, targetPath2, -45, 10, 10);
    }
}
