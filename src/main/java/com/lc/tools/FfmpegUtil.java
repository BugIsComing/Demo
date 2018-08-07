package com.lc.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ffmpeg工具类
 * <p>需要依赖ffmpeg</p>
 *
 * @author flwang
 * @date 2018/6/3
 */
public class FfmpegUtil {

    /**
     * 将V3格式转换为wav
     *
     * @param ffmpegPath
     * @param fileName   文件名
     * @param sourcePath 原文件路径
     * @param destPath   转换后的目标文件路径
     */
    public static boolean V3ToWav(String ffmpegPath, String fileName, String sourcePath, String destPath) {
        List<String> cmd = new ArrayList<>();
        String sourceFullPath = new File(sourcePath, fileName).getAbsolutePath();
        File destDir = new File(destPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        String destFullPath = new File(destPath, fileName).getAbsolutePath();
        cmd.add(ffmpegPath);
        cmd.add("-i");
        cmd.add(sourceFullPath);
        //强制覆盖
        cmd.add("-y");
        //采样率
        cmd.add("-ar");
        cmd.add("16000");
        //声道
        cmd.add("-ac");
        cmd.add("1");
        cmd.add(destFullPath);

        return doConvert(cmd, sourceFullPath, destFullPath);
    }


    private static boolean doConvert(List<String> cmd, String source, String dest) {
        try {
            processCmd(cmd);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void processCmd(List cmd) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(cmd);
        Process process = builder.start();
        process.waitFor();
        process.destroy();
    }

    /**
     * 获取音频总时间
     *
     * @param ffmpegPath ffmpeg路径
     * @param videoPath  视频路径
     * @return x时x分x秒
     */
    public static String getAudioTimeStr(String ffmpegPath, String videoPath) {
        List<String> commands = new ArrayList<String>();
        commands.add(ffmpegPath);
        commands.add("-i");
        commands.add(videoPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                String time = getTimeStr(m.group(1));
                return time;
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取音频总时间
     *
     * @param ffmpegPath ffmpeg路径
     * @param videoPath  视频路径
     * @return
     */
    public static int getAudioTime(String ffmpegPath, String videoPath) {
        List<String> commands = new ArrayList<String>();
        commands.add(ffmpegPath);
        commands.add("-i");
        commands.add(videoPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                int time = getTimeLen(m.group(1));
                return time;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 获取视频总时间
     *
     * @param ffmpegPath ffmpeg路径
     * @param videoPath  视频路径
     * @return
     */
    public static int getVideoTime(String ffmpegPath, String videoPath) {
        List<String> commands = new ArrayList<String>();
        commands.add(ffmpegPath);
        commands.add("-i");
        commands.add(videoPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                int time = getTimeLen(m.group(1));
                return time;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 获取时间秒数
     *
     * @param timelen
     * @return 格式:"00:00:10.68"
     */
    private static int getTimeLen(String timelen) {
        int min = 0;
        String arr[] = timelen.split(":");
        if (arr[0].compareTo("0") > 0) {
            min += Integer.valueOf(arr[0]) * 60 * 60;//秒
        }
        if (arr[1].compareTo("0") > 0) {
            min += Integer.valueOf(arr[1]) * 60;
        }
        if (arr[2].compareTo("0") > 0) {
            min += Math.floor(Float.valueOf(arr[2]));
        }
        return min;
    }

    /**
     * 获取时间字符串
     *
     * @param timelen
     * @return x时x分x秒
     */
    private static String getTimeStr(String timelen) {
        StringBuilder min = new StringBuilder();

        String arr[] = timelen.split(":");
        if (arr[0].compareTo("0") > 0) {
            int hour = Integer.valueOf(arr[0]);
            if (hour > 0) {
                min.append(Integer.valueOf(arr[0])).append("时");
            }
        }
        if (arr[1].compareTo("0") > 0) {
            min.append(Integer.valueOf(arr[1])).append("分");
        }
        if (arr[2].compareTo("0") > 0) {
            min.append(Float.valueOf(arr[2]).intValue()).append("秒");
        }
        return min.toString();
    }
}
