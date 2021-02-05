package com.me.reader.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5Digest(String source, Integer salt) {
        char[] chars = source.toCharArray();
        // 混淆原数据，对每一个字符进行混淆
        for (int i = 0; i < chars.length; ++i) {
            chars[i] = (char) (chars[i] + salt);
        }
        String target = new String(chars);
        // 计算MD5摘要并以32个字符的十六进制字符串形式返回值。
        String md5 = DigestUtils.md5Hex(target);
        return md5;
    }
}
