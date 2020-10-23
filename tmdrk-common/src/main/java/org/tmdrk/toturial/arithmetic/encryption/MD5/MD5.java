package org.tmdrk.toturial.arithmetic.encryption.MD5;

import cn.hutool.crypto.SecureUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName MD5
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/15 11:20
 * @Version 1.0
 **/
public class MD5 {
    public static void main(String[] args) {
        String str = "kjdhfskjdhDFDFfskdjf";
        System.out.println(encodeMD5Hex(str));
        System.out.println(encodeMD5ByJDK(str));
        System.out.println(SecureUtil.md5(str));

        System.out.println(Base64.encodeBase64URLSafeString(str.getBytes()));
        System.out.println(Base64.encodeBase64(str.getBytes()));
    }


    public static byte[] encodeMD5(String data) throws Exception {
        // 执行消息摘要
        return DigestUtils.md5(data);
    }

    public static String encodeMD5Hex(String plainText){
        return DigestUtils.md5Hex(plainText);
    }

    public static String encodeMD5ByJDK(String plainText){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(plainText.getBytes());
            return new BigInteger(1, md5.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
