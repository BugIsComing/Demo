package com.lc.client;

import org.apache.ant.compress.taskdefs.Unzip;

import java.io.File;

public class MainTest2 {

    public static void main(String[] args) {
        File theZIPFile = new File("C:\\Users\\ifly_lc\\Desktop\\1527559923909KJ6tiW.zip");
        File theTargetFolder = new File("C:\\Users\\ifly_lc\\Desktop\\测试lc");
        Unzip unzipper = new Unzip();
        unzipper.setEncoding("GB2312");
        unzipper.setSrc(theZIPFile);
        unzipper.setDest(theTargetFolder);
        unzipper.execute();
    }
} 