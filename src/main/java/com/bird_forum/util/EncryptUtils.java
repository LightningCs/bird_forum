package com.bird_forum.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {

    // MD5加密
    public static String md5(String data) throws NoSuchAlgorithmException {
        // 加密工具类
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] pwd = md5.digest(data.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(pwd);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
