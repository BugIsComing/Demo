package com.lc.client;

/**
 * @author lc
 * @desc
 * @date 2018-08-16 22:19:47
 **/


public class Solution{
    public static void main(String[] args){
        String str1="1234";
        String str2="321";
        System.out.println(judge(str1,str2));
    }

    public static boolean judge(String str1,String str2){
        int len1 = (str1==null || str1.length()==0)?0:str1.length();
        int len2 = (str2==null || str2.length()==0)?0:str2.length();
        int[] count1 = new int[128];//最多128个字符
        int[] count2 = new int[128];//最多128个字符
        int i=0,j=0;
        while(i<len1){
            count1[str1.charAt(i)]++;
            i++;
        }
        while(j<len2){
            count2[str2.charAt(j)]++;
            j++;
        }
        for(int k=0;k<128;k++){
            if(count1[k] != count2[k]){
                return false;
            }
        }
        return true;
    }
}

