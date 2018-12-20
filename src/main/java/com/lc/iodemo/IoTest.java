package com.lc.iodemo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 学习一些io相关的
 *
 * @author
 * @date 2018年12月20日14:11:13
 */
public class IoTest {
    public static void main(String[] args) {
        new IoTest().emptyFile();
    }
    /**
     * 如果是空文件会死循环
     */
    public void emptyFile() {
        try {
            String convertFileName = "C:\\Users\\ifly_lc\\Desktop\\new 1.txt";
            File newFile = new File(convertFileName);
            long length = newFile.length();
            FileInputStream fis = new FileInputStream(newFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            //正确写法 while ((n = fis.read(b)) != -1 && n != 0)
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
