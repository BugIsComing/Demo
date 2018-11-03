package com.lc.client;

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
    ThreadPoolExecutor threadPoolExecutor;

    public void setExecutorService(ThreadPoolExecutor executorService) {
        this.threadPoolExecutor = executorService;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public static void main(String[] args) {
        String path = "E:\\lc";
        ClearAllComments clearAllComments = new ClearAllComments();
        clearAllComments.setExecutorService(new ThreadPoolExecutor(15, 20, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(250), new ThreadPoolExecutor.AbortPolicy()));
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
        StringBuffer str = new StringBuffer("");
        try {
            inputStream = new FileInputStream(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        if (bufferedReader == null) {
            return;
        }
        String content = null;
        try {
            while ((content = bufferedReader.readLine()) != null) {
                if (content.equals("")) {
                    continue;
                }
                state = doParseLine(content, str, state);
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
     * @param content
     * @param str
     * @param state
     * @return
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
        if (state == 0) {
            str.append("\n");
        }
        if (state == 2) {
            state = 0;
        }
        return state;
    }

    /**
     * 获取一个文件的后缀
     *
     * @param file
     * @return
     */
    private String getSuffixFromFile(final File file) {
        if (file == null) {
            return "";
        }
        String fileName = file.getName();
        if (fileName == null || fileName.length() == 0) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取不带后缀的文件名
     *
     * @param file
     * @return
     */
    private String getFileNameWithoutSuffix(final File file) {
        if (file == null) {
            return "";
        }
        String fileName = file.getName();
        if (fileName == null || fileName.length() == 0) {
            return "";
        }
        int index = fileName.lastIndexOf('.');
        return fileName.substring(0, index);
    }
}
