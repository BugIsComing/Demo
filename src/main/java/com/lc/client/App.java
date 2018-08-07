package com.lc.client;

import com.lc.tools.FfmpegUtil;
import org.apache.commons.lang.StringUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static final int equalToTableSizeFor(int cap) {
        if (cap >= MAXIMUM_CAPACITY) {
            return MAXIMUM_CAPACITY;
        }
        int n = cap;
        int count = 0;
        while (n > 0) {
            count++;
            n = n >> 1;
        }
        return 1 << (count);
    }

    public static void main(String[] args) {
//        for (int i=1;i<=32;i++){
//            System.out.println( tableSizeFor(i) + " " +equalToTableSizeFor(i));
//        }

        //Map<String,String> map = new HashMap<String, String>() ;
//        Map<String, String> map2 = new Hashtable<String, String>();
//        Timer tm = new Timer();
//        map2.put(null, "ad");
        //    ConcurrentHashMap<String,String> map1 = new ConcurrentHashMap<String, String>() ;
//        new Hashtable<String, String>();
        //ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//         User user = (User) ac.getBean("user");
//        HashMap<String,String> result = new HashMap<>();

//        if ("0".equalsIgnoreCase("0")) {
//            System.out.println("Hello World!");
//        }
//
//        for (int i = 1; i <= 10; i++) {
//            Random rn = new Random();
//            int answer = rn.nextInt(99)+1;
//            System.out.println("Hello World!" + answer);
//
//        }

//        String url = String.format("%s/%s", "http://lc", "updatesetting");
//        System.out.println("Hello World!" + url);
//        int probability = Integer.parseInt(" 99");
//        System.out.println("Hello World!" + probability);
//        try {
//            URL url = new URL("https://www.univstar.com/v1/pc/qrcode?pid=58aee84902094a27a3627e6a191023c5&t=1524558660776");
//            System.out.println("LCLC");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            URI uri = new URI("https://www.univstar.com/v1/pc/qrcode?pid=58aee84902094a27a3627e6a191023c5&t=1524558660776");
//
//            System.out.println(uri.getQuery());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        String temp = "08553962231|^|125301253041|^|否|^||^||^|2018-05-02 22:28:34|^|2018-05-03 04:27:05|^|21511|^|1|^|外呼日志|^|否|^|0030003|^||^||^|3|^||^||^||^||^||^||^|";
//        String[] arr = temp.split("\\|\\^\\|", -1);
//        System.out.println(arr.length);
//        Integer o = -1234;
//        System.out.println(Integer.toUnsignedString(-1234));

//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String beginTime = "2018-05-24 09:44:36";
//        try {
//            System.out.println(df.parse(beginTime).getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        String srcUrl = "http://10.25.174.50/wave/voiceprint/2018080215332921118880406378.wav";
        String dstPath = "";


        String fileName = srcUrl.substring(srcUrl.lastIndexOf('/') + 1);
        File saveDir;
        File file = null;
        try {

            dstPath = "E:\\";
            URL url = new URL(srcUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            saveDir = new File(dstPath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            /**
             * 转换文件格式
             */
            String ffmpegPath = dstPath + File.separator + "ffmpeg.exe";
            String path = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
            FfmpegUtil.V3ToWav(ffmpegPath, file.getName(), path, path + File.separator + "trans");

            /**
             * 读取文件流
             */
            byte[] buffer;
            file = new File(path + File.separator + "trans" + File.separator + fileName);
            int length = (int) file.length();
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(length);
            byte[] b = new byte[length];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            String encodeText = org.apache.commons.codec.binary.Base64.encodeBase64String(buffer);
            byte[] encodeText2 = org.apache.commons.codec.binary.Base64.decodeBase64(encodeText);
            String encodeText3 = org.apache.commons.codec.binary.Base64.encodeBase64String(encodeText2);
            System.out.println(encodeText3);

        } catch (Exception e) {
           System.out.println(e.getMessage());
        }


    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 将采用点复制，8k-->16k
     *
     * @param orig
     * @return
     */
    public static byte[] convert8kTo16k(byte[] orig) {
        byte[] dest = new byte[]{};
        for (int j = 0; j < orig.length; j = j + 2) {
            byte[] byte2 = new byte[2];
            byte2[1] = orig[j + 1];
            byte2[0] = orig[j];
            dest = append(dest, byte2);
            dest = append(dest, byte2);
        }
        return dest;
    }


    /**
     * 拼接byte[]
     *
     * @param orig 原始byte[]
     * @param dest 需要拼接的数据
     * @return byte[]
     */
    public static byte[] append(byte[] orig, byte[] dest) {

        byte[] newByte = new byte[orig.length + dest.length];

        System.arraycopy(orig, 0, newByte, 0, orig.length);
        System.arraycopy(dest, 0, newByte, orig.length, dest.length);

        return newByte;


    }
}
