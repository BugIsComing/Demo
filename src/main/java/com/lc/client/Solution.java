package com.lc.client;

/**
 * @author lc
 * @desc
 * @date 2018-08-16 22:19:47
 **/
public class Solution {
    public String replaceSpace(StringBuffer str) {
        if(str == null || str.length() ==0){
            return "";
        }
        String result = str.toString();
        result.replace(" ","%20");
        return result;
    }
}
