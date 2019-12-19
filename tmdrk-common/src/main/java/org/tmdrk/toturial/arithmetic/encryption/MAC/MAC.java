package org.tmdrk.toturial.arithmetic.encryption.MAC;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.tmdrk.toturial.arithmetic.encryption.SHA.ApacheSHA;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName MAC
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/16 20:27
 * @Version 1.0
 **/
public class MAC {
    /**
     * JDK支持HmacMD5, HmacSHA1,HmacSHA256, HmacSHA384, HmacSHA512
     */
    public enum HmacTypeEn {
        HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512;
    }

    public static byte[] encode(byte[] plaintext, byte[] secretKey, HmacTypeEn type) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey, type.name());
        Mac mac = Mac.getInstance(keySpec.getAlgorithm());
        mac.init(keySpec);
        return mac.doFinal(plaintext);
    }

    public static void main(String[] args) throws Exception {
        String msg = "Hello World!";
        byte[] secretKey = "Secret_Key".getBytes("UTF8");
        byte[] digest = MAC.encode(msg.getBytes(), secretKey, HmacTypeEn.HmacSHA256);
        System.out.println("原文: " + msg);
        System.out.println("摘要: " + Base64.encodeBase64URLSafeString(digest));
        System.out.println(String.format("cipertext:%s,lenght:%s", Hex.encodeHexString(digest), Hex.encodeHexString(digest).length()));
    }
}
