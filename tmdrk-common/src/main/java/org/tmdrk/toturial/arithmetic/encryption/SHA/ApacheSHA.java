package org.tmdrk.toturial.arithmetic.encryption.SHA;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName ApacheSHA
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/12/16 10:20
 * @Version 1.0
 **/
public class ApacheSHA {
    public static void main(String[] args) {
        String str = "周杰你是一个什么样的";
        System.out.println(sha1String(str));
        String cipertext = sha256String(str.getBytes());
        System.out.println(String.format("cipertext:%s,lenght:%s", cipertext, cipertext.length()));
    }

    public static String sha1String(String text) {
        return DigestUtils.sha1Hex(text);
    }

    public static String sha256String(byte[] bytes) {
        return DigestUtils.sha256Hex(bytes);
    }

}
