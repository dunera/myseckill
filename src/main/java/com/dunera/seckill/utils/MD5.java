package com.dunera.seckill.utils;

import org.springframework.util.DigestUtils;

/**
 * @author lyx
 * @date 2018/11/15
 */
public class MD5 {

    public static String getMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static boolean checkMD5(String original, String md5) {
        return getMD5(original).equals(md5);
    }
}
