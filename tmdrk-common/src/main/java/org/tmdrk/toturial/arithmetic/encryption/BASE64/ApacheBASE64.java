package org.tmdrk.toturial.arithmetic.encryption.BASE64;

import org.apache.commons.codec.binary.Base64;

/**
 * @ClassName ApacheBASE64
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/15 13:16
 * @Version 1.0
 **/
public class ApacheBASE64 {
    public static void main(String[] args) {
        String string = "hello world 是根深蒂固!";
        //编码
        String encode = encode(string.getBytes());
        System.out.println(string + "\t编码后的字符串为：" + encode);
        //解码
        String decode = decode(encode.getBytes());
        System.out.println(encode + "\t字符串解码后为：" + decode);
    }
    //base64 解码
    public static String decode(byte[] bytes) {
        return new String(Base64.decodeBase64(bytes));
    }

    //base64 编码
    public static String encode(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }
}
