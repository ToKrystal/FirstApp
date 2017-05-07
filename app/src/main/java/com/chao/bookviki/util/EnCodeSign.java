package com.chao.bookviki.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jessica on 2017/5/7.
 */

public class EnCodeSign {
    public static String md5Encode(int count,int appId,String sign){
        String toStr = "count"+count+"showapi_appid"+appId;
        String str = toStr + sign;
        try {
            return DigestUtils.md5Hex(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }




}
