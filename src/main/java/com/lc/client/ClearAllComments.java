package com.lc.client;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * 该类实现清楚指定后缀类型的文件中所有注释
 *
 * @author lc
 * @date 2018年11月2日19:52:37
 */
public class ClearAllComments {
    public static void main(String[] args) {
        String path = "C:\\Users\\ifly_lc\\Desktop";
        ClearAllComments clearAllComments = new ClearAllComments();
        clearAllComments.doScan(path);
    }

    public void doScan(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        if (tempList == null || tempList.length == 0) {
            return;
        }
        for (File item : tempList) {
            if (item.isDirectory()) {
                doScan(item.getPath());
            } else {
                String fileName = item.getName();
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                //if ("java".equalsIgnoreCase(suffix)) {
                if (fileName.equalsIgnoreCase("ThreadPoolExecutor.java")) {
                    System.out.println(fileName);
                    try {
                        doClear(item);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        System.out.println("文件中注释格式不对");
                        return;
                    }
                }

            }
        }
    }

    private void doClear(File file) throws Exception {
        int state = 0;
        StringBuffer str = new StringBuffer("");
        FileReader fr = new FileReader(file);
        int ch = 0, ch1;
        char temp;
        ch = fr.read();
        while (ch != -1) {
            //System.out.print((char) ch);
            temp = (char) ch;
//            if (ch == '\r') {
//                if ((ch1 = fr.read()) == '\n') {
//                    continue;
//                }
//                str.append('\r');
//                ch = ch1;
//            }
            str.append(temp);
            if (state == 0) {
                if (ch == '/') {
                    state = 1;
                }
            } else if (state == 1) {
                if (ch == '/') {
                    state = 2;
                } else if (ch == '*') {
                    state = 3;
                } else {
                    state = 0;
                }
                if (state == 2 || state == 3) {
                    str.deleteCharAt(str.length() - 1);
                    str.deleteCharAt(str.length() - 1);
                }
            } else if (state == 2) {
                str.deleteCharAt(str.length() - 1);
                if (ch == '\n') {
                    state = 0;
                }
            } else if (state == 3) {
                str.deleteCharAt(str.length() - 1);
                if (ch == '*') {
                    state = 4;
                }
            } else if (state == 4) {
                str.deleteCharAt(str.length() - 1);
                if (ch == '/') {
                    state = 0;
                } else {
                    state = 3;
                }
            }
            ch = fr.read();
        }
        fr.close();
        //String newPath = file.getPath() + Path.s
        //File newFile = new File();
        System.out.println(str.toString());
    }
}
