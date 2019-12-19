package org.tmdrk.toturial.arithmetic.encryption.BASE64;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @ClassName BASE64
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/15 11:11
 * @Version 1.0
 **/
public class BASE64 {
    public static void main(String[] args) {
        String orig = "hello world 是根深蒂固!";
        String cip = BASE64.encode(orig);
        System.out.println(cip);
        System.out.println(BASE64.decode(cip));
    }

    public static String encode(String plaintext){
        return Base64.getEncoder().encodeToString(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String ciphertext){
        return new String(Base64.getDecoder().decode(ciphertext),StandardCharsets.UTF_8);
    }

    public static byte[] encodeToByte(String plaintext){
        return Base64.getEncoder().encode(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decodeToByte(String ciphertext){
        return Base64.getDecoder().decode(ciphertext);
    }
}
