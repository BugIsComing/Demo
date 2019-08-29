package com.lc.client;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
    private static String parseTimePoint(String timeSpec) throws Exception {
        List<String> hourList = new ArrayList<>();
        List<String> minuteList = new ArrayList<>();
        String hourPattern = "\\d+时";
        String minPat = "\\d+分";
        matchHourAndMinute(hourPattern, timeSpec, hourList);
        matchHourAndMinute(minPat, timeSpec, minuteList);
        if (hourList.size() == minuteList.size() && hourList.size() == 2) {
            return hourList.get(0) + ":" + minuteList.get(0) + "-" + hourList.get(1) + ":" + minuteList.get(1);
        }
        throw new Exception(timeSpec);
    }

    private static void matchHourAndMinute(String pattern, String line, List<String> resultList) {
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        // Now create matcher object.
        Matcher m = r.matcher(line);
        while (m.find()) {
            String text = m.group();
            Matcher matcher = Pattern.compile("\\d+").matcher(text);
            while (matcher.find()) {
                resultList.add(matcher.group(0));
            }
        }
    }
    public static void main(String[] args) {
        String str="每 1天00 小时00 分00 秒  从 第1天的07:00:00  至 第1天的08:59:59 进行促销";
        String startTime=str.substring(25,33);
        System.out.println(startTime);
        String endTime=str.substring(41,49);
        System.out.println(endTime);
    }
}
