package org.tmdrk.toturial.arithmetic.encryption.RSA;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.base.Joiner;
import com.google.inject.internal.util.$SourceProvider;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.UrlBase64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.tmdrk.toturial.common.util.StringUtil;
import org.tmdrk.toturial.entity.User;

import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * RSASignature 简便的加解密签名验签工具
 *
 * @author Jie.Zhou
 * @date 2020/8/10 16:49
 */
public class RSASignature {
    private static final Logger logger = LoggerFactory.getLogger(RSASignature.class);
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    public static final String ENCODING = "utf-8";
//    public static final String X509 = "X.509";

    //在线生成公钥和私钥
    protected static String publicKey =   "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeCKvGI4ZYMP3/up4nFWU4+vBV\n" +
            "ZINk7k8lrT2+Wu/vpOSKBGKl937Dva/zcb2lNL4qA2pA1mbB7Cmk0Kw+TAnil93v\n" +
            "+X7oB2ooBQwRPlIUUKkAfvpLCVwdbbPdnWVEQV1l0q51Ds/ex3YyjiAggJJHDKO1\n" +
            "0HD+tWc1EhC35h5mMwIDAQAB";
    protected static String privateKey =   "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ4Iq8Yjhlgw/f+6\n" +
            "nicVZTj68FVkg2TuTyWtPb5a7++k5IoEYqX3fsO9r/NxvaU0vioDakDWZsHsKaTQ\n" +
            "rD5MCeKX3e/5fugHaigFDBE+UhRQqQB++ksJXB1ts92dZURBXWXSrnUOz97HdjKO\n" +
            "ICCAkkcMo7XQcP61ZzUSELfmHmYzAgMBAAECgYB2Ohxv9gG6lqRfHbjVmm8IqssD\n" +
            "x1d5OcOidzGa6CNaUCzdiBxY94gxXgZ1BOGQ551ghv+FlCB58XjSIX7KeE5ntp7c\n" +
            "XLW8rCkXHEm8YdknBlGQIgtbDagpT3RjSiUKNjIt2Yptnlg/eknAsFSm/uKBzLbH\n" +
            "jLDwxtEo1ht7AP7yqQJBAMzfT1OlZWAilHw4sP7vWaB9BB6KW9d4atNd2yp93z3R\n" +
            "KcaaSjLmOmffsB46A7B1esctwPawLWBF7y3QauvMwQ8CQQDFeP26DPx0HHI1o59t\n" +
            "v7fDAJlQKjENZV23IjGYZaQU+DUoCbVHjse8MYccHVETi1N/y+OMtXlxJfldiZPF\n" +
            "tACdAkEAjoXrka1GURK9aY2m1DN+jn9qFjT6n3NOHaz1gyH94+tWvKspYy93AjxD\n" +
            "MUP6vQ99UoMp8nOtnYQJWD3dpBzVUQJAUJShvOUFs3/UHw1IxmkgXIOHDE5bO+Ms\n" +
            "Tcm3QT/gp+ntDwx/G9corCPtxUw1RGtrRE/35/g5uTFMw52bXmaohQJAJczvWAp+\n" +
            "jB8cP+38o17/90lSMRZ9yY/MFFm1IBdbIDkLo14/iTKs84Hoi8EJuEqPZfMRFrr+\n" +
            "riEkJAS4XgZL5Q==";


