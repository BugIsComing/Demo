package com.lc.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

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
        String tag = "海林。";
        tag = tag.replaceAll("[\\pP‘’“”]", "");
        System.out.println(tag);

        ArrayList<String> al = new ArrayList<>();
        al.add("2");
        al.add("1");
        LinkedList<String> ll = new LinkedList<>();
        ll.add("2");
        ll.add("1");

        Collections.binarySearch(al,"2");
        Collections.binarySearch(ll,"2");

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
