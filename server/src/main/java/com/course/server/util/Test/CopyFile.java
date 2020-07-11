package com.course.server.util.Test;

import com.course.server.util.CopyUtil;

import java.io.*;
import java.text.DecimalFormat;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/9 17:42
 */
public class CopyFile extends Thread {
    public File older;
    public File newer;

    public CopyFile(String older, String newer) {
        this.older = new File(older);
        this.newer = new File(newer);
    }

    @Override
    public void run() {
        try (FileInputStream fis = new FileInputStream(older);
             FileOutputStream fos = new FileOutputStream(newer);) {
            byte[] b = new byte[1024]; //声明一个字节数组,每次读取的数据存到该字节数组里
            int length = 0;//每次读取的数据长度
            long len = older.length();//源文件长度
            double temp = 0;
            DecimalFormat df = new DecimalFormat("##%");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while ((length = fis.read(b)) != -1) {
                fos.write(b, 0, length);//每次读取的内容 输出到目标文件中
                temp += length; //每次读取的数据长度累加
                double d = temp / len;
                int jd = (int) d;
                if (jd % 10 == 0) {
                    System.out.println("文件复制了" + df.format(d));
                }
            }
            System.out.println("复制完毕");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }


}