    public static void main(String[] args) throws Exception {
        Map<String, Object> genKeyPair = RSAUtils.genKeyPair();
        String privateKey = RSAUtils.getPrivateKey(genKeyPair);
        String publicKey = RSAUtils.getPublicKey(genKeyPair);
        User User = new User(1L,"张三","12343234566",null);
        String content = getContent(User); //消息
//        String content = getContentWithSha1(User); //消息摘要
        System.out.println("cont:"+content);
        String sign = signByPrivateKey(content, privateKey);
        System.out.println("签名结果："+sign);
        boolean res = verifySignByPublicKey(content, sign, publicKey);
        System.out.println("验签结果："+res);


        /**************** com.town.icbc.commons:1.1.0  RSAUtils ****************/

//        System.out.println("privateKey:"+privateKey);
//        System.out.println("publicKey:"+publicKey);
        String ciphertext = RSAUtils.encodeByPublicKeyFormat(content, publicKey);
        String plaintext = RSAUtils.decodeByPrivateKey(ciphertext, privateKey);
        System.out.println("ciphertext:"+ciphertext +" length:"+ciphertext.length());
        System.out.println("plaintext:"+plaintext);

        String sig = RSAUtils.sign(content.getBytes(), privateKey);
        System.out.println("签名结果："+sig);
        boolean verify = RSAUtils.verify(content.getBytes(), publicKey, sig);
        System.out.println("验签结果："+verify);
    }

    /**
     * 获取私钥
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key.getBytes(ENCODING));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

//    /**
//     * 获取公钥
//     *
//     * @param key
//     * @return
//     * @throws Exception
//     */
//    public static PublicKey getPublicKey(String key) throws Exception {
//        byte[] keyBytes = Base64.decodeBase64(key.getBytes(ENCODING));
//        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
//        InputStream in = new ByteArrayInputStream(keyBytes);
//        Certificate certificate = certificateFactory.generateCertificate(in);
//        PublicKey publicKey = certificate.getPublicKey();
//        return publicKey;
//    }
//
//    /**
//     * 使用公钥对明文进行加密，返回BASE64编码的字符串
//     *
//     * @param publicKey
//     * @param plainText
//     * @return
//     */
//    public static String encrypt(String publicKey, String plainText) {
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//            byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes(ENCODING));
//            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
//            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
//            byte[] enBytes = cipher.doFinal(plainText.getBytes());
//            return new String(Base64.encodeBase64(enBytes));
//        } catch (Exception e) {
//            LOGGER.error("rsa encrypt exception: {}", e.getMessage(), e);
//        }
//        return null;
//    }
//
//    /**
//     * 使用私钥对明文密文进行解密
//     *
//     * @param privateKey
//     * @param enStr
//     * @return
//     */
//    public static String decrypt(String privateKey, String enStr) {
//        try {
//            PrivateKey priKey = getPrivateKey(privateKey);
//            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, priKey);
//            byte[] deBytes = cipher.doFinal(Base64.decodeBase64(enStr));
//            return new String(deBytes);
//        } catch (Exception e) {
//            LOGGER.error("rsa decrypt exception: {}", e.getMessage(), e);
//        }
//        return null;
//    }

    /**
     * RSA私钥签名
     *
     * @param content    待签名数据
     * @param privateKey 私钥
     * @return 签名值
     */
    public static String signByPrivateKey(String content, String privateKey) {
        try {
            PrivateKey priKey = getPrivateKey(privateKey);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(content.getBytes(ENCODING));
            byte[] signed = signature.sign();
            return new String(UrlBase64.encode(signed), ENCODING);
        } catch (Exception e) {
            logger.error("sign error, content: {}", content, e);
        }
        return null;
    }

    /**
     * 公钥验签
     * @param content
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean verifySignByPublicKey(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes(ENCODING));
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(ENCODING));

            return signature.verify(UrlBase64.decode(sign.getBytes(ENCODING)));

        } catch (Exception e) {
            logger.error("verify sign error, content: {}, sign: {}", content, sign, e);
        }
        return false;
    }

    /**
     * @Description:按字典升序组装消息内容
     * 例：name1=value1&name2=value2......
     * @param bean:签名对象
     * @return: java.lang.String
     **/
    public static String getContent(Object bean){
        TreeMap<String,Object> params = new TreeMap<>();
        BeanUtil.beanToMap(bean,params,false,true);
        return Joiner.on("&").withKeyValueSeparator("=").join(params);
    }
}
