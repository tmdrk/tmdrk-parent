package org.tmdrk.toturial.arithmetic.encryption.AES;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * 海牙湾对接车点点
 *
 * @author Jie.Zhou
 * @date 2020/7/29 14:22
 */
public class Aes {
    public static void main(String[] args) {
        String key = "47286efc430ba2779d255e331dfbf81e";
//        String data = "test";
        String data = "15605527959";

        System.out.println(Aes.encrypt(data, key));

        System.out.println(Aes.decrypt(Aes.encrypt(data, key), key));

        System.out.println(Aes.decrypt("4zEZMLk6kIJQqh6kLBrBoQ==", key));
    }

    public static String encrypt(String input, String key){
        byte[] crypted = null;
        try{
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return new String(Base64.encodeBase64(crypted));
    }

    public static String decrypt(String input, String key){
        byte[] output = null;
        try{
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decodeBase64(input));
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return new String(output);
    }
}
