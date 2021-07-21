package org.tmdrk.toturial.arithmetic.encryption.RSA;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
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
import java.util.stream.Collectors;

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
    protected static String publicKey  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSLmDA2cy57FJa4SYgGzLbky5YzXNY0ZXYsbYFeY79XGDdlCFGQxYAD0ekjX8EGVhUYgyMHubQz7fhwzOo78nBWHeefsVoiaujpnN1eK6eKvcga4Xa7TyfPsxcwdnBsDFrZ1Ug1mJLKUBe05G++EK8lhzCL4gKBHvLu7TBmCjMxQIDAQAB";
    protected static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJIuYMDZzLnsUlrhJiAbMtuTLljNc1jRldixtgV5jv1cYN2UIUZDFgAPR6SNfwQZWFRiDIwe5tDPt+HDM6jvycFYd55+xWiJq6Omc3V4rp4q9yBrhdrtPJ8+zFzB2cGwMWtnVSDWYkspQF7Tkb74QryWHMIviAoEe8u7tMGYKMzFAgMBAAECgYEAh2obCss4EUtQBwvmq3tGo402M+EuZyrPqwsE2RGAWkfHG7vrDxF6QdflwBOrg/qOjqy9ftfpbaneZ27SXj6zH9gREHlugnpK8jH17c0+1U+9e2K216ViiOG7mV96A8DhDn/kMFql2GsRlJWPxdtRG+Y86m520C3Y5tEhkek9+ckCQQDCS+GVC9h8gZzuqMAg06NMdg1eMvfLjo+RFNw78bj67XS4P7vyhFTf822+u7PpQbnqYpZbY7JtMRbGsr5PlFUfAkEAwJrFh6wjC2mUTgSUjpyWGCtcEZtn24u+ereZPLst5W0N7mH+sGEkJwj8WiqtGrsd2K7XlrTzBK29NHXNBvNdmwJAM5y2msIfyssfZeJbzxyJF2mQmYJOgrsm9fIloqLOcZGcXMlJYt22MhtW/sCbxQ2ZlmKD8Fjmb80HcNbQaRFNHwJAUMlaazLvrBzH4QWYzkytxEuDnbsCkBsIdW7HLqsQcDgS7Ndbvd2xDVJ+js9xtlGgDkAgG42glWjOgM+chPrVWwJBALUPZMGfOw9QR1nX+rI5kTagOf92d/gEjlP+UFkuJZxHqHLZYQ6gHdpkSlJ1NL1ZvpRv6pG1UIi90kNYTLMjeFo=";


    /**
     * 公钥加密私钥解密称之为——加解密
     * 私钥加密公钥解密称之为——签名验签
     **/
    public static void main(String[] args) throws Exception {
//        Map<String, Object> genKeyPair = RSAUtils.genKeyPair();
//        String privateKey = RSAUtils.getPrivateKey(genKeyPair);
//        String publicKey = RSAUtils.getPublicKey(genKeyPair);
//        System.out.println("privateKey="+privateKey);
//        System.out.println("publicKey="+publicKey);
        User User = new User(1L,"张三","12343234566",null);
        String content = getPureContent(User);              //消息内容
        System.out.println("cont:"+content);
        String digest = DigestUtils.sha256Hex(content);     //消息摘要
        String sign = signByPrivateKey(digest, privateKey); //数字签名
        System.out.println("签名结果："+sign);

        boolean res = verifySignByPublicKey(DigestUtils.sha256Hex(content), sign, publicKey);
        System.out.println("验签结果："+res);
        System.out.println("content:"+new String(Base64.decodeBase64(content)));


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

    /**
     * @Description:按字典升序组装消息内容
     * 例：name1=value1&name2=value2......
     * @param bean:签名对象
     * @return: java.lang.String
     **/
    public static String getPureContent(Object bean){
        String json = JSONObject.toJSONString(bean);
        TreeMap<String,Object> params = JSONObject.parseObject(json, TreeMap.class);
        String content = params.entrySet().stream().filter(e -> e.getValue() != null).map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
        System.out.println("content:"+content);
        return Base64.encodeBase64URLSafeString(content.getBytes());
    }
}
