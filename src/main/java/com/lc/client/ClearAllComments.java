package com.lc.client;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 该类实现清楚指定后缀类型的文件中所有注释
 * 同时通过线程池进行处理
 *
 * @author lc
 * @date 2018年11月2日19:52:37
 */
public class ClearAllComments {
    private ThreadPoolExecutor threadPoolExecutor;

    public void setExecutorService(ThreadPoolExecutor executorService) {
        this.threadPoolExecutor = executorService;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\dmall_lc\\Desktop\\IO";
        ClearAllComments clearAllComments = new ClearAllComments();
        clearAllComments.setExecutorService(new ThreadPoolExecutor(15, 20, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(250), new ThreadPoolExecutor.AbortPolicy()));
        /**
         * 处理清理注释的前提是文件里面的注释方式都是正确的
         */
        clearAllComments.doScan(path);
        /**
         * 要在主线程调用shutdown操作，也就是所有任务都被执行完之后才能提交
         */
        clearAllComments.getThreadPoolExecutor().shutdown();
    }

    public void doScan(String path) {
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
                if ("java".equalsIgnoreCase(suffix)) {
                    //if (fileName.equalsIgnoreCase("ThreadPoolExecutor.java")) {
                    try {
                        threadPoolExecutor.execute(new ClearWorker(item));
                    } catch (Exception e) {
                        e.printStackTrace();
                        threadPoolExecutor.shutdown();
                        return;
                    }
                }
            }
        }
    }
}

/**
 * 这是清除注释的worker
 */
class ClearWorker implements Runnable {
    private File target;

    public ClearWorker(File target) {
        this.target = target;
    }

    @Override
    public void run() {
        int state = 0;
        FileInputStream inputStream = null;
        if (target == null) {
            return;
        }
        StringBuffer str = new StringBuffer();
        try {
            inputStream = new FileInputStream(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert inputStream != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String content;
        try {
            while ((content = bufferedReader.readLine()) != null) {
                if (content.equals("") || isLineInValid(content)) {
                    continue;
                }
                state = doParseLine(content, str, state);
            }
            if(state!=0){
                throw new Exception("Java file error");
            }
            String suffix = getSuffixFromFile(target);
            String path = target.getPath();
            String newFileName = path.substring(0, path.lastIndexOf(File.separator)) + File.separator + getFileNameWithoutSuffix(target) + "_NoComment." + suffix;
            BufferedWriter out = new BufferedWriter(new FileWriter(newFileName));
            out.write(str.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 处理一行数据
     *
     * @param content 输入内容
     * @param str     输出
     * @param state   状态标注
     * @return 返回状态码
     */
    private int doParseLine(String content, StringBuffer str, int state) {
        int ch;
        char temp;
        for (int i = 0; i < content.length(); i++) {
            ch = content.charAt(i);
            temp = (char) ch;
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
            } else if (state == 3) {
                str.deleteCharAt(str.length() - 1);
                if (ch == '*') {
                    state = 4;
                }
            } else if (state == 4) {
                str.deleteCharAt(str.length() - 1);
                if (ch == '/') {
                    state = 0;
                } else if (ch != '*') {
                    state = 3;
                }
            }
        }
        if (state == 0 || state == 2) {
            str.append("\n");
        }
        if (state == 2) {
            state = 0;
        }
        return state;
    }

    /**
     * 获取文件后缀
     *
     * @param file 文件
     * @return 返回文件后缀
     */
    private String getSuffixFromFile(final File file) {
        if (isFileInValid(file)) {
            return "";
        }
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取文件名
     *
     * @param file 文件
     * @return 返回文件名
     */
    private String getFileNameWithoutSuffix(final File file) {
        if (isFileInValid(file)) {
            return "";
        }
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        return fileName.substring(0, index);
    }

    /**
     * 检查文件是否合法
     *
     * @param file 文件
     * @return true表示不合法，false表示合法
     */
    private Boolean isFileInValid(final File file) {
        if (file == null) {
            return Boolean.TRUE;
        }
        String fileName = file.getName();
        if (StringUtils.isEmpty(fileName)) return Boolean.TRUE;
        return Boolean.FALSE;
    }

    /**
     * 判断当前行是否只包含注释
     *
     * @param line
     * @return
     */
    private Boolean isLineInValid(String line) {
        String temp = new String(line);
        temp = temp.trim();
        if (temp.indexOf("//") == 0) {
            return true;
        }
        return false;
    }
}
